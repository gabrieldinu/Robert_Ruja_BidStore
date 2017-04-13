package ro.fortech.application.bidstore.backend.persisetence.dao;

import ro.fortech.application.bidstore.backend.persisetence.entity.User;
import ro.fortech.application.bidstore.backend.persisetence.entity.UserAuth;

/**
 * Created by robert.ruja on 10-Apr-17.
 */

public interface UserDAO {

    UserAuth getUserAuthentication(String username);

    User getUser(User user);

    void saveUser(UserAuth userAuth, User user);

}
