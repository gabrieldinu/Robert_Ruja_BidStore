package ro.fortech.application.bidstore.backend.model;

/**
 * Created by robert.ruja on 25-Apr-17.
 */
public enum AddressType {
    HOME,BILLING,SHIPPING;
    String mapKey;
    AddressType(){
            this.mapKey = this.toString().toLowerCase() + "Address";
    }

    public String getMapKey() {
        return mapKey;
    }

}
