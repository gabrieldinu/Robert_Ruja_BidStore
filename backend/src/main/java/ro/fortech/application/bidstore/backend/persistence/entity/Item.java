package ro.fortech.application.bidstore.backend.persistence.entity;

import ro.fortech.application.bidstore.backend.model.BidStatus;
import ro.fortech.application.bidstore.backend.util.Formatter;

import javax.persistence.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(schema = "bid_app", name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "initial_price")
    private Double initialPrice;

    @Column(name = "current_price")
    private Double currentBid;

    @Column(name = "opening_date")
    private Date openingDate;

    @Column(name = "closing_date")
    private Date closingDate;

    @Column(name = "bid_status")
    @Enumerated
    private BidStatus status;

    @Column(name = "bid_count")
    private Integer bidCount;

    @ManyToOne
    @JoinColumn(name ="winner_user_id")
    private User winner;

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User owner;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "item_category",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> categories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId", fetch = FetchType.LAZY)
    private List<Bid> bids;

    public void addCategory(Category category) {
        if(this.categories == null) {
            categories = new ArrayList<>();
        }
        this.categories.add(category);
    }

    public void addBid(Bid bid) {
        if(this.getBids() == null){
            this.setBids(new ArrayList<>());
        }
        if(!this.bids.contains(bid)) {
            this.bids.add(bid);
            this.bidCount++;
        }
        if(this.bids.size()>0){
            setCurrentBid(Collections.max(this.bids).getBidValue());
        } else {
            setCurrentBid(bid.getBidValue());
        }
    }

    public void removeBid(Bid bid) {
        if(this.getBids() != null && this.getBids().contains(bid)) {
            this.bids.remove(bid);
            this.bidCount--;
        }
        if(this.bids.size()>1){
            setCurrentBid(Collections.max(this.bids).getBidValue());
        } else {
            setCurrentBid(0.0);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getInitialPrice() {

        return Formatter.formatPrice(initialPrice);
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public BidStatus getStatus() {
        return status;
    }

    public void setStatus(BidStatus status) {
        this.status = status;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public boolean hasCategory(Category selectedCategory) {
        return categories.contains(selectedCategory);
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Double getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(Double currentBid) {
        if(currentBid == null)
            this.currentBid = 0.0;
        else
            this.currentBid = currentBid;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public int getBidCount() {
        return this.bidCount;
    }

    public void setBidCount(int bidCount) {
        this.bidCount = bidCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (bidCount != item.bidCount) return false;
        if (!id.equals(item.id)) return false;
        if (!name.equals(item.name)) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (initialPrice != null ? !initialPrice.equals(item.initialPrice) : item.initialPrice != null) return false;
        if (currentBid != null ? !currentBid.equals(item.currentBid) : item.currentBid != null) return false;
        if (openingDate != null ? !openingDate.equals(item.openingDate) : item.openingDate != null) return false;
        if (closingDate != null ? !closingDate.equals(item.closingDate) : item.closingDate != null) return false;
        if (status != item.status) return false;
        if (winner != null ? !winner.equals(item.winner) : item.winner != null) return false;
        if (owner != null ? !owner.equals(item.owner) : item.owner != null) return false;
        if (categories != null ? !categories.equals(item.categories) : item.categories != null) return false;
        return bids != null ? bids.equals(item.bids) : item.bids == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (initialPrice != null ? initialPrice.hashCode() : 0);
        result = 31 * result + (currentBid != null ? currentBid.hashCode() : 0);
        result = 31 * result + (openingDate != null ? openingDate.hashCode() : 0);
        result = 31 * result + (closingDate != null ? closingDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (winner != null ? winner.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        result = 31 * result + (bids != null ? bids.hashCode() : 0);
        result = 31 * result + bidCount;
        return result;
    }
}
