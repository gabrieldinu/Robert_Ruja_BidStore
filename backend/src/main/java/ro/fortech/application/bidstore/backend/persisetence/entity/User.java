package ro.fortech.application.bidstore.backend.persisetence.entity;

/**
 * Created by robert.ruja on 10-Apr-17.
 */

import ro.fortech.application.bidstore.backend.model.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "user")
@NamedQueries({@NamedQuery(name = User.FIND_BY_USERNAME, query= "Select u FROM User u WHERE u.username = :username")})
public class User implements Serializable {

    public static final java.lang.String FIND_BY_USERNAME = "User.findByUsername" ;
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
    @Column(name = "role")
    private UserRole role;

    public User(String username, String firstName, String lastName, String email, UserRole role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;

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
