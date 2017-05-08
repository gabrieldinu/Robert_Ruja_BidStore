package ro.fortech.application.bidstore.backend.persistence.dao;

import ro.fortech.application.bidstore.backend.model.BiddingUser;
import ro.fortech.application.bidstore.backend.persistence.entity.Bid;
import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.backend.persistence.entity.Item;
import ro.fortech.application.bidstore.backend.persistence.entity.UserAuth;

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

    long getBoughtItemCount(String username);

    long getSoldItemCount(String username);

    long getPlacedItemCount(String username);

    List<Item> getFullItemList();

    List<String> getCategoriesNameContains(String query);

    List<Item> getItems(List<Long> categoryIds, int maxResults, String sortBy, boolean ascending, String searchFilter);

    List<String> getItemsNameContains(String query);

    boolean saveItem(Item item);

    boolean saveBid(Bid bid);

    boolean removeBid(Bid bid);
}
