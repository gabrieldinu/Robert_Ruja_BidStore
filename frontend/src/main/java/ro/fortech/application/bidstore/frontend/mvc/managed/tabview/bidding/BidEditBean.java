package ro.fortech.application.bidstore.frontend.mvc.managed.tabview.bidding;

import ro.fortech.application.bidstore.backend.exception.bidding.BiddingException;
import ro.fortech.application.bidstore.backend.model.ItemDetails;
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
import java.util.HashMap;

/**
 * Created by robert.ruja on 05-May-17.
 */

@ViewScoped
@ManagedBean(name = "bidEditBean")
public class BidEditBean implements Serializable {

    private boolean editable;

    private String bidValueText;

    private Item selectedItem;

    private ItemDetails selectedItemDetails;

    private Bid currentItemBid;

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

            if(currentItemBid == null) {
                currentItemBid = new Bid();
                currentItemBid.setItemId(selectedItemDetails.getItemId());
                currentItemBid.setBidUserId(userAccount.getUser().getUsername());
            }
            currentItemBid.setBidValue(Double.parseDouble(bidValueText));
            currentItemBid.setBidDate(new Timestamp(System.currentTimeMillis()));
            try {
                biddingService.saveBid(currentItemBid);
            } catch(BiddingException ex) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update",
                        "An error occured while trying to update current item. Please try again later!"));
            }
            renderSingleItem(selectedItemDetails.getItemId());
            this.editable = false;
        }
    }

    public void removeBid(){

        try {
            biddingService.removeBid(currentItemBid);
        } catch(BiddingException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update",
                    "An error occured while trying to remove current bid. Please try again later!"));
        }
        renderSingleItem(selectedItemDetails.getItemId());
    }

    public void renderSingleItem(Long itemId){
        this.selectedItemDetails = biddingService.getItems(
                contentView.getTreeBean().getSelectedCategory(),
                null,
                false,
                null,
                new HashMap<String,Object>(){
                    {
                        put("id",itemId);
                    }
                }

        ).get(0);
        this.currentItemBid = biddingService.getBidForItem(itemId, userAccount.getUser().getUsername());

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

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public ItemDetails getSelectedItemDetails() {
        return selectedItemDetails;
    }

    public void setSelectedItemDetails(ItemDetails selectedItemDetails) {
        this.selectedItemDetails = selectedItemDetails;
    }

    public Bid getCurrentItemBid() {
        return currentItemBid;
    }

    public void setCurrentItemBid(Bid currentItemBid) {
        this.currentItemBid = currentItemBid;
    }
}
