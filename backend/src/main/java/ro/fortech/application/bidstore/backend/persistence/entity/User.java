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

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = User.FIND_BY_USERNAME, query= "Select u FROM User u WHERE u.username = :username"),
        @NamedQuery(name = User.FIND_BY_EMAIL, query= "Select u FROM User u  WHERE u.email = :email")
        })
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    public static final java.lang.String FIND_BY_USERNAME = "User.findByUsername" ;
    public static final java.lang.String FIND_BY_EMAIL = "User.findByEmail";
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
}
