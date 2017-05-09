package ro.fortech.application.bidstore.backend.model;

import ro.fortech.application.bidstore.backend.persistence.entity.User;

/**
 * Created by coco on 06-May-17.
 */
public class BiddingUser extends User {

    private long itemsPlaced;
    private long itemsSold;
    private long itemsBought;

    public BiddingUser() {

    }

    public long getItemsPlaced() {
        return itemsPlaced;
    }

    public void setItemsPlaced(long itemsPlaced) {
        this.itemsPlaced = itemsPlaced;
    }

    public long getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(long itemsSold) {
        this.itemsSold = itemsSold;
    }

    public long getItemsBought() {
        return itemsBought;
    }

    public void setItemsBought(long itemsBought) {
        this.itemsBought = itemsBought;
    }
}
