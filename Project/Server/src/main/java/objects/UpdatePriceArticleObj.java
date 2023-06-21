/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;

/**
 *
 * @author student
 */
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
