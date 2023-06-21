package objects;

import java.io.Serializable;


public class MakeTransactionObj implements Serializable {
    private String username;

    public MakeTransactionObj(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
