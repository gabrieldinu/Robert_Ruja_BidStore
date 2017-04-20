package ro.fortech.application.bidstore.backend.persisetence.entity;

import ro.fortech.application.bidstore.backend.model.UserEnabled;
import ro.fortech.application.bidstore.backend.model.UserRole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by robert.ruja on 19-Apr-17.
 */

@Entity
@Table(name = "BID_INFO")
public class BiddingUser extends User {

    @Column(name = "item_placed")
    private Long itemsPlaced;
    @Column(name = "item_sold")
    private Long itemsSold;
    @Column(name = "item_bought")
    private Long itemsBought;

    public BiddingUser(String username, String firstName, String lastName, String email, UserRole role, UserEnabled enabled, Long itemsPlaced, Long itemsSold, Long itemsBought) {
        super(username, firstName, lastName, email, role, enabled);
        this.itemsPlaced = itemsPlaced;
        this.itemsSold = itemsSold;
        this.itemsBought = itemsBought;
    }

    public BiddingUser() {

    }

    public Long getItemsPlaced() {
        return itemsPlaced;
    }

    public void setItemsPlaced(Long itemsPlaced) {
        this.itemsPlaced = itemsPlaced;
    }

    public Long getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(Long itemsSold) {
        this.itemsSold = itemsSold;
    }

    public Long getItemsBought() {
        return itemsBought;
    }

    public void setItemsBought(Long itemsBought) {
        this.itemsBought = itemsBought;
    }
}
