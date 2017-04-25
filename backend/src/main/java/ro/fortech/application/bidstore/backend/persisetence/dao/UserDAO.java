package ro.fortech.application.bidstore.backend.persisetence.dao;

import ro.fortech.application.bidstore.backend.exception.AccountException;
import ro.fortech.application.bidstore.backend.persisetence.entity.User;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAddress;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAuth;

import java.util.Date;
import java.util.Map;

/**
 * Created by robert.ruja on 10-Apr-17.
 */

public interface UserDAO {

    UserAuth getUserAuthentication(String username);

    User getUserDetails(User user);

    boolean saveUserInfo(UserAuth userAuth, User user);

    boolean saveUserInfo(User user);

    UserAuth getUserAuthenticationByActivationToken(String uuid);

    boolean saveUserAuthentication(UserAuth userAuth);

    void deleteUserWithExpiredDate(Date date);

    User getByEmail(User user);

    UserAuth getUserAuthenticationByResetToken(String s);

    UserAuth getUserAuthenticationByUUID(String cookieValue);

    boolean setUserEnabled(String managedUsername, boolean b);

    boolean saveUserAddress(Map<String,UserAddress> userAddressMap);

    Map<String,UserAddress> getUserAddressDetails(User user);
}
