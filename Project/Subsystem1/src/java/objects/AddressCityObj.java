package objects;

import java.io.Serializable;


public class AddressCityObj implements Serializable{
    private String username;
    private String address;
    private int cityId;

    public AddressCityObj(String username, String address, int cityId) {
        this.username = username;
        this.address = address;
        this.cityId = cityId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    
}
