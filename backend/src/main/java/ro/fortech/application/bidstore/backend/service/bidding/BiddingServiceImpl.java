package ro.fortech.application.bidstore.backend.service.bidding;

import ro.fortech.application.bidstore.backend.exception.bidding.BiddingException;
import ro.fortech.application.bidstore.backend.model.*;
import ro.fortech.application.bidstore.backend.persistence.dao.BiddingDAO;
import ro.fortech.application.bidstore.backend.persistence.dao.UserDAO;
import ro.fortech.application.bidstore.backend.persistence.entity.Bid;
import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.backend.persistence.entity.Item;
import ro.fortech.application.bidstore.backend.persistence.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
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
    public List<BiddingUser> getBiddingUsers(String sortField, boolean ascending, Map<String, Object> likeFilters, Map<String, Object> equalFilters) {

        List<BiddingUser> users = new ArrayList<>();
        for(Object o: userDAO.getUserList(sortField, ascending, likeFilters, equalFilters)){
            Object[] columns = (Object[])o;
            BiddingUser user = new BiddingUser();
            user.setUsername((String)columns[0]);
            user.setFirstName((String)columns[1]);
            user.setLastName((String)columns[2]);
            user.setEmail((String)columns[3]);
            user.setRole((UserRole)columns[4]);
            user.setUserEnabled((UserEnabled)columns[5]);
            user.setItemsPlaced((Long)columns[6]);
            user.setItemsSold((Long)columns[7]);
            user.setItemsBought((Long)columns[8]);
            users.add(user);
        }
        return users;
    }

    @Override
    public BiddingUser getSingleBiddingUser(User user) {
        List<BiddingUser> results = getBiddingUsers(null, false, null, new HashMap<String,Object>(){{put("username",user.getUsername());}});
        if(!results.isEmpty()) {
            return results.get(0);
        }
        return null;
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
    public List<String> getCategoriesNameCointains(String query) {
        return biddingDAO.getCategoriesNameContains(query);
    }

    @Override
    public List<String> getItemsNameCointains(String query) {
        return biddingDAO.getItemsNameContains(query);
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

    @Override
    public List<ItemDetails> getItems(Category category, String sortBy, boolean ascending, String searchFilter, Map<String, Object> filters) {
        List<Long> categoryIds = new ArrayList<>();
        categoryIds.add(category.getId());
        populateCategoryChildrenIds(categoryIds,category);

        List<ItemDetails> itemDetailsList = new ArrayList<>();

        for(Object o: biddingDAO.getItems(categoryIds, sortBy,ascending,searchFilter,filters)) {
            //build item details
            Object[] columns = (Object[])o;
            ItemDetails details = new ItemDetails();
            details.setItemId((Long)columns[0]);
            details.setItemName((String)columns[1]);
            details.setDescription((String)columns[2]);
            details.setOpeningDate((Date) columns[3]);
            details.setClosingDate((Date) columns[4]);
            details.setStatus((BidStatus) columns[5]);
            details.setInitialPrice((Double)columns[6]);
            details.setBidCount((Long)columns[7]);
            details.setBestBid(details.getBidCount() > 0 ? (Double)columns[8]:details.getInitialPrice());
            itemDetailsList.add(details);
        }

        return itemDetailsList;
    }

    @Override
    public void saveItem(Item item) throws BiddingException{
        if(!biddingDAO.saveItem(item))
            throw new BiddingException("An error occured while trying to save item in the database!");
    }

    @Override
    public void saveBid(Bid bid) throws BiddingException {
        if(!biddingDAO.saveBid(bid))
            throw new BiddingException("An error occured while trying to save the new bid");
    }

    @Override
    public void removeBid(Bid bid) throws BiddingException{
        if(!biddingDAO.removeBid(bid))
            throw new BiddingException("An error occured while trying to remove the selected bid");
    }

    @Override
    public Item getItemWithId(Long itemId) {
        return biddingDAO.getItemWithId(itemId);
    }

    @Override
    public Bid getBidForItem(Long itemId, String username) {
        return biddingDAO.getBidForItem(itemId,username);
    }

    @Override
    public List<Category> getCategories(String sortBy, boolean ascending, String searchText) {
        return biddingDAO.getCategories(sortBy, ascending, searchText);
    }

    private void populateCategoryChildrenIds(List<Long> categoryIds, Category category){

        if(category.getChildren() == null)
            return;
        for(Category child: category.getChildren()){
            categoryIds.add(child.getId());
            populateCategoryChildrenIds(categoryIds,child);
        }
    }
}
