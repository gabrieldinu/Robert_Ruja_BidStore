package ro.fortech.application.bidstore.frontend.mvc.model;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import ro.fortech.application.bidstore.backend.model.BiddingUser;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;
import ro.fortech.application.bidstore.frontend.util.Paginator;

import java.util.*;

/**
 * Created by robert.ruja on 19-Apr-17.
 */
public class LazyUserDataModel extends LazyDataModel<BiddingUser> {

    private BiddingService service;

    private Paginator paginator = new Paginator();

    public LazyUserDataModel(BiddingService service) {
        this.service = service;
    }

    @Override
    public BiddingUser getRowData(String rowKey) {
        return service.getSingleBiddingUser(rowKey);
    }

    @Override
    public Object getRowKey(BiddingUser user) {
        return user.getUsername();
    }
    private List<BiddingUser> tempList;

    public List<BiddingUser> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters ){

        boolean ascending = false;
        if(sortField != null) {
            if (sortOrder.equals(SortOrder.ASCENDING))
                ascending = true;
            else
                ascending = false;


            switch (sortField) {
                case "admin":
                    sortField = "role";
                    break;
                case "enabled":
                    sortField = "userEnabled";
                    break;
                default:
            }
        }
        Map<String,Object> likeMap = new HashMap<>();
        Map<String,Object> equalMap = new HashMap<>();


        for(Map.Entry<String, Object> entry :filters.entrySet()) {
            String key = entry.getKey();
            if(key.equals("itemsPlaced") || key.equals("itemsBought") || key.equals("itemsSold"))
                equalMap.put(key, entry.getValue());
            else
                likeMap.put(key,entry.getValue());
        }
        List<BiddingUser> results = service.getBiddingUsers(sortField, ascending, likeMap,equalMap);

        paginator.compute(results.size(), pageSize);

        this.setRowCount(results.size());

        return results.subList(paginator.getStartIndex(), paginator.getEndIndex());
    }
}

