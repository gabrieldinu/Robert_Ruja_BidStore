package ro.fortech.application.bidstore.frontend.mvc.managed.tabview.bidding;

import ro.fortech.application.bidstore.backend.exception.bidding.BiddingException;
import ro.fortech.application.bidstore.backend.persistence.entity.Bid;
import ro.fortech.application.bidstore.backend.persistence.entity.Item;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;
import ro.fortech.application.bidstore.frontend.mvc.managed.account.UserAccount;
import ro.fortech.application.bidstore.frontend.mvc.managed.tabview.ContentView;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by robert.ruja on 05-May-17.
 */

@ViewScoped
@ManagedBean(name = "bidEditBean")
public class BidEditBean implements Serializable {

    private boolean editable;

    private String bidValueText;

    @ManagedProperty(value = "#{contentView}")
    private ContentView contentView;

    @Inject
    FacesContext context;

    @Inject
    private BiddingService biddingService;

    @Inject
    private UserAccount userAccount;

    public void editBid(){
        this.editable = true;
    }

    public void discardBid() {
        this.editable = false;
    }

    public void placeBid() {
        if(bidValueText != null){
            Bid bid = contentView.getCurrentItemBid();
            Item item = contentView.getSelectedItem();
            if(bid == null) {
                bid = new Bid();
                bid.setItemId(contentView.getSelectedItem().getId());
                bid.setBidUserId(userAccount.getUser().getUsername());
            }
            bid.setBidValue(Double.parseDouble(bidValueText));
            bid.setBidDate(new Timestamp(System.currentTimeMillis()));
            item.addBid(bid);
            item.setCurrentBid(bid.getBidValue());
            try {
                //biddingService.saveBid(bid);
                biddingService.saveItem(item);
            } catch(BiddingException ex) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update",
                        "An error occured while trying to update current item. Please try again later!"));
            }
            contentView.setCurrentItemBid(bid);
            this.editable = false;
        }
    }

    public void removeBid(){
        Item selectedItem = contentView.getSelectedItem();
        Bid bid = contentView.getCurrentItemBid();
        selectedItem.removeBid(bid);
        try {
            //todo: the bid does not remove from db, check why
            biddingService.saveItem(selectedItem);
        } catch(BiddingException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update",
                    "An error occured while trying to update current item. Please try again later!"));
        }
        contentView.setCurrentItemBid(null);
    }

    public String getBidValueText() {
        return bidValueText;
    }

    public void setBidValueText(String bidValueText) {
        this.bidValueText = bidValueText;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public ContentView getContentView() {
        return contentView;
    }

    public void setContentView(ContentView contentView) {
        this.contentView = contentView;
    }

    public BiddingService getBiddingService() {
        return biddingService;
    }

    public void setBiddingService(BiddingService biddingService) {
        this.biddingService = biddingService;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
