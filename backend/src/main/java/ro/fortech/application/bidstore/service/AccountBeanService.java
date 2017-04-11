package ro.fortech.application.bidstore.service;

import ro.fortech.application.bidstore.persisetence.entity.User;
import ro.fortech.application.bidstore.persisetence.entity.UserAuth;

import javax.ejb.Local;

/**
 * Created by robert.ruja on 10-Apr-17.
 */
@Local
public interface AccountBeanService {

    String showUser();

    UserAuth getAutenticatedUser(String user, String password);
}
