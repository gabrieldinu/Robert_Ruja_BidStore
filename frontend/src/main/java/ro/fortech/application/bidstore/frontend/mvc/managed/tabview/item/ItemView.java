package ro.fortech.application.bidstore.frontend.mvc.managed.tabview.item;

import org.primefaces.model.LazyDataModel;
import ro.fortech.application.bidstore.backend.exception.bidding.BiddingException;
import ro.fortech.application.bidstore.backend.model.ItemDetails;
import ro.fortech.application.bidstore.backend.persistence.entity.Item;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;
import ro.fortech.application.bidstore.frontend.mvc.managed.account.UserAccount;
import ro.fortech.application.bidstore.frontend.mvc.model.LazyItemDataModel;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.sql.Date;
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

    @Inject
    private FacesContext context;

    @PostConstruct
    public void init(){
        this.lazyItemDataModel = new LazyItemDataModel(content);
        this.lazyItemDataModel.setService(this.service);
        this.lazyItemDataModel.setContent(this.content);
        this.lazyItemDataModel.setUserAccount(userAccount);
    }
    private LazyItemDataModel lazyItemDataModel;

    private String content = "sell";

    public BiddingService getService() {
        return service;
    }

    public void abandon(ItemDetails details) {
        Item item = service.getItemWithId(details.getItemId());
        item.setClosingDate(new Date(0L));
        if(details.getStatus().equals("NOT YET OPEN")){
            try {
                service.removeItem(item);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Save item",
                        "The item was successfully removed"));
            } catch (BiddingException e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save item",
                        "There was a problem while trying to remove the item from db. Please try again later!"));
            }
        }else{
            saveItem(item);
        }
    }

    public void close(ItemDetails details) {
        Item item = service.getItemWithId(details.getItemId());
        item.setClosingDate(details.getOpeningDate());
        saveItem(item);
    }

    private void saveItem(Item item) {
        try {
            service.saveItem(item);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Save item",
                    "The item was successfully saved"));
        } catch (BiddingException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save item",
                    "There was a problem while trying to save the item into db. Please try again later!"));
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
        this.lazyItemDataModel.setContent(content);
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public LazyItemDataModel getLazyItemDataModel() {
        return lazyItemDataModel;
    }

    public void setLazyItemDataModel(LazyItemDataModel lazyItemDataModel) {
        this.lazyItemDataModel = lazyItemDataModel;
    }
}
