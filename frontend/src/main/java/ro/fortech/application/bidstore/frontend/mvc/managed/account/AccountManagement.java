package ro.fortech.application.bidstore.frontend.mvc.managed.account;

import ro.fortech.application.bidstore.backend.exception.account.AccountException;
import ro.fortech.application.bidstore.backend.model.AddressType;
import ro.fortech.application.bidstore.backend.persistence.entity.User;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAddress;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAddressPk;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAuth;
import ro.fortech.application.bidstore.backend.service.account.UserAccountService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by robert.ruja on 25-Apr-17.
 */

@ManagedBean(name = "accountMgt")
@ViewScoped
public class AccountManagement implements Serializable {

    @Inject
    UserAccountService userAccountService;

    @Inject
    FacesContext context;

    @Inject
    UserAccount account;

    private Map<String,UserAddress> addressMap;

    private String currentPassword;

    private String newPassword;

    private String confirmPassword;

    private String passwordMessage;

    @PostConstruct
    public void init(){
        if(userAccountService != null) {
            addressMap = userAccountService.getUserAddressMap(account.getUser());
            if (addressMap.isEmpty()) {
                addressMap.put("homeAddress", new UserAddress(new UserAddressPk(account.getUser().getUsername(), AddressType.HOME)));
                addressMap.put("billingAddress", new UserAddress(new UserAddressPk(account.getUser().getUsername(), AddressType.BILLING)));
                addressMap.put("shippingAddress", new UserAddress(new UserAddressPk(account.getUser().getUsername(), AddressType.SHIPPING)));

            }
        }
    }

    public void changePassword() {
        if(newPassword != null && !newPassword.isEmpty()){
            UserAuth auth = new UserAuth();
            auth.setPassword(currentPassword);
            auth.setUsername(account.getUserAuth().getUsername());
            try {
                if (currentPassword != null && !currentPassword.isEmpty() && userAccountService.isAuthenticUser(auth)) {
                    auth.setPassword(newPassword);
                    userAccountService.changeUserAuthentication(auth);
                } else {
                    context.addMessage("passwordForm:passwordMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                            "The current password is incorrect, check your passord"));
                    currentPassword = null;
                    return;
                }
            }catch(AccountException ex) {
                context.addMessage("passwordForm:passwordMessage", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                        "An internal error occured while trying to save the new password"));
                return;
            }
        }

        context.addMessage("passwordForm:passwordMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                "Your account has been updated successfully!"));
        resetPasswods();
    }

    public void saveUserAccount() {

        try{
            User user = account.getUser();
            userAccountService.updateUserDetails(user);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                    "Your account has been updated successfully!"));
        }catch (AccountException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    "An internal error occured while saving your account. Please try again later"));
        }
    }

    public void saveUserAddress() {
        try{
            userAccountService.updateUserAddress(addressMap);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
                    "Your address details have been updated successfully!"));
        }catch (AccountException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    "An internal error occured while saving your account. Please try again later"));
        }
    }

    private void resetPasswods(){
        this.currentPassword = null;
        this.newPassword = null;
        this.confirmPassword = null;
    }

    public Map<String, UserAddress> getAddressMap() {
        return addressMap;
    }

    public void setAddressMap(Map<String, UserAddress> addressMap) {
        this.addressMap = addressMap;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPasswordMessage() {
        return passwordMessage;
    }

    public void setPasswordMessage(String passwordMessage) {
        this.passwordMessage = passwordMessage;
    }
}
