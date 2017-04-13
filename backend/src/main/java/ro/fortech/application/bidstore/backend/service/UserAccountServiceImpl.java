package ro.fortech.application.bidstore.backend.service;

import ro.fortech.application.bidstore.backend.exception.AccountActivationException;
import ro.fortech.application.bidstore.backend.exception.AccountException;
import ro.fortech.application.bidstore.backend.model.UserRegistration;
import ro.fortech.application.bidstore.backend.persisetence.dao.UserDAO;
import ro.fortech.application.bidstore.backend.persisetence.entity.User;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAuth;
import ro.fortech.application.bidstore.backend.util.PasswordDigest;

import javax.ejb.Stateful;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;


/**
 * Created by robert.ruja on 10-Apr-17.
 */

@Named
@Stateful
public class UserAccountServiceImpl implements UserAccountService {

    @Inject
    private UserDAO userDAO;

    @Override
    public User getUserDetails(User user) {

        return userDAO.getUser(user);
    }

    public boolean isAuthenticUser(UserAuth userAuthInput){

        UserAuth userAuthFromDB = userDAO.getUserAuthentication(userAuthInput.getUsername());

        if(userAuthFromDB == null) {
            return false;
        }

        return userAuthFromDB.getPassword().equals(
                PasswordDigest.digestPassword(userAuthInput.getPassword())
        );

    }

    @Override
    public UUID insertNewUser(UserAuth userAuth, User user) throws AccountException {

        if(getRegisterStatus(userAuth) == UserRegistration.REGISTERED) {
            throw new AccountException("Username already taken");
        }

        UUID uuid = UUID.randomUUID();

        //DigestUserPassword
        userAuth.setPassword(PasswordDigest.digestPassword(userAuth.getPassword()));
        userAuth.setUuid(uuid.toString());
        userDAO.saveUser(userAuth,user);
        return uuid;
    }

    @Override
    public UserRegistration getRegisterStatus(UserAuth userAuth) {

        UserAuth userAuthFromDB = userDAO.getUserAuthentication(userAuth.getUsername());

        if(userAuthFromDB == null) {

            return UserRegistration.UNREGISTERED;

        }else if (userAuthFromDB.getUuid() != null){

            return UserRegistration.PENDING;
        }
        return UserRegistration.REGISTERED;
    }

    @Override
    public void activateAccount(String pathUUID) throws AccountActivationException{

        if(!pathUUID.equals("qqqq")){
            throw new AccountActivationException("Failed activating UUID: " + pathUUID);
        }
    }
}
