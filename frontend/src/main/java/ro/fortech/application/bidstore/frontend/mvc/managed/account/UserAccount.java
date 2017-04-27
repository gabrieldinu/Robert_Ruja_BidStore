package ro.fortech.application.bidstore.frontend.mvc.managed.account;


import ro.fortech.application.bidstore.backend.exception.account.AccountEmailException;
import ro.fortech.application.bidstore.backend.exception.account.AccountException;
import ro.fortech.application.bidstore.backend.exception.email.EmailException;
import ro.fortech.application.bidstore.backend.model.UserRegistration;
import ro.fortech.application.bidstore.backend.model.UserRole;
import ro.fortech.application.bidstore.backend.persistence.entity.User;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAuth;
import ro.fortech.application.bidstore.backend.service.account.UserAccountService;
import ro.fortech.application.bidstore.backend.service.mail.EmailBuilder;
import ro.fortech.application.bidstore.backend.service.mail.MailService;
import ro.fortech.application.bidstore.backend.service.mail.ConfiguredMailService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.AlterableContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by robert.ruja on 11-Apr-17.
 */
@Named
@SessionScoped
public class UserAccount implements Serializable {

    @Inject
    UserAccountService userAccountService;

    private User user = new User();
    private UserAuth userAuth = new UserAuth();
    private boolean loggedIn;
    private boolean admin;
    private boolean rememberMe;

    private String password;
    private String confirmationPassword;


    @Inject
    FacesContext context;

    @Inject
    BeanManager beanManager;

    @Inject
    ExternalContext externalContext;

    @Inject
    Properties configProperties;

    @Inject
    HttpServletRequest request;

    @Inject
    HttpServletResponse response;

    @Inject
    @ConfiguredMailService
    MailService mailService;

    private static final String COOKIE_NAME = "applicationBidStoreCookie";
    private static final int COOKIE_AGE = 1800; //half an hour

    @PostConstruct
    private void init() {

        //check if the user has remember me cookie
        String cookieValue = getCookieValue();
        if (cookieValue == null)
            return;
        userAuth = userAccountService.getAuthenticationByUUID(cookieValue);

        if(userAuth !=null) {
            user.setUsername(userAuth.getUsername());
            user = userAccountService.getUserDetails(user);
            if (user.getRole().equals(UserRole.ADMIN))
                admin = true;
            // The user is now logged in
            loggedIn = true;
        } else {
            removeCookie();
        }
    }

    public String signIn() {

        if( userAccountService.isAuthenticUser(userAuth)) {

            user.setUsername(userAuth.getUsername());

            if((user = userAccountService.getUserDetails(user)) == null) {
                destroyContext();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal error",
                       "An error occured during user access" ));
                return "/view/public/error.xhtml";
            }

            if(userAccountService.getRegisterStatus(userAuth) == UserRegistration.PENDING) {

                destroyContext();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
                        "You already have an account, but it is not activated. Please check your email!" ));
                return null;
            }

            if(!user.isEnabled()){
                destroyContext();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
                        "Your account has been disabled, contact your administrator to enable it!" ));
                return null;
            }

            if (user.getRole().equals(UserRole.ADMIN)) {

                admin = true;
            }
            if (rememberMe) {
                String uuid = UUID.randomUUID().toString();
                userAuth.setUuid(uuid);
                addCookie(uuid);
            } else {
                userAuth.setUuid(null);
                removeCookie();
            }
            loggedIn = true;
            return "/view/main";
        }

        else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Wrong user/password",
                    "Check your inputs or ask for a new password"));
            return null;
        }
    }

    public String register() {

        UserRegistration status = userAccountService.getRegisterStatus(userAuth);

        switch(status){

            case REGISTERED:
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","Account name already exists"));
                return null;

            case UNREGISTERED:
                try {
                    user.setUsername(userAuth.getUsername());
                    user.setRole(UserRole.USER);
                    user.setEnabled(true);
                    UUID uuid = userAccountService.insertNewUser(userAuth, user);
                    sendConfirmationEmail(user, uuid);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Mail sent "));
                    return "/view/public/account/mailSent";
                } catch(AccountEmailException ex) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","This email address already exists"));
                    return null;
                }catch(AccountException ex){

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","An internal error occured"));
                    return null;
                }

            case PENDING:

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error","You already have an account, but it is not activated! Check your email!"));
                return null;

            default:

                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An internal error occured"));
                return null;
        }

    }

    private void sendConfirmationEmail(User user, UUID uuid) throws AccountException {

        if(this.configProperties != null) {
            try {
                mailService.sendMail(
                        EmailBuilder.getEmailBuilder()
                                .withFrom("caveat-emptor@fortech.ro")
                                .withTo(user.getEmail())
                                .withSubject("Caveat Emptor account activation for " + user.getFirstName() + " " + user.getLastName())
                                .withText("Hi, your activation link is " + configProperties.getProperty("application.url") + "/BidStore/activate?activationId=" + uuid)
                                .build()
                );
            } catch (EmailException e) {
                throw new AccountException(e);
            }
        }else {
            throw new AccountException("Unable to fetch mail configuration properties");
        }
    }

    private void destroyContext() {
        AlterableContext ctx = (AlterableContext) beanManager.getContext(SessionScoped.class);
        Bean<?> myBean = beanManager.getBeans(UserAccount.class).iterator().next();
        ctx.destroy(myBean);
        //myBean = beanManager.getBeans(ShoppingCartBean.class).iterator().next();
        //ctx.destroy(myBean);
    }

    public String doLogout() {
        destroyContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Log out",
                "You have been logged out successfully"));
        return "/view/public/account/signin";
    }
    public void checkLoggedIn() throws IOException {
        if (isLoggedIn()) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/home.xhtml");
        }
    }

    public String recoverPassword() {

        try {
            String resetToken = userAccountService.resetPassword(user);

            if(this.configProperties != null) {

                mailService.sendMail(
                        EmailBuilder.getEmailBuilder()
                                .withFrom("caveat-emptor@fortech.ro")
                                .withTo(user.getEmail())
                                .withSubject("Caveat Emptor password reset link")
                                .withText("Hi, to reset you password follow this link: " + configProperties.getProperty("application.url") + "/BidStore/resetPassword?token=" + resetToken)
                                .build()
                );
            }else {
                throw new AccountException("Unable to fetch mail configuration properties");
            }
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email sent",
                    "An email has been sent to " + user.getEmail() + " with a link to reset your password :" ));
        } catch (AccountException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Unknown email",
                    "This email address is unknown to our database!"));
            return null;
        } catch (EmailException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal error",
                    "An internal error occurred while sending your email. Please try again later. If the problem persists, contact your administrator"));
        }

        return "/view/public/account/signin";
    }

    public String changePassword(){
        try {
            userAccountService.changeUserAuthentication(userAuth);
            destroyContext();
            this.loggedIn = false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New password",
                    "Your password has been reset successfully. You can now login with your new credentials."));

        } catch (AccountException ex){
            destroyContext();
            this.loggedIn = false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal error",
                    "An internal error occurred, please try again later"));
        }
        return "/view/public/account/signin";
    }

    private String getCookieValue() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void removeCookie() {
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private void addCookie(String value) {
        Cookie cookie = new Cookie(COOKIE_NAME, value);
        cookie.setPath("/sampleJSFLogin");
        cookie.setMaxAge(COOKIE_AGE);
        response.addCookie(cookie);
    }

    public UserAccountService getUserAccountService() {
        return userAccountService;
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    public void checkAdminPermissions(ComponentSystemEvent event) throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        if(!admin){
            context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath()+"/view/main.xhtml");
        }
        return;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
