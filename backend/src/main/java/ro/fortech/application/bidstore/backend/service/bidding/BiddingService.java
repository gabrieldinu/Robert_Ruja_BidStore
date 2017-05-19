package ro.fortech.application.bidstore.backend.service.bidding;

import ro.fortech.application.bidstore.backend.exception.bidding.BiddingException;
import ro.fortech.application.bidstore.backend.model.BiddingUser;
import ro.fortech.application.bidstore.backend.model.ItemDetails;
import ro.fortech.application.bidstore.backend.persistence.entity.Bid;
import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.backend.persistence.entity.Item;
import ro.fortech.application.bidstore.backend.persistence.entity.User;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;

/**
 * Created by robert.ruja on 19-Apr-17.
 */

@Local
public interface BiddingService {

    List<BiddingUser> getBiddingUsers(String sortField, boolean ascending, Map<String, Object> likeFilters, Map<String, Object> equalFilters);

    BiddingUser getSingleBiddingUser(User user);

    BiddingUser getSingleBiddingUser(String username);

    List<Category> getCategoriesWithParentId(Long id);

    List<String> getCategoriesNameCointains(String query);

    List<String> getItemsNameCointains(String query);

    List<Category> getAllCategories();

    Category getRoot();

    List<Item> getFullItemList();

    List<ItemDetails> getItems(Category category, String sortBy, boolean ascending, String searchFilter, Map<String, Object> filterMap);

    List<ItemDetails> getItemsToSell(String sortBy, boolean ascending, User user);

    List<ItemDetails> getItemsToBuy(String sortBy, boolean ascending, User user);

    void saveItem(Item item) throws BiddingException;

    void saveBid(Bid bid) throws BiddingException;

    void removeBid(Bid bid) throws BiddingException;

    Item getItemWithId(Long itemId);

    Bid getBidForItem(Long itemId, String username);

    List<Category> getCategories(String sortBy, boolean ascending, String searchText);

    Map<Long, Category> getCategoriesAsMap();

    void saveCategory(Category category) throws BiddingException;

    void remove(Category category) throws BiddingException;

    Category getCategoryById(Long id);

    void removeItem(Item item) throws BiddingException;
}
