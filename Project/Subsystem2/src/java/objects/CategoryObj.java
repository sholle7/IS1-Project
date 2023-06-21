package objects;

import java.io.Serializable;


public class CategoryObj implements Serializable{
    private String categoryName;

    public CategoryObj(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
     
}
