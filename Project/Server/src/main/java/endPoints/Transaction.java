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


@Path("transakcija")
public class Transaction {
    
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
    @Path("napraviTransakciju")
    public Response makeTransaction(@QueryParam("username") String username){
       
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        MakeTransactionObj transaction = new MakeTransactionObj(username);
        ObjectMessage objectM = context.createObjectMessage(transaction);
        try {
            objectM.setIntProperty("opcija", 11);
        } catch (JMSException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_2, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_3);
        JMSConsumer consumer2 = context.createConsumer(queueReceive_2);
        JMSConsumer consumer1 = context.createConsumer(queueReceive);
        
        Message msg2 = consumer2.receive();
        if (msg2 instanceof ObjectMessage) try {
            
            
                ObjectMessage objMsg = (ObjectMessage)msg2;
                CartPrice cP = (CartPrice) objMsg.getObject();       
                
                CartPrice cartPriceH = new CartPrice(username, cP.getCurrentCartPrice());
               
                cartPriceH.money = cP.money;
                        
                ObjectMessage objectMH = context.createObjectMessage(cartPriceH);
                try {
                    objectMH.setIntProperty("opcija", 11);
                } catch (JMSException ex) {
                    Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
                
                producer.send(queueSend_3, objectMH);
                Message msg = consumer.receive();
                
                ObjectMessage objMsgNew = (ObjectMessage)msg;
                CartPrice cP2 = (CartPrice) objMsgNew.getObject();
                
                CartPrice newCartPrice = new CartPrice(username, cP2.getCurrentCartPrice());
                newCartPrice.setIdN(cP2.getIdN());
                newCartPrice.money = cP2.money;
                
                ObjectMessage objectMHNew = context.createObjectMessage(newCartPrice);
                
                try {
                    objectMHNew.setIntProperty("opcija", 20);
                } catch (JMSException ex) {
                    Logger.getLogger(Article.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                producer.send(queueSend_2, objectMHNew);
                Message msg_help = consumer2.receive();   
             
                producer.send(queueSend, objectMH);
                Message msg1 = consumer1.receive();
                return Response.ok().entity(((TextMessage) msg1).getText()).build();
            //return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return Response.ok().entity("OK").build();
       
    }
    
    
    
    @GET
    @Path("dohvatiSveNarudzbineKorisnika")
    public Response getAllOrdersFromUser(@QueryParam("username") String username){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        AllOrdersFromUserObj allOrdersFromUser= new AllOrdersFromUserObj(username);
        ObjectMessage objectM = context.createObjectMessage(allOrdersFromUser);
        try {
            objectM.setIntProperty("opcija", 17);
        } catch (JMSException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_3, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_3);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        return Response.ok().entity("OK").build();
        
    }
    
    
    @GET
    @Path("dohvatiSveNarudzbine")
    public Response getAllOrders(){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        AllOrders allOrders = new AllOrders();
        ObjectMessage objectM = context.createObjectMessage(allOrders);
        try {
            objectM.setIntProperty("opcija", 18);
        } catch (JMSException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_3, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_3);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        return Response.ok().entity("OK").build();
        
    }
    
    @GET
    @Path("dohvatiSveTransakcije")
    public Response getAllTransactions(){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        AllTransactionsObj allTransactions = new AllTransactionsObj();
        ObjectMessage objectM = context.createObjectMessage(allTransactions);
        try {
            objectM.setIntProperty("opcija", 19);
        } catch (JMSException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queueSend_3, objectM);
        
        JMSConsumer consumer = context.createConsumer(queueReceive_3);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage) try {
            return Response.ok().entity(((TextMessage) msg).getText()).build();
        } catch (JMSException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        return Response.ok().entity("OK").build();
        
    }
    
}
