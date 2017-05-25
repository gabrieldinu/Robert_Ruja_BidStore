package ro.fortech.application.bidstore.backend.service.bidding;

import ro.fortech.application.bidstore.backend.exception.bidding.BiddingException;
import ro.fortech.application.bidstore.backend.model.*;
import ro.fortech.application.bidstore.backend.persistence.dao.BiddingDAO;
import ro.fortech.application.bidstore.backend.persistence.dao.UserDAO;
import ro.fortech.application.bidstore.backend.persistence.dao.UserManagementDAO;
import ro.fortech.application.bidstore.backend.persistence.entity.Bid;
import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.backend.persistence.entity.Item;
import ro.fortech.application.bidstore.backend.persistence.entity.User;

import javax.annotation.PostConstruct;
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

    @Inject
    private UserManagementDAO userManagementDAO;

    @PostConstruct
    public void init() {

    }

    @Override
    public List<BiddingUser> getBiddingUsers(String sortField, boolean ascending, Map<String, Object> likeFilters, Map<String, Object> equalFilters) {
        return userManagementDAO.getUserList();
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
        List<Category> result = new ArrayList<Category>(){{
            add(getRoot());
            addAll(biddingDAO.getAllCategories());
        }
    };
        return result;
    }

    public Category getRoot() {
        Category root = new Category();
        root.setId(0L);
        root.setParentId(null);
        root.setName("All");
        root.setChildren(biddingDAO.getRootCategories());
        return root;
    }

    @Override
    public List<Item> getFullItemList() {
        return biddingDAO.getFullItemList();
    }

    @Override
    public List<ItemDetails> getItems(Category category, String sortBy, boolean ascending, String searchFilter, Map<String, Object> filters) {

        List<ItemDetails> itemDetailsList = new ArrayList<>();

        for(Object o: biddingDAO.getItems(category.getId(), sortBy,ascending,searchFilter,filters)) {
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
            details.setImageUrl((String)columns[8]);
            details.setOwner((String)columns[9]);
            itemDetailsList.add(details);
        }
        return itemDetailsList;
    }

    @Override
    public GenericDTO<ItemDetails> getItemsToSell(GenericDTO<ItemDetails> genericDTO, User user) {
        for(ItemDetails itemDetails : biddingDAO.getItemsForUserToSell(genericDTO,user.getUsername()).getRows()) {
            //set best bid as initial price if null
            if(itemDetails.getBestBid() == null)
                itemDetails.setBestBid(itemDetails.getInitialPrice());

            //set the winner
            if (itemDetails.getStatus().equals("CLOSED") && itemDetails.getBidCount() > 0){
                Object[] winner = (Object[]) biddingDAO.getWinnerForItem(itemDetails.getItemId(), itemDetails.getBestBid());
                if (winner != null) {
                    itemDetails.setWinnerId((String)winner[0]);
                    itemDetails.setWinner(winner[1] + " " + winner[2]);
                }
            }
            //set the categories for item
            itemDetails.setAllCategories(biddingDAO.getCategoriesForItem(itemDetails.getItemId()));
        }
        return genericDTO;
    }

    @Override
    public GenericDTO<ItemDetails> getItemsToBuy(GenericDTO<ItemDetails> genericDTO, User user) {
        List<ItemDetails> result = new ArrayList<>();
        for(Object o: biddingDAO.getItemsForUserToBuy(null,false,user.getUsername())){
            ItemDetails details = new ItemDetails();
            Object[] oDetails = (Object[])o;
            details.setItemId((Long)oDetails[0]);
            details.setItemName((String)oDetails[1]);
            details.setDescription((String)oDetails[2]);
            details.setInitialPrice((Double)oDetails[3]);
            details.setOpeningDate((Date)oDetails[4]);
            details.setClosingDate((Date)oDetails[5]);
            details.setBidCount((Long)oDetails[6]);
            Double bestBid = (Double)oDetails[7];
            if(bestBid != null) {
                details.setBestBid(bestBid);
            } else {
                details.setBestBid(details.getInitialPrice());
            }
            details.setYourBid((Double)oDetails[8]);
            details.setStatus((String) oDetails[9]);
            details.setAllCategories(biddingDAO.getCategoriesForItem(details.getItemId()));
            if (details.getStatus().equals("CLOSED") && details.getBidCount() > 0){
                Object[] winner = (Object[]) biddingDAO.getWinnerForItem(details.getItemId(), details.getBestBid());
                if (winner != null) {
                    details.setWinnerId((String)winner[0]);
                    details.setWinner(winner[1] + " " + winner[2]);
                }
            }
            result.add(details);
        }
        return null;
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
    public Map<Long, Category> getCategoriesAsMap() {
        Map<Long, Category> results = new HashMap<>();
        for(Category category : biddingDAO.getAllCategories()) {
            results.put(category.getId(), category);
        }
        return results;
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
        if(id != null && id > 0)
            return biddingDAO.getCategoryById(id);
        else
            return getRoot();
    }

    @Override
    public void removeItem(Item item) throws BiddingException {
        if(!biddingDAO.removeItem(item)) {
            throw new BiddingException("An error occured while trying to remove category from db");
        }
    }
}
