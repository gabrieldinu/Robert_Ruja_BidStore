package ro.fortech.application.bidstore.backend.persistence.entity;

import java.sql.Timestamp;

/**
 * Created by robert.ruja on 05-May-17.
 */
public class Bid {

    private Long id;

    private Timestamp bidDate;

    private Item item;

    private BiddingUser user;

    private double bidValue;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getBidDate() {
        return bidDate;
    }

    public void setBidDate(Timestamp bidDate) {
        this.bidDate = bidDate;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BiddingUser getUser() {
        return user;
    }

    public void setUser(BiddingUser user) {
        this.user = user;
    }

    public double getBidValue() {
        return bidValue;
    }

    public void setBidValue(double bidValue) {
        this.bidValue = bidValue;
    }
}
