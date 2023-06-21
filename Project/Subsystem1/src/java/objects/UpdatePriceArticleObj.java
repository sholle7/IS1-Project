package objects;

import java.io.Serializable;


public class UpdatePriceArticleObj implements Serializable{
    private String articleName;
    private int articlePrice; 

    public UpdatePriceArticleObj(String articleName, int articlePrice) {
        this.articleName = articleName;
        this.articlePrice = articlePrice;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getArticlePrice() {
        return articlePrice;
    }

    public void setArticlePrice(int articlePrice) {
        this.articlePrice = articlePrice;
    }
    
}
