package objects;

import java.io.Serializable;


public class CartFromUser implements Serializable{
    private String username;

    public CartFromUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
