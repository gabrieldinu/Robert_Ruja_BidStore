package ro.fortech.application.bidstore.backend.service.account;

import ro.fortech.application.bidstore.backend.exception.account.AccountException;
import ro.fortech.application.bidstore.backend.model.UserRegistration;
import ro.fortech.application.bidstore.backend.persistence.entity.User;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAddress;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAuth;

import javax.ejb.Local;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * Created by robert.ruja on 10-Apr-17.
 */
@Local
public interface UserAccountService extends Serializable {

    User getUserDetails(User user);

    boolean isAuthenticUser(UserAuth userAuth);

    UUID insertNewUser(UserAuth userAuth, User user) throws AccountException;

    UserRegistration getRegisterStatus(UserAuth userAuth);

    void activateAccount(String pathUUID) throws AccountException;

    String resetPassword(User user) throws AccountException;

    UserAuth getRequestChangePassowrd(String token);

    void changeUserAuthentication(UserAuth userAuth) throws AccountException;

    UserAuth getAuthenticationByUUID(String cookieValue);

    void enableUser(String managedUsername) throws AccountException;

    void disableUser(String managedUsername) throws AccountException;

    Map<String,UserAddress> getUserAddressMap(User user);

    void updateUserDetails(User user) throws AccountException;

    void updateUserAddress(Map<String,UserAddress> addressMap) throws AccountException;
}
