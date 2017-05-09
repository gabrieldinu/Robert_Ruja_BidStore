package ro.fortech.application.bidstore.backend.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by robert.ruja on 09-May-17.
 */

@Entity
@Table(name = "item_sold_bought")
public class ItemSoldBought {

    @Id
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "winner_id")
    private String winnerId;

    @Column(name = "seller_id")
    private String sellerId;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
