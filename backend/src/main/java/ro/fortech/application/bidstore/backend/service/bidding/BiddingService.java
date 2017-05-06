package ro.fortech.application.bidstore.backend.service.bidding;

import ro.fortech.application.bidstore.backend.model.BiddingUser;
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

    List<BiddingUser> getBiddingUsers(int first, int pageSize, String sortField, String sortOrder, Map<String,Object> filters);

    BiddingUser getSingleBiddingUser(User user);

    BiddingUser getSingleBiddingUser(String username);

    List<Category> getCategoriesWithParentId(Long id);

    List<Category> getAllCategories();

    Category getRoot();

    List<Item> getFullItemList();
}