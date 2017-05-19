package ro.fortech.application.bidstore.backend.persistence.entity;


import ro.fortech.application.bidstore.backend.util.Formatter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(schema = "bid_app", name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(length = 2000)
    @Size(max = 2000)
    private String description;

    @Column(name = "initial_price")
    private Double initialPrice;

    @Column(name = "opening_date")
    private Date openingDate;

    @Column(name = "closing_date")
    private Date closingDate;

    @Column(name = "owner_user_id")
    private String owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "itemId")
    private List<Bid> bids;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "item_category",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories;

    public boolean hasCategory (Category category) {
        return this.categories != null && this.categories.contains(category);
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
