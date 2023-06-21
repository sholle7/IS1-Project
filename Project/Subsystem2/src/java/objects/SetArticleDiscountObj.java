package objects;

import java.io.Serializable;


public class SetArticleDiscountObj implements Serializable{
    private String articleName;
    private int articleDiscount; 

    public SetArticleDiscountObj(String articleName, int articleDiscount) {
        this.articleName = articleName;
        this.articleDiscount = articleDiscount;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getArticleDiscount() {
        return articleDiscount;
    }

    public void setArticleDiscount(int articleDiscount) {
        this.articleDiscount = articleDiscount;
    }
        
}
