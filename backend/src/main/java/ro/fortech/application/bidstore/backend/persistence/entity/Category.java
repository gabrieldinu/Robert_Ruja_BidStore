package ro.fortech.application.bidstore.backend.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "category", schema = "bid_app")
public class Category implements Serializable {

    @Column
    private String name;

    @Id
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column
    private String description;

    public Category() {

    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parentId")
    private List<Category> children;

    public Category(String name,Long parentId, Long id, String description) {
        this.name = name;
        this.id = id;
        this.parentId = parentId;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!id.equals(category.id)) return false;
        return parentId != null ? parentId.equals(category.parentId) : category.parentId == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        return result;
    }
}
