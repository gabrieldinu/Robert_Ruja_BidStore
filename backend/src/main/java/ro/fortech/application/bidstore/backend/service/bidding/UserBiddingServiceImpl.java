package ro.fortech.application.bidstore.backend.service.bidding;

import ro.fortech.application.bidstore.backend.persistence.dao.BiddingDAO;
import ro.fortech.application.bidstore.backend.persistence.entity.BiddingUser;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by robert.ruja on 19-Apr-17.
 */

@Stateless
public class UserBiddingServiceImpl implements UserBiddingService {

    @Inject
    private BiddingDAO biddingDAO;

    @Override
    public List<BiddingUser> getBiddingUsers(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters) {

        return biddingDAO.queryForBiddingUsers(first, pageSize, sortField, sortOrder, filters);

    }

    @Override
    public BiddingUser getSingleBiddingUser(String username) {
        return biddingDAO.getSingleBiddingUser(username);
    }
}
