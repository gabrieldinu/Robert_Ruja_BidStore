package ro.fortech.application.bidstore.persisetence.dao;

import ro.fortech.application.bidstore.persisetence.entity.User;
import ro.fortech.application.bidstore.persisetence.entity.UserAuth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Created by robert.ruja on 10-Apr-17.
 */

public interface UserDAO {

    User getUser(String username);

    UserAuth getUserAuthentication(String username, String password);
}
