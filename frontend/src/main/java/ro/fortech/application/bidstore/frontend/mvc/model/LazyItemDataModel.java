package ro.fortech.application.bidstore.frontend.mvc.model;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import ro.fortech.application.bidstore.backend.model.GenericDTO;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;
import ro.fortech.application.bidstore.frontend.mvc.managed.account.UserAccount;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

/**
 * Created by robert.ruja on 25-May-17.
 */

public class LazyItemDataModel extends LazyDataModel{

    private BiddingService service;

    private UserAccount userAccount;

    private String content;

    public LazyItemDataModel(String content) {

    }

    @Override
    public List load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
        GenericDTO genericDTO = new GenericDTO();
        genericDTO.setAscending(sortOrder.equals(SortOrder.ASCENDING));
        genericDTO.setFirstElement(first);
        genericDTO.setSortField(sortField);
        genericDTO.setElementCount(pageSize);
        if(content.equals("sell")){
            service.getItemsToSell(genericDTO, userAccount.getUser());
            super.setRowCount(genericDTO.getTotalCount());
            return genericDTO.getRows();
        } else {
            return service.getItemsToBuy(genericDTO,userAccount.getUser()).getRows();
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BiddingService getService() {
        return service;
    }

    public void setService(BiddingService service) {
        this.service = service;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
