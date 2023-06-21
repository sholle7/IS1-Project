package objects;

import java.io.Serializable;


public class ArticleObj implements Serializable{
    private String articleName;
    private String articleAbout; 
    private int articlePrice; 
    private String categoryName;
    private String username;
    private int idKat;
    
    public ArticleObj(String articleName, String articleAbout, int articlePrice, String categoryName, String username) {
        this.articleName = articleName;
        this.articleAbout = articleAbout;
        this.articlePrice = articlePrice;
        this.categoryName = categoryName;
        this.username = username;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleAbout() {
        return articleAbout;
    }

    public void setArticleAbout(String articleAbout) {
        this.articleAbout = articleAbout;
    }

    public int getArticlePrice() {
        return articlePrice;
    }
    
    public String getUsername() {
        return username;
    }

    public void setArticlePrice(int articlePrice) {
        this.articlePrice = articlePrice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdKat() {
        return idKat;
    }

    public void setIdKat(int idKat) {
        this.idKat = idKat;
    }
    
}
