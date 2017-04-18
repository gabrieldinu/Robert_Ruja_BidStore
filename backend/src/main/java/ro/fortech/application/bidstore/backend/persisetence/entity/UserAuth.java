package ro.fortech.application.bidstore.backend.persisetence.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * Created by robert.ruja on 10-Apr-17.
 */

@Entity
@Table(name= "user_auth")
@NamedQueries({
        @NamedQuery(name = UserAuth.FIND_BY_USERNAME, query= "Select u FROM UserAuth u WHERE u.username = :username"),
        @NamedQuery(name = UserAuth.FIND_BY_ACTIVATION_TOKEN, query = "Select u FROM UserAuth u WHERE u.activationToken = :activationToken" ),
        @NamedQuery(name = UserAuth.DELETE_BY_EXPIRING_DATE, query ="delete from UserAuth u WHERE u.activationToken is not null and u.expiringDate < :date "),
        @NamedQuery(name = UserAuth.FIND_BY_RESET_TOKEN, query = "Select u FROM UserAuth u WHERE u.resetToken = :token"),
        @NamedQuery(name = UserAuth.FIND_BY_UUID, query = "Select u FROM UserAuth u WHERE u.uuid = :uuid")})

public class UserAuth {

    public static final String FIND_BY_USERNAME = "UserAuth.findByUsername";
    public static final String FIND_BY_ACTIVATION_TOKEN = "UserAuth.findByUUID";
    public static final String DELETE_BY_EXPIRING_DATE = "UserAuth.deleteByExpiringDate";
    public static final String FIND_BY_RESET_TOKEN = "UserAuth.findByResetToken";
    public static final String FIND_BY_UUID = "UserAuth.findByUUID";

    @Id
    @NotNull
    @Size(min = 1, max = 30)
    private String username;

    @Column(name="pass",length = 256, nullable = false)
    @NotNull
    @Size(min = 1, max = 256)
    private String password;

    @Column(name="activ_token",length = 256)
    @Size(min = 1, max = 256)
    private String activationToken;

    @Column(name="exp_date")
    private Timestamp expiringDate;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(length = 256)
    @Size(min = 1, max = 256)
    private String uuid;

    public UserAuth() {
    }

    public UserAuth(String username, String password, UUID activationToken) {
        this.username = username;
        this.password = password;
        this.activationToken = activationToken.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public Timestamp getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Timestamp expiringDate) {
        this.expiringDate = expiringDate;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
