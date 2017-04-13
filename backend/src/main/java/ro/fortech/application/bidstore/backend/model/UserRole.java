package ro.fortech.application.bidstore.backend.model;

/**
 * Created by robert.ruja on 10-Apr-17.
 */
public enum UserRole {

    ADMIN(1),USER(2);

    private int role_number;

    UserRole(int number){
        this.role_number = number;
    }

    public int getRole_number() {
        return role_number;
    }

    public void setRole_number(int role_number) {
        this.role_number = role_number;
    }
}
