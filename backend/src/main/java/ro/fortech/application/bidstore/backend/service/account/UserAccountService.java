package ro.fortech.application.bidstore.backend.service.account;

import ro.fortech.application.bidstore.backend.exception.AccountActivationException;
import ro.fortech.application.bidstore.backend.exception.AccountException;
import ro.fortech.application.bidstore.backend.model.UserRegistration;
import ro.fortech.application.bidstore.backend.persisetence.entity.User;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAuth;

import javax.ejb.Local;
import java.io.Serializable;
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
}
