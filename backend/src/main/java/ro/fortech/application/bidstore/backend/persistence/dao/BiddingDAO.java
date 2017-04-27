package ro.fortech.application.bidstore.backend.persistence.dao;

import ro.fortech.application.bidstore.backend.persistence.entity.BiddingUser;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAuth;

import java.util.List;
import java.util.Map;

/**
 * Created by robert.ruja on 20-Apr-17.
 */
public interface BiddingDAO {
    List<BiddingUser> queryForBiddingUsers(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters);

    BiddingUser getSingleBiddingUser(String rowKey);

    boolean enableBiddingUser(UserAuth auth);

    void saveBiddingUser(BiddingUser userDetails);
}
