package ro.fortech.application.bidstore.backend.service.bidding;

import ro.fortech.application.bidstore.backend.persistence.entity.BiddingUser;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;

/**
 * Created by robert.ruja on 19-Apr-17.
 */

@Local
public interface UserBiddingService {

    List<BiddingUser> getBiddingUsers(int first, int pageSize, String sortField, String sortOrder, Map<String,Object> filters);

    BiddingUser getSingleBiddingUser(String rowKey);
}
