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


@Path("korisnik")
public class User {
    
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
    @Path("kreirajGrad")
    public Response createCity(@QueryParam("cityName") String cityName){
       
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        CityObj grad = new CityObj(cityName);
        ObjectMessage objectM = context.createObjectMessage(grad);
        
        try {
            objectM.setIntProperty("opcija", 1);
        } catch (JMSException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        producer.send(queueSend, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        
        return Response.ok().entity("OK").build();
       
    }
        
    @POST
    @Path("kreirajKorisnika")
    public Response createUser(@QueryParam("username")String username, @QueryParam("password")String password, @QueryParam("firstName")String firstName, @QueryParam("lastName")String lastName, @QueryParam("address")String address, @QueryParam("cityId")int cityId){
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        UserObj user = new UserObj(username, password, firstName, lastName, address, cityId);
        ObjectMessage objectM = context.createObjectMessage(user);
        try {
            objectM.setIntProperty("opcija", 2);
        } catch (JMSException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive);
        
        JMSConsumer consumer2 = context.createConsumer(queueReceive_2);
        JMSConsumer consumer3 = context.createConsumer(queueReceive_3);
        
        
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            
            producer.send(queueSend_2, objectM);
            Message msg2 = consumer2.receive();
            
            producer.send(queueSend_3, objectM);
            Message msg3 = consumer3.receive();
            
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        
        return Response.ok().entity("OK").build();
    
    }
    
    
    
    @POST
    @Path("dodajNovac")
    public Response addMoney(@QueryParam("username") String username, @QueryParam("moneyAmount") int moneyAmount){
       
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        MoneyObj money = new MoneyObj(username, moneyAmount);
        ObjectMessage objectM = context.createObjectMessage(money);
        try {
            objectM.setIntProperty("opcija", 3);
        } catch (JMSException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive);
        
        JMSConsumer consumer2 = context.createConsumer(queueReceive_2);
        JMSConsumer consumer3 = context.createConsumer(queueReceive_3);
        
        Message msg = consumer.receive();
 
        if (msg instanceof TextMessage) try {
            
            
            producer.send(queueSend_2, objectM);
            Message msg2 = consumer2.receive();
            
            producer.send(queueSend_3, objectM);
            Message msg3 = consumer3.receive();
            
            
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return Response.ok().entity("OK").build();
       
    }
    
    
    @POST
    @Path("promeniAdresuIGrad")
    public Response updateAddressAndCity(@QueryParam("username") String username, @QueryParam("address") String address,@QueryParam("cityId") int cityId){
       
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        AddressCityObj money = new AddressCityObj(username, address, cityId);
        ObjectMessage objectM = context.createObjectMessage(money);
        try {
            objectM.setIntProperty("opcija", 4);
        } catch (JMSException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive);
        
        JMSConsumer consumer2 = context.createConsumer(queueReceive_2);
        JMSConsumer consumer3 = context.createConsumer(queueReceive_3);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            
            producer.send(queueSend_2, objectM);
            Message msg2 = consumer2.receive();
            
            producer.send(queueSend_3, objectM);
            Message msg3 = consumer3.receive();
            
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return Response.ok().entity("OK").build();
       
    }
    
    
    @GET
    @Path("dohvatiSveGradove")
    public Response getAllCities(){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        AllCitiesObj allCities= new AllCitiesObj();
        ObjectMessage objectM = context.createObjectMessage(allCities);
        try {
            objectM.setIntProperty("opcija", 12);
        } catch (JMSException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        return Response.ok().entity("OK").build();
        
    }
    
    
    @GET
    @Path("dohvatiSveKorisnike")
    public Response getAllUsers(){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        AllUsersObj allUsers= new AllUsersObj();
        ObjectMessage objectM = context.createObjectMessage(allUsers);
        try {
            objectM.setIntProperty("opcija", 13);
        } catch (JMSException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend, objectM);
        
        
        
        JMSConsumer consumer = context.createConsumer(queueReceive);
        
       
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        return Response.ok().entity("OK").build();
        
    }
    
}
