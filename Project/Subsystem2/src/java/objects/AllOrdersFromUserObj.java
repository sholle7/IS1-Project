package objects;

import java.io.Serializable;


public class AllOrdersFromUserObj implements Serializable {
    private String username;

    public AllOrdersFromUserObj(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
