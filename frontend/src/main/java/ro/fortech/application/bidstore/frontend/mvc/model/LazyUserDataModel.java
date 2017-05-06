package ro.fortech.application.bidstore.frontend.mvc.model;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import ro.fortech.application.bidstore.backend.model.BiddingUser;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;

import java.util.*;

/**
 * Created by robert.ruja on 19-Apr-17.
 */
public class LazyUserDataModel extends LazyDataModel<BiddingUser> {

    private BiddingService service;

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
    @Override
    public List<BiddingUser> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {

        String sort;
        switch (sortOrder){
            case DESCENDING: sort = "DESC";
            break;
            case ASCENDING: sort = "ASC";
            break;
            default: sort = null;
        }
        if(sortField != null)
            switch (sortField){
                case "admin": sortField = "role";
                    break;
                case "enabled": sortField = "userEnabled";
                    break;
                default:
            }
       this.tempList = service.getBiddingUsers(first,pageSize, sortField, sort, filters);

        this.setRowCount(tempList.size());

        return tempList;
    }
}

