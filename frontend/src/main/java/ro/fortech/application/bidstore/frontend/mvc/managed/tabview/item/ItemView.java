package ro.fortech.application.bidstore.frontend.mvc.managed.tabview.item;

import ro.fortech.application.bidstore.backend.model.ItemDetails;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;
import ro.fortech.application.bidstore.frontend.mvc.managed.account.UserAccount;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

/**
 * Created by coco on 14-May-17.
 */

@ManagedBean
@ViewScoped
public class ItemView implements Serializable {

    @Inject
    private BiddingService service;

    @Inject
    private UserAccount userAccount;

    private String content = "sell";

    public BiddingService getService() {
        return service;
    }

    public List<ItemDetails> getItems() {
        if(content.equals("sell")){
            return service.getItemsToSell(null,false,userAccount.getUser());
        } else {
            return service.getItemsToBuy(null,false,userAccount.getUser());
        }
    }

    public void setService(BiddingService service) {
        this.service = service;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
