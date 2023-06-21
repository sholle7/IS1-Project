package subsystem3;

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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import objects.*;


public class Main {
    @Resource(lookup="jms/__defaultConnectionFactory")
    public static ConnectionFactory connectionFactory;
    
    @Resource(lookup="queue_prodaja_artikala_send_3")
    public static Queue queueSend_receiveQueryFromServer_3;
    
    @Resource(lookup="queue_prodaja_artikala_receive_3")
    public static Queue queueReceive_sendResultsToServer_3;    
     
    
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    
    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("Subsystem3PU");
        entityManager = entityManagerFactory.createEntityManager();
        JMSConsumer consumer;
        
        try (JMSContext context = connectionFactory.createContext()) {
            consumer = context.createConsumer(queueSend_receiveQueryFromServer_3);
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
              
                        
                        TextMessage textMsg2 = context.createTextMessage();
 

                        if(u == null || u.size() > 0){
                            try {
                                textMsg2.setText("Korisnik sa datim korisnickim imenom vec postoji! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        else{

                            Korisnik newUser = new Korisnik();
                            
                            
                            /*Korpa ca = new Korpa();
                            ca.setIdKorisnik(newUser);
                            ca.setUkupnaCena(0);*/

                            
                            newUser.setAdresa(user.getAddress());
                            newUser.setIme(user.getFirstName());
                            newUser.setPrezime(user.getLastName());
                            newUser.setKorisnickoIme(user.getUsername());
                            newUser.setSifra(user.getPassword());
                            newUser.setNovac(0);
                            newUser.setIdG(user.getCityId());

                            EntityTransaction transaction = entityManager.getTransaction();
                            transaction.begin();
                            entityManager.persist(newUser);                               
                            entityManager.flush();
                            entityManager.refresh(newUser);

                            /*entityManager.persist(ca);
                            entityManager.flush();
                            entityManager.refresh(ca);*/
                            transaction.commit();


                            try {
                                textMsg2.setText("Korisnik je dodat u bazu podataka! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        
                        producer.send(queueReceive_sendResultsToServer_3, textMsg2);
                        
                        
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
                                            
                        
                        producer.send(queueReceive_sendResultsToServer_3, textMsg3);
                        
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
                        
                        

                        if(uAddressCity == null || uAddressCity.size() == 0){
                                try {
                                    textMsg4.setText("Korisnik sa datim korisnickim imenom ne postoji! \n");
                                } catch (JMSException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }

                        else{
                                Korisnik newUserAddressCity = uAddressCity.get(0);
                               

                                newUserAddressCity.setAdresa(userAddressCity.getAddress());
                                newUserAddressCity.setIdG(userAddressCity.getCityId());


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
                        
                                            
                        
                        producer.send(queueReceive_sendResultsToServer_3, textMsg4);
                        
                        break;  
                        
                        
                             
                    case 9:
                        
                        ArticleInCartObj articalInCartObjAdd = null;
                        try {
                             articalInCartObjAdd = (ArticleInCartObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        int idArtikla = articalInCartObjAdd.getIdArtikla();
                        int idKorpe = articalInCartObjAdd.getIdKorpe();
                        int popust = articalInCartObjAdd.getDiscount();
                        int cena = articalInCartObjAdd.getPrice();
                                             
                        
                        TypedQuery<Korisnik> userForAddCartFound = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", articalInCartObjAdd.getUsername());
                       
                        List<Korisnik> userInCartAddList = new ArrayList<Korisnik>();
                        userInCartAddList = userForAddCartFound.getResultList();
                        
                        Korisnik currentUser = userInCartAddList.get(0);
                        
                          
                        
                        TypedQuery<Stavka> itemFromU = entityManager.createQuery("SELECT s FROM Stavka s WHERE s.idKorpe = :idCart and s.idA = :articalId and s.idN is null",Stavka.class)
                                .setParameter("articalId", idArtikla).setParameter("idCart", idKorpe);
                        List<Stavka> itemsCurrentUser = new ArrayList<Stavka>();
                        itemsCurrentUser = itemFromU.getResultList();
                        
                        
                        
                        
                        TextMessage textMsg9 = context.createTextMessage();     
                        

                        if(userInCartAddList == null || userInCartAddList.size() == 0){
                            try {
                                textMsg9.setText("Korisnik sa datim korisnickim imenom ne postoji! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else{

                            if(itemsCurrentUser == null || itemsCurrentUser.size() == 0){
                                Stavka item = new Stavka();
                               


                                double multiplier = (100 - popust)/ 100.0;



                                item.setKolicina(articalInCartObjAdd.getArticleAmount());
                                item.setIdA(idArtikla);
                                item.setJedinicnaCena((int)(cena* multiplier));
                                item.setIdKorpe(idKorpe);


                                EntityTransaction transaction = entityManager.getTransaction();
                                
                                transaction.begin();
                                
                                entityManager.persist(item);
                                entityManager.flush();
                                entityManager.refresh(item);

                                transaction.commit();

                                try {
                                    textMsg9.setText("Artikal je uspesno dodat u korpu! \n");
                                } catch (JMSException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }


                            }

                            else  {
                             
                                Stavka itemNew = itemsCurrentUser.get(0);
                                double multiplier = (100 - popust)/ 100.0;

                              
                                itemNew.setKolicina(itemNew.getKolicina() + articalInCartObjAdd.getArticleAmount());                                   
                                itemNew.setJedinicnaCena((int)(cena* multiplier));


                                EntityTransaction transaction = entityManager.getTransaction();
                                transaction.begin();
                                entityManager.persist(itemNew);
                                entityManager.flush();
                                entityManager.refresh(itemNew);

                                transaction.commit();

                                try {
                                    textMsg9.setText("Artikal je uspesno dodat u korpu! \n");
                                } catch (JMSException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                            
                                       
                      
                        }
                        
                        producer.send(queueReceive_sendResultsToServer_3, textMsg9);
                        break;
   
                        
                        
                        
                    case 10:
                      
                        
                        DeleteArticleInCartObj articalInCartObjSub = null;
                        try {
                             articalInCartObjSub = (DeleteArticleInCartObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        int idArtiklaSub = articalInCartObjSub.getIdArtikla();
                        int idKorpeSub = articalInCartObjSub.getIdKorpe();
                        int popustSub = articalInCartObjSub.getDiscount();
                        int cenaSub = articalInCartObjSub.getPrice();
                        
                        
                        TypedQuery<Korisnik> userForSubCartFound = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", articalInCartObjSub.getUsername());
                       
                        List<Korisnik> userInCartSubList = new ArrayList<Korisnik>();
                        userInCartSubList = userForSubCartFound.getResultList();
                        
                        Korisnik currentUserSub = userInCartSubList.get(0);
                      
                        
                        TypedQuery<Stavka> itemFromUSub = entityManager.createQuery("SELECT s FROM Stavka s WHERE s.idKorpe = :idCart and s.idA = :currentidA and s.idN is null",Stavka.class)
                                .setParameter("currentidA", articalInCartObjSub.getIdArtikla()).setParameter("idCart", articalInCartObjSub.getIdKorpe());
                        List<Stavka> itemsCurrentUserSub = new ArrayList<Stavka>();
                        itemsCurrentUserSub = itemFromUSub.getResultList();
                        
                        
  
                        TextMessage textMsg10 = context.createTextMessage();     
                        
  
                        if(userInCartSubList == null || userInCartSubList.size() == 0){
                            try {
                                textMsg10.setText("Korisnik sa datim korisnickim imenom ne postoji! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else{
                            if(itemsCurrentUserSub == null ||itemsCurrentUserSub.size()==0 ){
                                    try {
                                    textMsg10.setText("Stavka sa datim nazivom nije u korpi! \n");
                                } catch (JMSException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else{
                                Stavka currItem = itemsCurrentUserSub.get(0);

                                if(currItem.getKolicina() - articalInCartObjSub.getArticleAmount() > 0){

                                    int newAmount = currItem.getKolicina() - articalInCartObjSub.getArticleAmount();

                                    currItem.setKolicina(newAmount);

                                    EntityTransaction transaction = entityManager.getTransaction();
                                    transaction.begin();

                                 
                                    entityManager.persist(currItem);
                                    entityManager.flush();
                                    entityManager.refresh(currItem);


                                    transaction.commit();

                                    try {
                                        textMsg10.setText("Artikal je uspesno izbacen iz korpe! \n");
                                    } catch (JMSException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                                else if(currItem.getKolicina() - articalInCartObjSub.getArticleAmount() == 0){

                                    int newAmount = currItem.getKolicina() - articalInCartObjSub.getArticleAmount();


 

                                    EntityTransaction transaction = entityManager.getTransaction();
                                    transaction.begin();

                                    entityManager.remove(currItem);
                                    entityManager.flush();

                                    transaction.commit();

                                    try {
                                        textMsg10.setText("Artikal je uspesno izbacen iz korpe! \n");
                                    } catch (JMSException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }

                                else{
                                    try {
                                        textMsg10.setText("Pokusavate oduzeti vecu kolicinu artikala nego sto je u korpi! \n");
                                    } catch (JMSException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            
                                
     
                            }
                      
                        }
                        
                        
                        producer.send(queueReceive_sendResultsToServer_3, textMsg10);
                        break;


                    case 11:
                        
                        CartPrice transactionObj = null;
                        
                        try {
                             transactionObj = (CartPrice) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                        
                        TypedQuery<Korisnik> userForTransactionFound = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", transactionObj.getUsername());
                       
                        List<Korisnik> userForTransactionList = new ArrayList<Korisnik>();
                        userForTransactionList = userForTransactionFound.getResultList();
                        
                        
                        
                        TextMessage textMsg11 = context.createTextMessage();  
                        
     
                        UserMoneyTransaction moneyTransaction = null;


                        if(userForTransactionList == null|| userForTransactionList.size() == 0){
                            try {
                               textMsg11.setText("Korisnik sa datim korisnickim imenom ne postoji! \n");
                            } catch (JMSException ex) {
                               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        
                        else{
                            
                            Korisnik currentUserTransaction = userForTransactionList.get(0);
                            
                            TypedQuery<Stavka> itemFromUs = entityManager.createQuery("SELECT s FROM Stavka s WHERE s.idKorpe = :cartlId and s.idN is null",Stavka.class).setParameter("cartlId", currentUserTransaction.getIdK());
                            List<Stavka> itemsCurrentUs = new ArrayList<Stavka>();
                            itemsCurrentUs = itemFromUs.getResultList();                         

                            moneyTransaction = new UserMoneyTransaction();
                            
                            if(currentUserTransaction.getNovac() < transactionObj.getCurrentCartPrice()){
                                try {
                                   textMsg11.setText("Korisnik nema dovoljno novca da izvrsi transakciju! \n");
                                } catch (JMSException ex) {
                                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            else{
                                if(transactionObj.getCurrentCartPrice() == 0){
                                    try {
                                       textMsg11.setText("Korisnik nema nijedan artikal u korpi! \n");
                                    } catch (JMSException ex) {
                                       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                                else {
                                    Narudzbina n = new Narudzbina();
                                    Transakcija t = new Transakcija();

                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 

                                    LocalDateTime now = LocalDateTime.now();
                                    String currDateString = dtf.format(now);
                                    java.util.Date currDate = null; 

                                    try {
                                        currDate = format.parse(currDateString);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    n.setAdresa(currentUserTransaction.getAdresa());
                                    n.setIdG(currentUserTransaction.getIdG());
                                    n.setIdK(currentUserTransaction);
                                    n.setVremeKreiranja(currDate);
                                    n.setUkupnaCena(transactionObj.getCurrentCartPrice());

                                    t.setIdN(n);
                                    t.setVremePlacanja(currDate);
                                    t.setSumaPlacanja(transactionObj.getCurrentCartPrice());



                                    EntityTransaction transaction = entityManager.getTransaction();
                                    transaction.begin();

                                    entityManager.persist(n);
                                    entityManager.flush();
                                    entityManager.refresh(n);

                                    entityManager.persist(t);
                                    entityManager.flush();
                                    entityManager.refresh(t);


                                    for(Stavka i: itemsCurrentUs){
                                        int idK = i.getIdKorpe();
                                        
                                        TypedQuery<Korisnik> userTra = entityManager.createNamedQuery("Korisnik.findByIdK", Korisnik.class).setParameter("idK", idK);
                                        List<Korisnik> userForTraList = new ArrayList<Korisnik>();
                                        userForTraList = userTra.getResultList();
                                        
                                        Korisnik cUser = userForTraList.get(0);
                                        cUser.setNovac(cUser.getNovac() + i.getJedinicnaCena() * i.getKolicina());
                                        i.setIdN(n);
                                        
                                        transactionObj.setIdN(n.getIdN());
                                       
                                        entityManager.persist(i);
                                        entityManager.flush();
                                        entityManager.refresh(i);

                                        entityManager.persist(cUser);
                                        entityManager.flush();
                                        entityManager.refresh(cUser);
                                    }



                                    currentUserTransaction.setNovac(currentUserTransaction.getNovac() - transactionObj.getCurrentCartPrice());
                                    

                                    entityManager.persist(currentUserTransaction);
                                    entityManager.flush();
                                    entityManager.refresh(currentUserTransaction);
                                    
                                    List<Integer> allUsersMoney =  transactionObj.money;
                                    
                                    TypedQuery<Korisnik> allUsersForTransactionFound = entityManager.createNamedQuery("Korisnik.findAll", Korisnik.class);
                                    List<Korisnik> allUsersForTransactionList = new ArrayList<Korisnik>();
                                    allUsersForTransactionList = allUsersForTransactionFound.getResultList();
                                    
 
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
                                }
                            }

                            

                        }
                        /*
                        TypedQuery<Korisnik> allUsersForTransactionFound = entityManager.createNamedQuery("Korisnik.findAll", Korisnik.class);
                       
                        List<Korisnik> allUsersForTransactionList = new ArrayList<Korisnik>();
                        allUsersForTransactionList = allUsersForTransactionFound.getResultList();
                        
                        for(Korisnik us : allUsersForTransactionList){
                            moneyTransaction.money.add(us.getNovac());
                        }*/
                        
                        ObjectMessage objMsg11 = context.createObjectMessage(transactionObj); 
                        producer.send(queueReceive_sendResultsToServer_3, objMsg11);
                        break;
                        
                    case 17:
                        
                        
                        AllOrdersFromUserObj allOrdersFromUser = null;
                        
                        try {
                             allOrdersFromUser = (AllOrdersFromUserObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                        
                        TypedQuery<Korisnik> userQuery = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", allOrdersFromUser.getUsername());
                       
                        List<Korisnik> userL = new ArrayList<Korisnik>();
                        userL = userQuery.getResultList();
                        
                        Korisnik userNar = userL.get(0);
                        
                        //Korisnik u = entityManager.find(Korisnik.class, allOrdersFromUser.getUsername());
                        
                        TypedQuery<Narudzbina> ordersFromUser = entityManager.createQuery("SELECT n FROM Narudzbina n WHERE n.idK = :userId",Narudzbina.class).setParameter("userId", userNar);
                        
                        
                        
                        List<Narudzbina> orders = new ArrayList<Narudzbina>();
                        orders = ordersFromUser.getResultList();
                        
                        
                        String ordersString = "";
                        for (Narudzbina n : orders){

                            ordersString += "Vreme kreiranja: " + n.getVremeKreiranja()+ ", Adresa: " + n.getAdresa()  + ", "+ ", Grad: " + n.getIdG() +"\n";
                        }
                       
                        
                        TextMessage textMsg17 = context.createTextMessage();
                        
                        try {
                            textMsg17.setText(ordersString);
                            
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                        producer.send(queueReceive_sendResultsToServer_3, textMsg17);
                                       
                        
                        break;
   
                    case 18:
                        
                        TypedQuery<Narudzbina> allOrds = entityManager.createNamedQuery("Narudzbina.findAll",Narudzbina.class);                       
                        List<Narudzbina> ords = new ArrayList<Narudzbina>();
                        ords = allOrds.getResultList();


                        String allOrdsString = "";
                        if(ords.isEmpty()) allOrdsString = "Nema narudzbina";
                       
                         
                        for (Narudzbina n : ords){  
                            allOrdsString += "Vreme kreiranja: " + n.getVremeKreiranja()+ ", Adresa: " + n.getAdresa()  + ", "+ ", Grad: " + n.getIdG() +"\n";          
                        }
                        
                        TextMessage textMsg18 = context.createTextMessage();
                        
                        try {
                            textMsg18.setText(allOrdsString);                            
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                        
                       
                        producer.send(queueReceive_sendResultsToServer_3, textMsg18);
                                                          
                        
                        break;
                        
                    case 19:

                        TypedQuery<Transakcija> allTranscations = entityManager.createNamedQuery("Transakcija.findAll",Transakcija.class);                       
                        List<Transakcija> transactions = new ArrayList<Transakcija>();
                        transactions = allTranscations.getResultList();


                        String allTransactionsString = "";
                        if(transactions.isEmpty()) allTransactionsString = "Nema transkacija";
                       
                         
                        for (Transakcija t : transactions){  
                            allTransactionsString += "Vreme placanja: " + t.getVremePlacanja()+ ", Suma placanja: " + t.getSumaPlacanja() + ", "+ ", Korisnik: " + t.getIdN().getIdK().getKorisnickoIme() +"\n";          
                        }
                        
                        TextMessage textMsg19 = context.createTextMessage();
                        
                        try {
                            textMsg19.setText(allTransactionsString);                            
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                        
                       
                        producer.send(queueReceive_sendResultsToServer_3, textMsg19);
                        
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
