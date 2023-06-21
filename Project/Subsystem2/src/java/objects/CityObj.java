package objects;

import java.io.Serializable;


public class CityObj implements Serializable{
    private String cityName;

    public CityObj(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
}
