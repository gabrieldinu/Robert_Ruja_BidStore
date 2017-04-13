package ro.fortech.application.bidstore.mvc.model.managed;


import ro.fortech.application.bidstore.backend.exception.AccountException;
import ro.fortech.application.bidstore.backend.model.UserRegistration;
import ro.fortech.application.bidstore.backend.model.UserRole;
import ro.fortech.application.bidstore.backend.persisetence.entity.User;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAuth;
import ro.fortech.application.bidstore.backend.service.UserAccountService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.AlterableContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by robert.ruja on 11-Apr-17.
 */
@Named
@SessionScoped
public class UserAccount implements Serializable{

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

    @PostConstruct
    public void init(){

    }

    public String signIn() {

        if( userAccountService.isAuthenticUser(userAuth)) {

            user.setUsername(userAuth.getUsername());

            if((user = userAccountService.getUserDetails(user)) == null) {
                destroyContext();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal error",
                       "An error occured during user access" ));
                return "/view/error.xhtml";
            }

            if(userAccountService.getRegisterStatus(userAuth) == UserRegistration.PENDING) {

                destroyContext();
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Error",
                        "You already have an account, but it is not activated. Please check your email!" ));
                return null;
            }

            if (user.getRole().equals(UserRole.ADMIN)) {

                admin = true;
            }
            loggedIn = true;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome back " + user.getFirstName(),
                    "You can now browse the catalog"));
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
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    "Account name already exists"));
                return null;

            case UNREGISTERED:
                try {
                    user.setUsername(userAuth.getUsername());
                    user.setRole(UserRole.USER);
                    UUID uuid = userAccountService.insertNewUser(userAuth, user);
                    context.addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Hi " + user.getFirstName(), "Welcome to bid store website"));
                    return "/view/account/mailSent";
                }catch(AccountException ex){
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                            "An internal error occured"));
                    return null;
                }

            case PENDING:
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                        "You already have an account, but it is not activated! Check your email!"));
                return null;

            default:
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                        "An internal error occured"));
                return null;
        }

    }

    private void destroyContext() {
        AlterableContext ctx = (AlterableContext) beanManager.getContext(SessionScoped.class);
        Bean<?> myBean = beanManager.getBeans(UserAccount.class).iterator().next();
        ctx.destroy(myBean);
        //myBean = beanManager.getBeans(ShoppingCartBean.class).iterator().next();
        ctx.destroy(myBean);
    }

    public String doLogout() {
        destroyContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Log out",
                "You have been logged out successfully"));
        return "/view/account/signin";
    }
    public void checkLoggedIn() throws IOException {
        if (isLoggedIn()) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/home.xhtml");
        }
    }

    public String recoverPassword() {
//        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_EMAIL, User.class);
//        query.setParameter("email", user.getEmail());
//        try {
//            user = query.getSingleResult();
//            String temporaryPassword = LoremIpsum.getInstance().getWords(1);
//            user.setPassword(PasswordUtils.digestPassword(temporaryPassword));
//            em.merge(user);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email sent",
                    "An email has been sent to " + user.getEmail() + " with temporary password :" ));
//            // send an email with the password "dummyPassword"
//            return doLogout();
//        } catch (NoResultException e) {
//            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Unknown email",
//                    "This email address is unknonw in our system"));
//            return null;
//        }
        return doLogout();
    }

    public UserAccountService getUserAccountService() {
        return userAccountService;
    }

    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
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
