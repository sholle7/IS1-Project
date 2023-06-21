package objects;

import java.io.Serializable;


public class AllArticlesFromUserObj implements Serializable{
    private String username;

    public AllArticlesFromUserObj(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
