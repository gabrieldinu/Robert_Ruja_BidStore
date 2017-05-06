package ro.fortech.application.bidstore.backend.service.bidding;

import ro.fortech.application.bidstore.backend.model.BiddingUser;
import ro.fortech.application.bidstore.backend.persistence.dao.BiddingDAO;
import ro.fortech.application.bidstore.backend.persistence.dao.UserDAO;
import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.backend.persistence.entity.Item;
import ro.fortech.application.bidstore.backend.persistence.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by robert.ruja on 19-Apr-17.
 */

@Stateless
public class BiddingServiceImpl implements BiddingService {

    @Inject
    private BiddingDAO biddingDAO;

    @Inject
    private UserDAO userDAO;

    @Override
    public List<BiddingUser> getBiddingUsers(int first, int pageSize, String sortField, String sortOrder, Map<String, Object> filters) {

        //todo: deal with sorting and filtering problem
        List<BiddingUser> biddingUserList = new ArrayList<>();

        for(User user: userDAO.getUserList()) {

            biddingUserList.add(getSingleBiddingUser(user));
        }
        return biddingUserList;

    }

    @Override
    public BiddingUser getSingleBiddingUser(User user) {
        BiddingUser biddingUser = new BiddingUser(user);
        biddingUser.setItemsBought(biddingDAO.getBoughtItemCount(user.getUsername()));
        biddingUser.setItemsSold(biddingDAO.getSoldItemCount(user.getUsername()));
        biddingUser.setItemsPlaced(biddingDAO.getPlacedItemCount(user.getUsername()));
        return biddingUser;
    }

    @Override
    public BiddingUser getSingleBiddingUser(String username) {
        return getSingleBiddingUser(userDAO.getUserDetails(username));
    }

    @Override
    public List<Category> getCategoriesWithParentId(Long id) {
        return biddingDAO.getCategoriesWithParentId(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return biddingDAO.getAllCategories();
    }

    public Category getRoot() {
        return biddingDAO.getRoot();
    }

    @Override
    public List<Item> getFullItemList() {
        return biddingDAO.getFullItemList();
    }
}
