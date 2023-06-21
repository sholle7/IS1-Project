package objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CartPrice implements Serializable{
    private String username;
    private int currentCartPrice;
    public List<Integer> money;
    private int idN;
    
    public CartPrice(String username, int currentCartPrice) {
        this.username = username;
        this.currentCartPrice = currentCartPrice;
        this.money = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

 
    
    public int getCurrentCartPrice() {
        return currentCartPrice;
    }

    public void setCurrentCartPrice(int currentCartPrice) {
        this.currentCartPrice = currentCartPrice;
    }

    public List<Integer> getMoney() {
        return money;
    }

    public void setMoney(List<Integer> money) {
        this.money = money;
    }

    public int getIdN() {
        return idN;
    }

    public void setIdN(int idN) {
        this.idN = idN;
    }

    
    
}
