package ro.fortech.application.bidstore.backend.persistence.dao;

import ro.fortech.application.bidstore.backend.model.BiddingUser;
import ro.fortech.application.bidstore.backend.persistence.entity.Bid;
import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.backend.persistence.entity.Item;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAuth;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by robert.ruja on 20-Apr-17.
 */
public interface BiddingDAO {

    BiddingUser getSingleBiddingUser(String username);

    boolean enableBiddingUser(UserAuth auth);

    List<Category> getCategoriesWithParentId(Long id);

    List<Category> getAllCategories();

    Category getRoot();

    List<Item> getFullItemList();

    List<String> getCategoriesNameContains(String query);

    List getItems(List<Long> categoryIds, String sortBy, boolean ascending, String searchText, Map<String, Object> searchFilter);

    List<String> getItemsNameContains(String query);

    boolean saveItem(Item item);

    boolean saveBid(Bid bid);

    boolean removeBid(Bid bid);

    Item getItemWithId(Long itemId);

    Bid getBidForItem(Long itemId, String username);

    List<Category> getCategories(String sortBy, boolean ascending, String searchText);

    boolean saveCategory(Category category);

    @Transactional
    boolean removeCategory(Category category);

    Category getCategoryById(Long id);
}
