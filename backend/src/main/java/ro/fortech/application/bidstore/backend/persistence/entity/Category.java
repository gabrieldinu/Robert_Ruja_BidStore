package ro.fortech.application.bidstore.backend.persistence.entity;

import java.io.Serializable;


public class Category implements Serializable {

    private String name;

    private Long parentId;

    private Long id;

    private String description;

    private Integer level;

    public Category() {

    }

    public Category(String name, Long parentId, Long id, String description, Integer level) {
        this.name = name;
        this.parentId = parentId;
        this.id = id;
        this.description = description;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (name != null ? !name.equals(category.name) : category.name != null) return false;
        if (parentId != null ? !parentId.equals(category.parentId) : category.parentId != null) return false;
        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        if (description != null ? !description.equals(category.description) : category.description != null)
            return false;
        return level != null ? level.equals(category.level) : category.level == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        return result;
    }
}
