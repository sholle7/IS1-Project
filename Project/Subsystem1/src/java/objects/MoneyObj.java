package objects;

import java.io.Serializable;


public class MoneyObj implements Serializable{
    private String username;
    private int moneyAmount;

    public MoneyObj(String username, int moneyAmount) {
        this.username = username;
        this.moneyAmount = moneyAmount;
    }

    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
    
}
