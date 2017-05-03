package ro.fortech.application.bidstore.backend.persistence.entity;

import ro.fortech.application.bidstore.backend.model.BidStatus;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.List;


public class Item {


    private Long id;

    private String name;

    private String description;

    private Double initialPrice;

    private Double currentBid;

    private Long bidCount;

    private Date openingDate;

    private Date closingDate;

    private BidStatus status;

    private String winnerName;

    private List<Category> categories;

    DecimalFormat format = new DecimalFormat("#.00");

    public Item(Long id, String name, String description, Double initialPrice, Double currentBid, Long bidCount, Date openingDate, Date closingDate, BidStatus status, String winnerName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.initialPrice = initialPrice;
        this.currentBid = currentBid;
        this.bidCount = bidCount;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.status = status;
        this.winnerName = winnerName;
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

        return Double.parseDouble(format.format(initialPrice));
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Double getCurrentBid() {
        return Double.parseDouble(format.format(currentBid));
    }

    public void setCurrentBid(Double currentBid) {
        this.currentBid = currentBid;
    }

    public Long getBidCount() {
        return bidCount;
    }

    public void setBidCount(Long bidCount) {
        this.bidCount = bidCount;
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

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
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
}
