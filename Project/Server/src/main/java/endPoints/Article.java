package endPoints;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import objects.*;


@Path("artikal")
public class Article {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    public ConnectionFactory connectionFactory;
    
    @Resource(lookup="queue_prodaja_artikala_send")
    public Queue queueSend;
    
    @Resource(lookup="queue_prodaja_artikala_send_2")
    public Queue queueSend_2;
    
    @Resource(lookup="queue_prodaja_artikala_send_3")
    public Queue queueSend_3;
    
    @Resource(lookup="queue_prodaja_artikala_receive")
    public Queue queueReceive;
    
    @Resource(lookup="queue_prodaja_artikala_receive_2")
    public Queue queueReceive_2;
    
    @Resource(lookup="queue_prodaja_artikala_receive_3")
    public Queue queueReceive_3;
    
    
    
    @POST
    @Path("kreirajKategoriju")
    public Response createCategory(@QueryParam("categoryName") String categoryName){
       
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        CategoryObj category = new CategoryObj(categoryName);
        ObjectMessage objectM = context.createObjectMessage(category);
        try {
            objectM.setIntProperty("opcija", 5);
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_2, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_2);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return Response.ok().entity("OK").build();
       
    }
    
    
    @POST
    @Path("kreairajArtikal")
    public Response createArticle(@QueryParam("articleName") String articleName, @QueryParam("articleAbout") String articleAbout, @QueryParam("articlePrice") int articlePrice, @QueryParam("categoryName") String categoryName, @QueryParam("username") String username){
       
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        ArticleObj article = new ArticleObj(articleName, articleAbout, articlePrice, categoryName, username);
        ObjectMessage objectM = context.createObjectMessage(article);
        try {
            objectM.setIntProperty("opcija", 6);
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_2, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_2);
        
       
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {                
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return Response.ok().entity("OK").build();
       
    }
    
    
    @POST
    @Path("promeniCenuArtikla")
    public Response updatePriceOfArticle(@QueryParam("articleName") String articleName, @QueryParam("articlePrice") int articlePrice){
       
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        UpdatePriceArticleObj updatedArticle = new UpdatePriceArticleObj(articleName, articlePrice);
        ObjectMessage objectM = context.createObjectMessage(updatedArticle);
        try {
            objectM.setIntProperty("opcija", 7);
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_2, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_2);
        
       
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            
  
            
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return Response.ok().entity("OK").build();
       
    }
    
    
    @POST
    @Path("postaviPopustArtikla")
    public Response setDiscount(@QueryParam("articleName") String articleName, @QueryParam("articleDiscount") int articleDiscount){
       
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        SetArticleDiscountObj setArticle = new SetArticleDiscountObj(articleName, articleDiscount);
        ObjectMessage objectM = context.createObjectMessage(setArticle);
        try {
            objectM.setIntProperty("opcija", 8);
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_2, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_2);
        
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
             
          
            
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return Response.ok().entity("OK").build();
       
    }
    
    @POST
    @Path("dodajArtikalUKorpu")
    public Response addArticleInCart(@QueryParam("username") String username,@QueryParam("articleName") String articleName, @QueryParam("articleAmount")int articleAmount){
       
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        ArticleInCartObj articleInCart = new ArticleInCartObj(username, articleName,articleAmount);
        ObjectMessage objectM = context.createObjectMessage(articleInCart);
        try {
            objectM.setIntProperty("opcija", 9);
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_2, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_2);
        
        JMSConsumer consumer3 = context.createConsumer(queueReceive_3);
        
 
        Message msg = consumer.receive();
         
        if (msg instanceof ObjectMessage) 
            try {
                
                ObjectMessage objMsg = (ObjectMessage)msg;
                ArticleInCartObj aM = (ArticleInCartObj) objMsg.getObject();           
                
                ArticleInCartObj articleInCartH = new ArticleInCartObj(username, articleName,articleAmount);
                articleInCartH.setDiscount(aM.getDiscount());
                articleInCartH.setIdArtikla(aM.getIdArtikla());
                articleInCartH.setIdKorpe(aM.getIdKorpe());
                articleInCartH.setPrice(aM.getPrice());
                
                ObjectMessage objectMH = context.createObjectMessage(articleInCartH);
                try {
                    objectMH.setIntProperty("opcija", 9);
                } catch (JMSException ex) {
                    Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
                }
                
              
                producer.send(queueSend_3, objectMH);
                Message msg3 = consumer3.receive();



                return Response.ok().entity(((TextMessage) msg3).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        return Response.ok().entity("OK").build();
       
    }
    
    
    @POST
    @Path("obrisiArtikalIzKorpe")
    public Response deleteArticleFromCart(@QueryParam("username") String username,@QueryParam("articleName") String articleName, @QueryParam("articleAmount")int articleAmount){
       
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        DeleteArticleInCartObj deletedArticleInCart = new DeleteArticleInCartObj(username, articleName,articleAmount);
        ObjectMessage objectM = context.createObjectMessage(deletedArticleInCart);
        try {
            objectM.setIntProperty("opcija", 10);
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_2, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_2);
        
        JMSConsumer consumer3 = context.createConsumer(queueReceive_3);
        
        Message msg = consumer.receive();
        if (msg instanceof ObjectMessage) try {
            
            ObjectMessage objMsg = (ObjectMessage)msg;
            DeleteArticleInCartObj aM = (DeleteArticleInCartObj) objMsg.getObject();           

            DeleteArticleInCartObj articleInCartH = new DeleteArticleInCartObj(username, articleName,articleAmount);
            articleInCartH.setDiscount(aM.getDiscount());
            articleInCartH.setIdArtikla(aM.getIdArtikla());
            articleInCartH.setIdKorpe(aM.getIdKorpe());
            articleInCartH.setPrice(aM.getPrice());

            ObjectMessage objectMH = context.createObjectMessage(articleInCartH);
            try {
                objectMH.setIntProperty("opcija", 10);
            } catch (JMSException ex) {
                Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
            }


            producer.send(queueSend_3, objectMH);
            Message msg3 = consumer3.receive();
            
            
            

            
            return Response.ok().entity(((TextMessage) msg3).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return Response.ok().entity("OK").build();
       
    }
    
    @GET
    @Path("dohvatiSveKategorije")
    public Response getAllCategories(){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        AllCategoriesObj allCategories= new AllCategoriesObj();
        ObjectMessage objectM = context.createObjectMessage(allCategories);
        try {
            objectM.setIntProperty("opcija", 14);
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_2, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_2);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        return Response.ok().entity("OK").build();
        
    }
    
    
    
    @GET
    @Path("dohvatiSveArtikleOdKorisnika")
    public Response getAllArticlesFromUser(@QueryParam("username") String username){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        AllArticlesFromUserObj allArticlesFromUser= new AllArticlesFromUserObj(username);
        ObjectMessage objectM = context.createObjectMessage(allArticlesFromUser);
        try {
            objectM.setIntProperty("opcija", 15);
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_2, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_2);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        return Response.ok().entity("OK").build();
        
    }
    
    @GET
    @Path("dohvatiSadrzajKorpeOdKorisnika")
    public Response getCartFromUser(@QueryParam("username") String username){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        CartFromUser cartFromUser= new CartFromUser(username);
        ObjectMessage objectM = context.createObjectMessage(cartFromUser);
        try {
            objectM.setIntProperty("opcija", 16);
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_2, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_2);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        return Response.ok().entity("OK").build();
        
    }
    
    
    
}
