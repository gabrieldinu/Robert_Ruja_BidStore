package ro.fortech.application.bidstore.backend.model;

import ro.fortech.application.bidstore.backend.persistence.entity.Category;
import ro.fortech.application.bidstore.backend.persistence.entity.Item;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by robert.ruja on 09-May-17.
 */
public class ItemDetails {

    private Long itemId;

    private String itemName;

    private String description;

    private double initialPrice;

    private Date closingDate;

    private String status;

    private Date openingDate;

    private Long bidCount;

    private double bestBid;

    private double yourBid;

    private String winner;

    private Map<String, Long> categories;

    private String selectedCategory;

    public ItemDetails() {
    }
    public ItemDetails(Item item) {
        this.setOpeningDate(item.getOpeningDate());
        this.setInitialPrice(item.getInitialPrice());
        this.setClosingDate(item.getClosingDate());
        this.setDescription(item.getDescription());
        this.setItemName(item.getName());
        this.setItemId(item.getId());
        if(!item.getCategories().isEmpty()){
            Map<String,Long> categoriesMap = new HashMap<>();
            for(Category category: item.getCategories()){
                categoriesMap.put(category.getName(),category.getId());
            }
            this.setCategories(categoriesMap);
        }
    }
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public Long getBidCount() {
        return bidCount;
    }

    public void setBidCount(Long bidCount) {
        this.bidCount = bidCount;
    }

    public double getBestBid() {
        return bestBid;
    }

    public void setBestBid(double bestBid) {
        this.bestBid = bestBid;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public double getYourBid() {
        return yourBid;
    }

    public void setYourBid(double yourBid) {
        this.yourBid = yourBid;
    }

    public Map<String, Long> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, Long> categories) {
        this.categories = categories;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
}
