package ro.fortech.application.bidstore.backend.persistence.dao;

import ro.fortech.application.bidstore.backend.model.BiddingUser;

import java.util.List;

/**
 * Created by robert.ruja on 23-May-17.
 */
public interface UserManagementDAO {

    List<BiddingUser> getUserList();
}
