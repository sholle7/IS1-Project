
package objects;

import java.io.Serializable;


public class DeleteArticleInCartObj implements Serializable{
    private String username;
    private String articleName;
    private int articleAmount;
    private int discount;
    private int price;
    private int idKorpe;
    private int idArtikla;

    public DeleteArticleInCartObj(String username, String articleName, int articleAmount) {
        this.username = username;
        this.articleName = articleName;
        this.articleAmount = articleAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getArticleAmount() {
        return articleAmount;
    }

    public void setArticleAmount(int articleAmount) {
        this.articleAmount = articleAmount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIdKorpe() {
        return idKorpe;
    }

    public void setIdKorpe(int idKorpe) {
        this.idKorpe = idKorpe;
    }

    public int getIdArtikla() {
        return idArtikla;
    }

    public void setIdArtikla(int idArtikla) {
        this.idArtikla = idArtikla;
    }
    
}
