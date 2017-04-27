package ro.fortech.application.bidstore.backend.service.account;

import ro.fortech.application.bidstore.backend.exception.account.AccountActivationException;
import ro.fortech.application.bidstore.backend.exception.account.AccountEmailException;
import ro.fortech.application.bidstore.backend.exception.account.AccountException;
import ro.fortech.application.bidstore.backend.model.UserRegistration;
import ro.fortech.application.bidstore.backend.persistence.dao.BiddingDAO;
import ro.fortech.application.bidstore.backend.persistence.dao.UserDAO;
import ro.fortech.application.bidstore.backend.persistence.entity.User;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAddress;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAuth;
import ro.fortech.application.bidstore.backend.util.PasswordDigest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;


/**
 * Created by robert.ruja on 10-Apr-17.
 */

@Named
@Stateless
public class UserAccountServiceImpl implements UserAccountService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private BiddingDAO biddingDAO;

    @Override
    public User getUserDetails(User user) {

        return userDAO.getUserDetails(user);
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

        if(getRegisterStatus(userAuth) == UserRegistration.REGISTERED ) {
            throw new AccountException("Username already taken");
        }

        if(checkExistingEmail(user))
            throw new AccountEmailException("Email already exists in the DB");

        UUID uuid = UUID.randomUUID();

        //DigestUserPassword
        userAuth.setPassword(PasswordDigest.digestPassword(userAuth.getPassword()));
        userAuth.setActivationToken(uuid.toString());

        //set expiration date of the account to 24h
        userAuth.setExpiringDate(new Timestamp(System.currentTimeMillis()+86400000));

        if(!userDAO.saveUserInfo(userAuth,user)){
            throw new AccountException("Failed to insert user into database");
        }

        return uuid;
    }

    @Override
    public void updateUserDetails(User user) throws AccountException {
        if(!userDAO.saveUserInfo(user)){
            throw new AccountException("Unable to save user details into database");
        }
    }

    @Override
    public void updateUserAddress(Map<String,UserAddress> addressMap) throws AccountException {
        if(!userDAO.saveUserAddress(addressMap)) {
            throw new AccountException("Unable to save user address into database");
        }
    }

    private boolean checkExistingEmail(User user) {
        return userDAO.getByEmail(user) != null;
    }

    @Override
    public UserRegistration getRegisterStatus(UserAuth userAuth) {

        UserAuth userAuthFromDB = userDAO.getUserAuthentication(userAuth.getUsername());

        if(userAuthFromDB == null) {

            return UserRegistration.UNREGISTERED;

        }else if (userAuthFromDB.getActivationToken() != null){

            return UserRegistration.PENDING;
        }
        return UserRegistration.REGISTERED;
    }

    @Override
    public void activateAccount(String uuid) throws AccountException {

        UserAuth userAuth;
            try {
                userAuth = userDAO.getUserAuthenticationByActivationToken(uuid);
            } catch(Exception ex) {
                throw new AccountException(ex);
            }

            if (userAuth != null){
                userAuth.setActivationToken(null);
                userAuth.setExpiringDate(null);
                if(!biddingDAO.enableBiddingUser(userAuth))
                    throw new AccountActivationException("Failed to activate account. Problem with enabling bidding user (DB save)");

            }else{
                throw new AccountActivationException("Failed activating user with UUID: " + userAuth.getUuid());
            }
    }

    @Override
    public String resetPassword(User user) throws AccountException{

        User userFromDB = userDAO.getByEmail(user);
        if(userFromDB != null ) {
            UserAuth userAuthFromDB = userDAO.getUserAuthentication(userFromDB.getUsername());

            //16 character random String
            String token = Long.toHexString(Double.doubleToLongBits(Math.random()));
            userAuthFromDB.setResetToken(PasswordDigest.digestPassword(token));
            userDAO.saveUserAuthentication(userAuthFromDB);
            return token;
        } else {
            throw new AccountException("No user found with this eamil");
        }
    }

    @Override
    public UserAuth getRequestChangePassowrd(String token) {

        return userDAO.getUserAuthenticationByResetToken(PasswordDigest.digestPassword(token));
    }

    @Override
    public void changeUserAuthentication(UserAuth userAuth) throws AccountException {
        userAuth.setPassword(PasswordDigest.digestPassword(userAuth.getPassword()));
        userAuth.setResetToken(null);
        try {
            userDAO.saveUserAuthentication(userAuth);
        } catch (Exception ex) {
            throw new AccountException(ex);
        }
    }

    @Override
    public UserAuth getAuthenticationByUUID(String cookieValue) {
        return userDAO.getUserAuthenticationByUUID(cookieValue);
    }

    @Override
    public void enableUser(String managedUsername) throws AccountException {
        if(!userDAO.setUserEnabled(managedUsername,true))
            throw new AccountException("Error while enabling the user");
    }

    @Override
    public void disableUser(String managedUsername) throws AccountException {
        if(!userDAO.setUserEnabled(managedUsername,false))
            throw new AccountException("Error while disabling the user");
    }

    @Override
    public Map<String, UserAddress> getUserAddressMap(User user) {
        return userDAO.getUserAddressDetails(user);
    }

}
