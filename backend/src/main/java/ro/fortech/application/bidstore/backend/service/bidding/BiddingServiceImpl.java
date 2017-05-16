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
            details.setInitialPrice((Double)columns[5]);
            details.setBidCount((Long)columns[6]);
            details.setBestBid(details.getBidCount() > 0 ? (Double)columns[7]:details.getInitialPrice());
            itemDetailsList.add(details);
        }

        return itemDetailsList;
    }

    @Override
    public List<ItemDetails> getItemsToSell(String sortBy, boolean ascending, User user) {
        List<ItemDetails> result = new ArrayList<>();
        for(Item item: biddingDAO.getItemsForUserToSell(sortBy,ascending,user.getUsername())){
            ItemDetails details = new ItemDetails(item);
            Object[] bidstatus = (Object[])biddingDAO.getBidStatusForItem(item, user.getUsername());
            if(bidstatus != null) {
                if(bidstatus[2] != null)
                    details.setBestBid((Double)bidstatus[2]);
                else
                    details.setBestBid(details.getInitialPrice());
                details.setBidCount((Long)bidstatus[1]);
                details.setStatus((String)bidstatus[3]);
                Object[] winner = (Object[]) biddingDAO.getWinnerForItem(item.getOwner(),details.getBestBid());
                if(winner != null) {
                    details.setWinner(winner[0] + " " + winner[1]);
                }
            }
            result.add(details);
        }
        return result;
    }

    @Override
    public List<ItemDetails> getItemsToBuy(String sortBy, boolean ascending, User user) {
        List<ItemDetails> results = new ArrayList<>();
            for(Item item: biddingDAO.getItemsForUserToBuy(sortBy,ascending,user.getUsername())){
                ItemDetails details = new ItemDetails(item);


                results.add(details);
            }
        return results;
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

    @Override
    public void saveCategory(Category category) throws BiddingException{
        if(!biddingDAO.saveCategory(category)) {
            throw new BiddingException("An error occured while trying to save category into db");
        }
    }
    @Override
    public void remove(Category category) throws BiddingException{
        if(!biddingDAO.removeCategory(category)) {
            throw new BiddingException("An error occured while trying to remove category from db");
        }
    }

    @Override
    public Category getCategoryById(Long id) {
        return biddingDAO.getCategoryById(id);
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
