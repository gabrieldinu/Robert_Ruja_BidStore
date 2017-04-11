package ro.fortech.application.bidstore.persisetence.entity;

import ro.fortech.application.bidstore.util.PasswordDigest;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Created by robert.ruja on 10-Apr-17.
 */
public class UserAuth {

    @Id
    @NotNull
    @Size(min = 1, max = 30)
    private String username;

    @Column(length = 256, nullable = false)
    @NotNull
    @Size(min = 1, max = 256)
    private String password;

    @Column(name="token",length = 256)
    @Size(min = 1, max = 256)
    private String uuid;

    @PrePersist
    @PreUpdate
    private void digestPassword() {
        password = PasswordDigest.digestPassword(password);
    }

    public UserAuth(String username, String password, UUID uuid) {
        this.username = username;
        this.password = password;
        this.uuid = uuid.toString();
    }
}
