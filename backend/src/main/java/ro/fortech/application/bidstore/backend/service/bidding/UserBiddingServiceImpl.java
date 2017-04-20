package ro.fortech.application.bidstore.backend.service.bidding;

import ro.fortech.application.bidstore.backend.model.UserEnabled;
import ro.fortech.application.bidstore.backend.model.UserRole;
import ro.fortech.application.bidstore.backend.persisetence.dao.BiddingDAO;
import ro.fortech.application.bidstore.backend.persisetence.entity.BiddingUser;
import ro.fortech.application.bidstore.backend.persisetence.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
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

        return biddingDAO.queryForBiddingUsers(first,pageSize,sortField,sortField,filters);

    }

    @Override
    public BiddingUser getSingleBiddingUser(String rowKey) {
        return biddingDAO.getSingleBiddingUser(rowKey);
    }
}
