package ro.fortech.application.bidstore.backend.persistence.entity;

import ro.fortech.application.bidstore.backend.util.Formatter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by robert.ruja on 05-May-17.
 */

    @Entity
    @Table(schema = "bid_app", name = "bid")
    public class Bid {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "bid_value")
        private double bidValue;

        @Column(name = "bid_date")
        private Timestamp bidDate;

        @Column(name = "bid_user_id")
        private String bidUserId;

        @Column(name = "item_id")
        private Long itemId;

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

        public String getBidUserId() {
            return bidUserId;
        }

        public void setBidUserId(String bidUserId) {
            this.bidUserId = bidUserId;
        }

        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public double getBidValue() {
            return Formatter.formatPrice(bidValue);
        }

        public void setBidValue(double bidValue) {
            this.bidValue = bidValue;
        }
}