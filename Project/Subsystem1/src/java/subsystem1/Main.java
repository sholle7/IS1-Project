package subsystem1;

import entities.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import objects.*;


public class Main {

    @Resource(lookup="jms/__defaultConnectionFactory")
    public static ConnectionFactory connectionFactory;
    
    @Resource(lookup="queue_prodaja_artikala_send")
    public static Queue queueSend_receiveQueryFromServer;
    
    @Resource(lookup="queue_prodaja_artikala_receive")
    public static Queue queueReceive_sendResultsToServer;    
     
    
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    
    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("Subsystem1PU");
        entityManager = entityManagerFactory.createEntityManager();
        JMSConsumer consumer;
        
       
          
        try (JMSContext context = connectionFactory.createContext()) {
            consumer = context.createConsumer(queueSend_receiveQueryFromServer);
            JMSProducer producer = context.createProducer();
            
           
            while(true){
                
                int option = 0;
                Message message = consumer.receive();
                ObjectMessage objectMessage = (ObjectMessage)message;
                try {
                    option = objectMessage.getIntProperty("opcija");
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                switch(option){
                    case 1:
                        CityObj city = null;
                        try {
                             city = (CityObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        TypedQuery<Grad> cityFound = entityManager.createNamedQuery("Grad.findByNaziv", Grad.class).setParameter("naziv", city.getCityName());
                       
                        List<Grad> c = new ArrayList<Grad>();
                        c = cityFound.getResultList();
                        
                        TextMessage textMsg1 = context.createTextMessage();
                        
                        if(c == null || c.size() > 0){
                            try {
                                textMsg1.setText("Grad sa datim nazivom vec postoji! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        else{
                            Grad newCity = new Grad();
                            newCity.setNaziv(city.getCityName());
                            EntityTransaction transaction = entityManager.getTransaction();
                            transaction.begin();
                            entityManager.persist(newCity);
                            entityManager.flush();
                            entityManager.refresh(newCity);
                            transaction.commit();
                            
                            try {
                                textMsg1.setText("Grad je dodat u bazu podataka! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        producer.send(queueReceive_sendResultsToServer, textMsg1);
                        
                        break;
                        
                    case 2:
                        UserObj user = null;
                        try {
                             user = (UserObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        TypedQuery<Korisnik> userFound = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", user.getUsername());
                       
                        List<Korisnik> u = new ArrayList<Korisnik>();
                        u = userFound.getResultList();
                        
                        
                        TypedQuery<Grad> cityForUserFound = entityManager.createNamedQuery("Grad.findByIdG", Grad.class).setParameter("idG", user.getCityId());
                       
                        List<Grad> cForUser = new ArrayList<Grad>();
                        cForUser = cityForUserFound.getResultList();
                        
                        
                        TextMessage textMsg2 = context.createTextMessage();
                        
                        if(cForUser == null || cForUser.size() == 0){
                            try {
                                textMsg2.setText("Grad sa datim nazivom ne postoji! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        else{
                         
                        
                            if(u == null || u.size() > 0){
                                try {
                                    textMsg2.setText("Korisnik sa datim korisnickim imenom vec postoji! \n");
                                } catch (JMSException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            else{
                           
                                Korisnik newUser = new Korisnik();
                                Grad g = cForUser.get(0);
                                
                                /*Korpa ca = new Korpa();
                                ca.setIdKorisnik(newUser);
                                ca.setUkupnaCena(0);*/
                                

                                newUser.setAdresa(user.getAddress());
                                newUser.setIme(user.getFirstName());
                                newUser.setPrezime(user.getLastName());
                                newUser.setKorisnickoIme(user.getUsername());
                                newUser.setSifra(user.getPassword());
                                newUser.setNovac(0);
                                newUser.setIdG(g);

                                EntityTransaction transaction = entityManager.getTransaction();
                                transaction.begin();
                                entityManager.persist(newUser);                               
                                entityManager.flush();
                                entityManager.refresh(newUser);
                                /*
                                entityManager.persist(ca);
                                entityManager.flush();
                                entityManager.refresh(ca);*/
                                transaction.commit();
                                

                                try {
                                    textMsg2.setText("Korisnik je dodat u bazu podataka! \n");
                                } catch (JMSException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        
                        producer.send(queueReceive_sendResultsToServer, textMsg2);
                        
                        
                        break;
                        
                        
                        
                    case 3:
                        MoneyObj userMoney = null;
                        try {
                             userMoney = (MoneyObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        TypedQuery<Korisnik> userMoneyFound = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", userMoney.getUsername());
                        List<Korisnik> uMoney = new ArrayList<Korisnik>();
                        uMoney = userMoneyFound.getResultList();
                        
                        TextMessage textMsg3 = context.createTextMessage();
                        
                        
                        if(uMoney == null || uMoney.size() == 0){
                                try {
                                    textMsg3.setText("Korisnik sa datim korisnickim imenom ne postoji! \n");
                                } catch (JMSException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                        
                        else{
                                Korisnik newUserMoney = uMoney.get(0);
                               
                                newUserMoney.setNovac(newUserMoney.getNovac() + userMoney.getMoneyAmount());
                                

                                EntityTransaction transaction = entityManager.getTransaction();
                                transaction.begin();
                                entityManager.persist(newUserMoney);
                                entityManager.flush();
                                entityManager.refresh(newUserMoney);
                                transaction.commit();

                                try {
                                    textMsg3.setText("Uspesno dodavanje novca korisniku! \n");
                                } catch (JMSException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                                            
                        
                        producer.send(queueReceive_sendResultsToServer, textMsg3);
                        
                        break;
                        
                    case 4:
                        AddressCityObj userAddressCity = null;
                        try {
                             userAddressCity = (AddressCityObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        TypedQuery<Korisnik> userAddressCityFound = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", userAddressCity.getUsername());
                        List<Korisnik> uAddressCity= new ArrayList<Korisnik>();
                        uAddressCity = userAddressCityFound.getResultList();
                        
                        TextMessage textMsg4 = context.createTextMessage();
                        
                        
                        
                        
                        TypedQuery<Grad> cityForAddressCityUserFound = entityManager.createNamedQuery("Grad.findByIdG", Grad.class).setParameter("idG", userAddressCity.getCityId());
                       
                        List<Grad> cForAddressCityUserFound = new ArrayList<Grad>();
                        cForAddressCityUserFound = cityForAddressCityUserFound.getResultList();
                        
                        
                       
                        
                        if(cForAddressCityUserFound == null || cForAddressCityUserFound.size() == 0){
                            try {
                                textMsg4.setText("Grad sa datim nazivom ne postoji! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        
                        
                        
                        else{
                            
                            if(uAddressCity == null || uAddressCity.size() == 0){
                                    try {
                                        textMsg4.setText("Korisnik sa datim korisnickim imenom ne postoji! \n");
                                    } catch (JMSException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            }

                            else{
                                    Korisnik newUserAddressCity = uAddressCity.get(0);
                                    Grad g = cForAddressCityUserFound.get(0);

                                    newUserAddressCity.setAdresa(userAddressCity.getAddress());
                                    newUserAddressCity.setIdG(g);


                                    EntityTransaction transaction = entityManager.getTransaction();
                                    transaction.begin();
                                    entityManager.persist(newUserAddressCity);
                                    entityManager.flush();
                                    entityManager.refresh(newUserAddressCity);
                                    transaction.commit();

                                    try {
                                        textMsg4.setText("Uspesna promena adrese i grada korisnika! \n");
                                    } catch (JMSException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                        }
                                            
                        
                        producer.send(queueReceive_sendResultsToServer, textMsg4);
                        
                        break;
                        
                        
                    case 11:
                        
                        CartPrice transactionObj = null;
                        
                        try {
                             transactionObj = (CartPrice) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
               
                        TextMessage textMsg11 = context.createTextMessage();  

                      
                        List<Integer> allUsersMoney =  transactionObj.money;

                        TypedQuery<Korisnik> allUsersForTransactionFound = entityManager.createNamedQuery("Korisnik.findAll", Korisnik.class);
                       
                        List<Korisnik> allUsersForTransactionList = new ArrayList<Korisnik>();
                        allUsersForTransactionList = allUsersForTransactionFound.getResultList();
                        
                        EntityTransaction transaction = entityManager.getTransaction();
                        transaction.begin();

    


                        for(int i = 0; i < allUsersMoney.size(); i++){
                           

                           
                            allUsersForTransactionList.get(i).setNovac(allUsersMoney.get(i));
                          

                    

                            entityManager.persist(allUsersForTransactionList.get(i));
                            entityManager.flush();
                            entityManager.refresh(allUsersForTransactionList.get(i));
                        }



                        transaction.commit();

                        try {
                            textMsg11.setText("Transakcija je uspesno izvrsena! \n");
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }

   
                        
                        producer.send(queueReceive_sendResultsToServer, textMsg11);
                        
                        break;
                        
                        
                    case 12:
                        
                        TypedQuery<Grad> allCities = entityManager.createNamedQuery("Grad.findAll", Grad.class);
                        List<Grad> cities = new ArrayList<Grad>();
                        cities = allCities.getResultList();
                        
                        
                        String allCitiesString = "";
                        if(cities.isEmpty()) allCitiesString = "Nema gradova";
                       
                         
                        for (Grad g : cities){
                            allCitiesString +=  g.getNaziv()+"\n";                          
                        }
                        
                        TextMessage textMsg12 = context.createTextMessage();
                        
                        try {
                            textMsg12.setText(allCitiesString);
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                        producer.send(queueReceive_sendResultsToServer, textMsg12);
                                          
                        
                        break;
                        
                    case 13:
                       
                        TypedQuery<Korisnik> allUsers = entityManager.createNamedQuery("Korisnik.findAll",Korisnik.class);
                        //List<Korisnik> users = entityManager.createNamedQuery("Korisnik.findAll",Korisnik.class).getResultList();
                        List<Korisnik> users = new ArrayList<Korisnik>();
                        users = allUsers.getResultList();


                        String allUsersString = "";
                        if(users.isEmpty()) allUsersString = "Nema korisnika";
                       
                         
                        for (Korisnik k : users){
                           allUsersString +=  k.getIdK()+" "+k.getIme()+" "+k.getPrezime()+ " "+k.getAdresa()+"\n";                           
                        }
                        
                        TextMessage textMsg13 = context.createTextMessage();
                        
                        try {
                            textMsg13.setText(allUsersString);
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                        producer.send(queueReceive_sendResultsToServer, textMsg13);
                                          
                        break;

                }
                
                
            }   
                   
        } finally{
            
           /* entityManager.flush();
            entityManager.clear();
            entityManagerFactory.close();*/
        }        
       
    }
    
}
