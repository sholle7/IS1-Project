package objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class UserMoneyTransaction implements Serializable {
    
    public List<Integer> money;

    public UserMoneyTransaction() {
        this.money = new ArrayList<>();
    }

    public List<Integer> getMoney() {
        return money;
    }

    public void setMoney(List<Integer> money) {
        this.money = money;
    }
    
}
