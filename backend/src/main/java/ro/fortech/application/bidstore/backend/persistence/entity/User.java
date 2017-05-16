package ro.fortech.application.bidstore.backend.persistence.entity;

/**
 * Created by robert.ruja on 10-Apr-17.
 */

import ro.fortech.application.bidstore.backend.model.UserEnabled;
import ro.fortech.application.bidstore.backend.model.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = User.FIND_BY_USERNAME, query= "Select u FROM User u WHERE u.username = :username"),
        @NamedQuery(name = User.FIND_BY_EMAIL, query= "Select u FROM User u  WHERE u.email = :email")
        })
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    public static final String FIND_BY_USERNAME = "User.findByUsername" ;
    public static final String FIND_BY_EMAIL = "User.findByEmail";
    @Id
    @NotNull
    @Size(min = 1, max = 30)
    private String username;

    @Column(length = 50, name = "first_name", nullable = false)
    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @Column(length = 50, name = "last_name", nullable = false)
    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @Column
    @NotNull
    private String email;

    @Enumerated
    @NotNull
    @Column(name = "enabled")
    private UserEnabled userEnabled;

    @Enumerated
    @NotNull
    @Column(name = "role")
    private UserRole role;

    @Column(name="token",length = 256)
    @Size(min = 1, max = 256)
    private String uuid;

    @Column(name="telephone",length = 20)
    private Integer telephone;

    @Column(name="exp_date")
    private Timestamp expiringDate;

    @Transient
    @Column(name="role")
    private boolean admin;

    @Transient
    @Column(name="enabled")
    private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    List<Item> placed;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "winnerId")
    List<ItemSoldBought> bought;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sellerId")
    List<ItemSoldBought> sold;

    public User(String username, String firstName, String lastName, String email, UserRole role, UserEnabled enabled) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.userEnabled = enabled;

    }

    public User(String username) {
        this.username = username;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserEnabled getEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(UserEnabled userEnabled) {
        this.userEnabled = userEnabled;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Timestamp getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Timestamp expiringDate) {
        this.expiringDate = expiringDate;
    }

    public boolean isAdmin() {
        return this.role.equals(UserRole.ADMIN);
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public UserEnabled getUserEnabled() {
        return userEnabled;
    }

    public boolean isEnabled() {
        return this.userEnabled.equals(UserEnabled.ENABLED);
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public List<Item> getPlaced() {
        return placed;
    }

    public void setPlaced(List<Item> placed) {
        this.placed = placed;
    }

    public List<ItemSoldBought> getBought() {
        return bought;
    }

    public void setBought(List<ItemSoldBought> bought) {
        this.bought = bought;
    }

    public List<ItemSoldBought> getSold() {
        return sold;
    }

    public void setSold(List<ItemSoldBought> sold) {
        this.sold = sold;
    }

    public void setEnabled(boolean enabled) {

        this.enabled = enabled;
        if(enabled) {
            setUserEnabled(UserEnabled.ENABLED);
        }else {
            setUserEnabled(UserEnabled.DISABLED);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (admin != user.admin) return false;
        if (enabled != user.enabled) return false;
        if (!username.equals(user.username)) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (userEnabled != user.userEnabled) return false;
        if (role != user.role) return false;
        if (uuid != null ? !uuid.equals(user.uuid) : user.uuid != null) return false;
        if (telephone != null ? !telephone.equals(user.telephone) : user.telephone != null) return false;
        return expiringDate != null ? expiringDate.equals(user.expiringDate) : user.expiringDate == null;
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + userEnabled.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (expiringDate != null ? expiringDate.hashCode() : 0);
        result = 31 * result + (admin ? 1 : 0);
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }
}
