package ro.fortech.application.bidstore.frontend.mvc.managed.tabview.bidding;

import ro.fortech.application.bidstore.backend.persistence.entity.Bid;
import ro.fortech.application.bidstore.backend.service.bidding.BiddingService;
import ro.fortech.application.bidstore.frontend.mvc.managed.account.UserAccount;
import ro.fortech.application.bidstore.frontend.mvc.managed.tabview.ContentView;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.sql.Timestamp;

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
            Bid bid = new Bid();


            bid.setBidValue(Double.parseDouble(bidValueText));
            bid.setBidDate(new Timestamp(System.currentTimeMillis()));
            bid.setItemId(contentView.getSelectedItem().getId());
            bid.setBidUserId(userAccount.getUser().getUsername());
            //TODO:save bid in db

            //todo: find a way to update the item currentBid if current bid is max
            contentView.setCurrentItemBid(bid);
            this.editable = false;
        }
    }

    public void removeBid(){
        //todo: remove bid from db
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
