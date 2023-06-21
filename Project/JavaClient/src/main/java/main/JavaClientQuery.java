package main;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;


public class JavaClientQuery {
    private final Client client = ClientBuilder.newClient();
    private final String schema = "http:";
    private final String hostName = "localhost";
    private final String port = "8080";
    private final String appPath = "Server/prodaja_artikala";
    private final String uri = schema + "//" + hostName + ":" + port + "/" + appPath + "/";
    
    public void createCity(String cityName){
        Response response = client.target(uri).path("korisnik/kreirajGrad/")
                            .queryParam("cityName", cityName)                       
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }

    public void createUser(String username, String password, String firstName, String lastName, String address, int cityId) {
        Response response = client.target(uri).path("korisnik/kreirajKorisnika/")
                            .queryParam("username", username) 
                            .queryParam("password", password) 
                            .queryParam("firstName", firstName) 
                            .queryParam("lastName", lastName)
                            .queryParam("address", address) 
                            .queryParam("cityId", cityId) 
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }

    public void addMoney(String username, int moneyAmount) {
        Response response = client.target(uri).path("korisnik/dodajNovac/")
                            .queryParam("username", username) 
                            .queryParam("moneyAmount", moneyAmount)
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }

    public void updateAddressAndCity(String username, String address, int cityId) {
        Response response = client.target(uri).path("korisnik/promeniAdresuIGrad/")
                            .queryParam("username", username) 
                            .queryParam("address", address)
                            .queryParam("cityId", cityId)
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }

    public void createCategory(String categoryName) {
        Response response = client.target(uri).path("artikal/kreirajKategoriju/")
                            .queryParam("categoryName", categoryName)                          
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }

    public void createArticle(String articleName, String articleAbout, int articlePrice, String categoryName, String username) {
        Response response = client.target(uri).path("artikal/kreairajArtikal/")
                            .queryParam("articleName", articleName)   
                            .queryParam("articleAbout", articleAbout) 
                            .queryParam("articlePrice", articlePrice) 
                            .queryParam("categoryName", categoryName) 
                            .queryParam("username", username) 
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }

    public void updatePriceOfArticle(String articleName, int articlePrice) {
        Response response = client.target(uri).path("artikal/promeniCenuArtikla/")
                            .queryParam("articleName", articleName)   
                            .queryParam("articlePrice", articlePrice) 
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }

    public void setDiscount(String articleName, int articleDiscount) {
        Response response = client.target(uri).path("artikal/postaviPopustArtikla/")
                            .queryParam("articleName", articleName)   
                            .queryParam("articleDiscount", articleDiscount) 
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }

    public void addArticleInCart(String username, String articleName, int articleAmount) {
        Response response = client.target(uri).path("artikal/dodajArtikalUKorpu/")
                            .queryParam("username", username)   
                            .queryParam("articleName", articleName) 
                            .queryParam("articleAmount", articleAmount) 
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }

    public void deleteArticleFromCart(String username, String articleName, int articleAmount) {
        Response response = client.target(uri).path("artikal/obrisiArtikalIzKorpe/")
                            .queryParam("username", username)   
                            .queryParam("articleName", articleName) 
                            .queryParam("articleAmount", articleAmount) 
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }

    public void makeTransaction(String username) {
        Response response = client.target(uri).path("transakcija/napraviTransakciju/")
                            .queryParam("username", username)                             
                            .request().post(null);
        
        System.out.println(response.readEntity(String.class));
    }

    public void getAllCities() {
        Response response = client.target(uri).path("korisnik/dohvatiSveGradove/")                         
                            .request().get();
        
        System.out.println(response.readEntity(String.class));
    }

    public void getAllUsers() {
        Response response = client.target(uri).path("korisnik/dohvatiSveKorisnike/")                         
                            .request().get();
        
        System.out.println(response.readEntity(String.class));
    }

    public void getAllCategories() {
        Response response = client.target(uri).path("artikal/dohvatiSveKategorije/")                  
                            .request().get();
        
        System.out.println(response.readEntity(String.class));
    }

    public void getAllArticlesFromUser(String username) {
        Response response = client.target(uri).path("artikal/dohvatiSveArtikleOdKorisnika/")  
                            .queryParam("username", username) 
                            .request().get();
        
        System.out.println(response.readEntity(String.class));
    }

    public void getCartFromUser(String username) {
        Response response = client.target(uri).path("artikal/dohvatiSadrzajKorpeOdKorisnika/")  
                            .queryParam("username", username) 
                            .request().get();
        
        System.out.println(response.readEntity(String.class));
    }

    public void getAllOrdersFromUser(String username) {
        Response response = client.target(uri).path("transakcija/dohvatiSveNarudzbineKorisnika/")
                            .queryParam("username", username)
                            .request().get();
        System.out.println(response.readEntity(String.class));
    }

    public void getAllOrders() {
       Response response = client.target(uri).path("transakcija/dohvatiSveNarudzbine/")                  
                            .request().get();
        System.out.println(response.readEntity(String.class));
    }

    public void getAllTransactions() {
       Response response = client.target(uri).path("transakcija/dohvatiSveTransakcije/")
                            .request().get();
       System.out.println(response.readEntity(String.class));
    }
    
    
}
