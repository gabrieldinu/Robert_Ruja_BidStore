package ro.fortech.application.bidstore.backend.persisetence.dao;

import ro.fortech.application.bidstore.backend.persisetence.entity.User;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAuth;

import java.util.Date;

/**
 * Created by robert.ruja on 10-Apr-17.
 */

public interface UserDAO {

    UserAuth getUserAuthentication(String username);

    User getUserDetails(User user);

    void saveUserInfo(UserAuth userAuth, User user);

    UserAuth getUserAuthenticationByUUID(String uuid);

    void saveUserAuthentication(UserAuth userAuth);

    void deleteUserWithExpiredDate(Date date);
}
