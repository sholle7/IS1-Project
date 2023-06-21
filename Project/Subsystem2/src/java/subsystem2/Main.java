package subsystem2;


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
    
    @Resource(lookup="queue_prodaja_artikala_send_2")
    public static Queue queueSend_receiveQueryFromServer_2;
    
    @Resource(lookup="queue_prodaja_artikala_receive_2")
    public static Queue queueReceive_sendResultsToServer_2;    
     
    
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    
    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("Subsystem2PU");
        entityManager = entityManagerFactory.createEntityManager();
        JMSConsumer consumer;
        
        try (JMSContext context = connectionFactory.createContext()) {
            consumer = context.createConsumer(queueSend_receiveQueryFromServer_2);
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
                            

                            Korpa ca = new Korpa();
                            ca.setIdKorisnik(newUser);
                            ca.setUkupnaCena(0);


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

                            entityManager.persist(ca);
                            entityManager.flush();
                            entityManager.refresh(ca);
                            transaction.commit();


                            try {
                                textMsg2.setText("Korisnik je dodat u bazu podataka! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        
                        producer.send(queueReceive_sendResultsToServer_2, textMsg2);
                        
                        
                        break;
                    case 5:
                        
                        CategoryObj category = null;
                        try {
                             category = (CategoryObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        TypedQuery<Kategorija> categoryFound = entityManager.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).setParameter("naziv", category.getCategoryName());
                       
                        List<Kategorija> cat = new ArrayList<Kategorija>();
                        cat = categoryFound.getResultList();
                        
                        TextMessage textMsg5 = context.createTextMessage();
                        
                        if(cat == null || cat.size() > 0){
                            try {
                                textMsg5.setText("Kategorija sa datim nazivom vec postoji! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        else{
                            Kategorija newCategory = new Kategorija();
                            newCategory.setNaziv(category.getCategoryName());
                            EntityTransaction transaction = entityManager.getTransaction();
                            transaction.begin();
                            entityManager.persist(newCategory);
                            entityManager.flush();
                            entityManager.refresh(newCategory);
                            transaction.commit();
                            
                            try {
                                textMsg5.setText("Kategorija je dodata u bazu podataka! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        producer.send(queueReceive_sendResultsToServer_2, textMsg5);
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
                                            
                        
                        producer.send(queueReceive_sendResultsToServer_2, textMsg3);
                        
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
                        
                                            
                        
                        producer.send(queueReceive_sendResultsToServer_2, textMsg4);
                        
                        break;
    
                        
                    case 6:
                        
                        ArticleObj artical = null;
                        try {
                             artical = (ArticleObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        TypedQuery<Artikal> articalFound = entityManager.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", artical.getArticleName());
                       
                        List<Artikal> artUser = new ArrayList<Artikal>();
                        artUser = articalFound.getResultList();
                        
                        
                        TypedQuery<Kategorija> categoryForArticalFound = entityManager.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).setParameter("naziv", artical.getCategoryName());
                       
                        List<Kategorija> catForArtical = new ArrayList<Kategorija>();
                        catForArtical = categoryForArticalFound.getResultList();
                        
                        
                        TypedQuery<Korisnik> userIsFound = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", artical.getUsername());
                       
                        List<Korisnik> userArt = new ArrayList<Korisnik>();
                        userArt = userIsFound.getResultList();
                        
                        
                        
                        TextMessage textMsg6 = context.createTextMessage();
                        
                        
                        if(catForArtical == null || catForArtical.size() == 0){
                            try {
                                textMsg6.setText("Kategorija sa datim nazivom ne postoji! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        else{
                            
                            if(userArt == null || userArt.size() == 0){
                                try {
                                    textMsg6.setText("Korisnik sa datim korisnickim imenom ne postoji! \n");
                                } catch (JMSException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else{
                            
                                if(artUser == null || artUser.size() > 0){
                                    try {
                                        textMsg6.setText("Artikal sa datim nazivom vec postoji! \n");
                                    } catch (JMSException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                                else{
                                    Artikal newArtical = new Artikal();
                                    newArtical.setCena(artical.getArticlePrice());
                                    newArtical.setNaziv(artical.getArticleName());
                                    newArtical.setOpis(artical.getArticleAbout());
                                    newArtical.setIdKat(catForArtical.get(0));
                                    newArtical.setIdKor(userArt.get(0));
                                    newArtical.setPopust(0);
                                    
                                    EntityTransaction transaction = entityManager.getTransaction();
                                    transaction.begin();
                                    entityManager.persist(newArtical);
                                    entityManager.flush();
                                    entityManager.refresh(newArtical);
                                    transaction.commit();

                                    try {
                                        textMsg6.setText("Artikal je dodat u bazu podataka! \n");
                                    } catch (JMSException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        }
                        
                        producer.send(queueReceive_sendResultsToServer_2, textMsg6);
                        
                        
                        break;
                        
                        
                        
                    case 7:
                        UpdatePriceArticleObj articalForUpdate = null;
                        try {
                             articalForUpdate = (UpdatePriceArticleObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        TypedQuery<Artikal> articalForUpdateFound = entityManager.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", articalForUpdate.getArticleName());
                       
                        List<Artikal> artUForUpdate = new ArrayList<Artikal>();
                        artUForUpdate = articalForUpdateFound.getResultList();
                        
                        TextMessage textMsg7 = context.createTextMessage();
                        
                        
                        if(artUForUpdate == null || artUForUpdate.size() == 0){
                            try {
                                textMsg7.setText("Artikal sa datim nazivom ne postoji! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        else{
                            Artikal articalPrice = artUForUpdate.get(0);
                            articalPrice.setCena(articalForUpdate.getArticlePrice());


                            EntityTransaction transaction = entityManager.getTransaction();
                            transaction.begin();
                            entityManager.persist(articalPrice);
                            entityManager.flush();
                            entityManager.refresh(articalPrice);
                            transaction.commit();

                            try {
                                textMsg7.setText("Cena artikla je uspesno izmenjena! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        
                        }
                        
                        producer.send(queueReceive_sendResultsToServer_2, textMsg7);
                        
                        break;
                        
                    case 8:
                        SetArticleDiscountObj articalForDiscount = null;
                        try {
                             articalForDiscount = (SetArticleDiscountObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        TypedQuery<Artikal> articalForDiscountFound = entityManager.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", articalForDiscount.getArticleName());
                       
                        List<Artikal> artUDiscount = new ArrayList<Artikal>();
                        artUDiscount = articalForDiscountFound.getResultList();
                        
                        TextMessage textMsg8 = context.createTextMessage();
                        
                        
                        if(artUDiscount == null || artUDiscount.size() == 0){
                            try {
                                textMsg8.setText("Artikal sa datim nazivom ne postoji! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        else{
                            Artikal newArticalForDiscount = artUDiscount.get(0);
                            newArticalForDiscount.setPopust(articalForDiscount.getArticleDiscount());


                            EntityTransaction transaction = entityManager.getTransaction();
                            transaction.begin();
                            entityManager.persist(newArticalForDiscount);
                            entityManager.flush();
                            entityManager.refresh(newArticalForDiscount);
                            transaction.commit();

                            try {
                                textMsg8.setText("Popust artikla je uspesno izmenjen! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        
                        }
                        
                        producer.send(queueReceive_sendResultsToServer_2, textMsg8);
                        
                        break;
                        
                        
                    case 9:
                        ArticleInCartObj articalInCartObjAdd = null;
                        try {
                             articalInCartObjAdd = (ArticleInCartObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        TypedQuery<Artikal> articalForAddInCartFound = entityManager.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", articalInCartObjAdd.getArticleName());
                        
                        List<Artikal> articalInCartAddList = new ArrayList<Artikal>();
                        articalInCartAddList = articalForAddInCartFound.getResultList();
                        
                        
                        
                        TypedQuery<Korisnik> userForAddCartFound = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", articalInCartObjAdd.getUsername());
                       
                        List<Korisnik> userInCartAddList = new ArrayList<Korisnik>();
                        userInCartAddList = userForAddCartFound.getResultList();
                        
                        Korisnik currentUser = userInCartAddList.get(0);
                        
                       
                        
                       
                        TypedQuery<Korpa> cartFromU = entityManager.createQuery("SELECT k FROM Korpa k WHERE k.idKorisnik = :userId",Korpa.class).setParameter("userId", currentUser);
                        Korpa cartCurrentUser = new Korpa();
                        cartCurrentUser = cartFromU.getSingleResult();
                        
                        
                        
                        TypedQuery<Stavka> itemFromU = entityManager.createQuery("SELECT s FROM Stavka s WHERE s.idKorpe = :idCart and s.idA = :articalId and s.idN is null",Stavka.class)
                                .setParameter("articalId", articalInCartAddList.get(0)).setParameter("idCart", cartCurrentUser);
                        List<Stavka> itemsCurrentUser = new ArrayList<Stavka>();
                        itemsCurrentUser = itemFromU.getResultList();
                        
                        
                        ArticleInCartObj newA = new ArticleInCartObj(articalInCartObjAdd.getUsername(), articalInCartObjAdd.getArticleName(), articalInCartObjAdd.getArticleAmount());
                                    
                        
                        TextMessage textMsg9 = context.createTextMessage();     
                        
                        if(articalInCartAddList == null || articalInCartAddList.size() == 0){
                            try {
                                textMsg9.setText("Artikal sa datim nazivom ne postoji! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else{
                            
                            
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
                                    Artikal articalFirst = articalInCartAddList.get(0);
                                    
                                    
                                    double multiplier = (100 - articalFirst.getPopust())/ 100.0;
                                    
                                    
                                    cartCurrentUser.setUkupnaCena(cartCurrentUser.getUkupnaCena() + articalInCartObjAdd.getArticleAmount() * (int)(articalFirst.getCena() * multiplier));

                                    item.setKolicina(articalInCartObjAdd.getArticleAmount());
                                    item.setIdA(articalFirst);
                                    item.setJedinicnaCena((int)(articalFirst.getCena() * multiplier));
                                    item.setIdKorpe(cartCurrentUser);                                                             
                                    
                                    newA.setIdArtikla(articalFirst.getIdA());
                                    newA.setIdKorpe(cartCurrentUser.getIdKorpe());
                                    newA.setDiscount(articalFirst.getPopust());
                                    newA.setPrice(articalFirst.getCena());

                                    EntityTransaction transaction = entityManager.getTransaction();
                                    transaction.begin();
                                    entityManager.persist(item);
                                    entityManager.flush();
                                    entityManager.refresh(item);

                                    entityManager.persist(cartCurrentUser);
                                    entityManager.flush();
                                    entityManager.refresh(cartCurrentUser);

                                    transaction.commit();

                                    try {
                                        textMsg9.setText("Artikal je uspesno dodat u korpu! \n");
                                    } catch (JMSException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                
     
                                }
                                
                                else  {
                                                   
                                    Artikal articalFirst = articalInCartAddList.get(0);
                                    Stavka itemNew = itemsCurrentUser.get(0);
                                    double multiplier = (100 - articalFirst.getPopust())/ 100.0;

                                    cartCurrentUser.setUkupnaCena(cartCurrentUser.getUkupnaCena() + articalInCartObjAdd.getArticleAmount() * (int)(articalFirst.getCena() * multiplier));
                                    itemNew.setKolicina(itemNew.getKolicina() + articalInCartObjAdd.getArticleAmount());                                   
                                    itemNew.setJedinicnaCena((int)(articalFirst.getCena() * multiplier));

                                    newA.setIdArtikla(articalFirst.getIdA());
                                    newA.setIdKorpe(cartCurrentUser.getIdKorpe());
                                    newA.setDiscount(articalFirst.getPopust());
                                    newA.setPrice(articalFirst.getCena());
                                    
                                    EntityTransaction transaction = entityManager.getTransaction();
                                    transaction.begin();
                                    entityManager.persist(itemNew);
                                    entityManager.flush();
                                    entityManager.refresh(itemNew);

                                    entityManager.persist(cartCurrentUser);
                                    entityManager.flush();
                                    entityManager.refresh(cartCurrentUser);

                                    transaction.commit();

                                    try {
                                        textMsg9.setText("Artikal je uspesno dodat u korpu! \n");
                                    } catch (JMSException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
 
                                }
                            }
                                       
                      
                        }
                        
                        ObjectMessage objMsg9 = context.createObjectMessage(newA);
                        producer.send(queueReceive_sendResultsToServer_2, objMsg9);
                        break;
                        
                    case 10:
                      
                        
                        DeleteArticleInCartObj articalInCartObjSub = null;
                        try {
                             articalInCartObjSub = (DeleteArticleInCartObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        TypedQuery<Artikal> articalForSubInCartFound = entityManager.createNamedQuery("Artikal.findByNaziv", Artikal.class).setParameter("naziv", articalInCartObjSub.getArticleName());
                        
                        List<Artikal> articalInCartSubList = new ArrayList<Artikal>();
                        articalInCartSubList = articalForSubInCartFound.getResultList();
                        
                        
                        
                        TypedQuery<Korisnik> userForSubCartFound = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", articalInCartObjSub.getUsername());
                       
                        List<Korisnik> userInCartSubList = new ArrayList<Korisnik>();
                        userInCartSubList = userForSubCartFound.getResultList();
                        
                        Korisnik currentUserSub = userInCartSubList.get(0);
                        
                        
                        
                        TypedQuery<Korpa> cartFromUSub = entityManager.createQuery("SELECT k FROM Korpa k WHERE k.idKorisnik = :userId",Korpa.class).setParameter("userId", currentUserSub);
                        Korpa cartCurrentUserSub = new Korpa();
                        cartCurrentUserSub = cartFromUSub.getSingleResult();
                        
                       
                        
                      
                        
                        TypedQuery<Stavka> itemFromUSub = entityManager.createQuery("SELECT s FROM Stavka s WHERE s.idKorpe = :idCart and s.idA = :currentidA and s.idN is null",Stavka.class)
                                .setParameter("currentidA", articalInCartSubList.get(0)).setParameter("idCart", cartCurrentUserSub);
                        List<Stavka> itemsCurrentUserSub = new ArrayList<Stavka>();
                        itemsCurrentUserSub = itemFromUSub.getResultList();
                        
                        DeleteArticleInCartObj newSubA = new DeleteArticleInCartObj(articalInCartObjSub.getUsername(), articalInCartObjSub.getArticleName(), articalInCartObjSub.getArticleAmount());
  
                        TextMessage textMsg10 = context.createTextMessage();     
                        
                        if(articalInCartSubList == null || articalInCartSubList.size() == 0){
                            try {
                                textMsg10.setText("Artikal sa datim nazivom ne postoji! \n");
                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else{
                            
                            
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


                                        cartCurrentUserSub.setUkupnaCena(cartCurrentUserSub.getUkupnaCena() - currItem.getJedinicnaCena() * articalInCartObjSub.getArticleAmount());

                                        currItem.setKolicina(newAmount);
                                        
                                        
                                        
                                        newSubA.setIdArtikla(currItem.getIdA().getIdA());
                                        newSubA.setIdKorpe(cartCurrentUserSub.getIdKorpe());
                                        newSubA.setDiscount(currItem.getIdA().getPopust());
                                        newSubA.setPrice(currItem.getIdA().getCena());
                                        
                                        

                                        EntityTransaction transaction = entityManager.getTransaction();
                                        transaction.begin();

                                        entityManager.persist(cartCurrentUserSub);
                                        entityManager.flush(); 
                                        entityManager.refresh(cartCurrentUserSub);

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


                                        cartCurrentUserSub.setUkupnaCena(cartCurrentUserSub.getUkupnaCena() - currItem.getJedinicnaCena() * articalInCartObjSub.getArticleAmount());

                                           
                                      
                                        
                                        newSubA.setIdArtikla(currItem.getIdA().getIdA());
                                        newSubA.setIdKorpe(cartCurrentUserSub.getIdKorpe());
                                        newSubA.setDiscount(currItem.getIdA().getPopust());
                                        newSubA.setPrice(currItem.getIdA().getCena());
                                        
                                        
                                        EntityTransaction transaction = entityManager.getTransaction();
                                        transaction.begin();

                                        entityManager.persist(cartCurrentUserSub);
                                        entityManager.flush(); 
                                        entityManager.refresh(cartCurrentUserSub);

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
                      
                        }
                        
                        ObjectMessage objMsg10 = context.createObjectMessage(newSubA);
                        producer.send(queueReceive_sendResultsToServer_2, objMsg10);
                        break;
                         
                        
                        
                    case 11:
                        
                        MakeTransactionObj transactionObj = null;
                        
                        try {
                             transactionObj = (MakeTransactionObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                        
                        TypedQuery<Korisnik> userForTransactionFound = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", transactionObj.getUsername());
                       
                        List<Korisnik> userForTransactionList = new ArrayList<Korisnik>();
                        userForTransactionList = userForTransactionFound.getResultList();
                        
                        
                        
                        TextMessage textMsg11 = context.createTextMessage();  
                        
                        CartPrice cartPrice = new CartPrice("", 0);
                        
                        if(userForTransactionList == null|| userForTransactionList.size() == 0){
                            try {
                               textMsg11.setText("Korisnik sa datim korisnickim imenom ne postoji! \n");
                            } catch (JMSException ex) {
                               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        
                        else{
                             
                            Korisnik currentUs = userForTransactionList.get(0);
                            
                            TypedQuery<Korpa> cartFromUs = entityManager.createQuery("SELECT k FROM Korpa k WHERE k.idKorisnik = :userId",Korpa.class).setParameter("userId", currentUs);
                            Korpa cartFromUserTransactiom = new Korpa();
                            cartFromUserTransactiom = cartFromUs.getSingleResult();

                            TypedQuery<Stavka> itemFromUs = entityManager.createQuery("SELECT s FROM Stavka s WHERE s.idKorpe = :cartlId and s.idN is null",Stavka.class).setParameter("cartlId", cartFromUserTransactiom);
                            List<Stavka> itemsCurrentUs = new ArrayList<Stavka>();
                            itemsCurrentUs = itemFromUs.getResultList();                         

                            TypedQuery<Artikal> allArticals = entityManager.createNamedQuery("Artikal.findAll", Artikal.class);
                            List<Artikal> allArticalsList = new ArrayList<Artikal>();
                            allArticalsList = allArticals.getResultList();
                            

                            if(currentUs.getNovac() < cartFromUserTransactiom.getUkupnaCena()){
                                try {
                                   textMsg11.setText("Korisnik nema dovoljno novca da izvrsi transakciju! \n");
                                } catch (JMSException ex) {
                                   Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            else{
                                
                                if(cartFromUserTransactiom.getUkupnaCena() == 0){
                                    try {
                                       textMsg11.setText("Korisnik nema nijedan artikal u korpi! \n");
                                    } catch (JMSException ex) {
                                       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                                else {
                                    EntityTransaction transaction = entityManager.getTransaction();
                                    transaction.begin();

                                    for(Stavka i: itemsCurrentUs){
                                        Korisnik cUser = i.getIdA().getIdKor();
                                        cUser.setNovac(i.getIdA().getIdKor().getNovac() + i.getJedinicnaCena() * i.getKolicina());
                                       

                                        entityManager.persist(cUser);
                                        entityManager.flush();
                                        entityManager.refresh(cUser);
                                    }


                                    cartPrice.setUsername(currentUs.getKorisnickoIme());
                                    cartPrice.setCurrentCartPrice(cartFromUserTransactiom.getUkupnaCena());
                                    
                                   
                                    
                                    currentUs.setNovac(currentUs.getNovac() - cartFromUserTransactiom.getUkupnaCena());
                                    cartFromUserTransactiom.setUkupnaCena(0);

                                    entityManager.persist(currentUs);
                                    entityManager.flush();
                                    entityManager.refresh(currentUs);

                                    entityManager.persist(cartFromUserTransactiom);
                                    entityManager.flush();
                                    entityManager.refresh(cartFromUserTransactiom);



                                    transaction.commit();

                                    try {
                                        textMsg11.setText("Transakcija je uspesno izvrsena! \n");
                                    } catch (JMSException ex) {
                                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                            

                        }
                        
                        TypedQuery<Korisnik> allUsersForTransactionFound = entityManager.createNamedQuery("Korisnik.findAll", Korisnik.class);
                       
                        List<Korisnik> allUsersForTransactionList = new ArrayList<Korisnik>();
                        allUsersForTransactionList = allUsersForTransactionFound.getResultList();
                        
                        for(Korisnik us : allUsersForTransactionList){
                            cartPrice.money.add(us.getNovac());
                        }
                         
                        ObjectMessage objMsg11 = context.createObjectMessage(cartPrice);         
                        producer.send(queueReceive_sendResultsToServer_2, objMsg11);
                        
                        break;
                        
        
                        
                    case 14:
                                           
                      
                        
                        TypedQuery<Kategorija> allCategories = entityManager.createNamedQuery("Kategorija.findAll",Kategorija.class);                       
                        List<Kategorija> categories = new ArrayList<Kategorija>();
                        categories = allCategories.getResultList();


                        String allCategoriesString = "";
                        if(categories.isEmpty()) allCategoriesString = "Nema kategorija";
                       
                         
                        for (Kategorija k : categories){                         
                            allCategoriesString +=  k.getNaziv()+"\n";             
                        }
                        
                        TextMessage textMsg14 = context.createTextMessage();
                        
                        try {
                            textMsg14.setText(allCategoriesString);                            
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                        
                       
                        producer.send(queueReceive_sendResultsToServer_2, textMsg14);
                                                          
                        
                        break;

                        
                    case 15:
                        
                        AllArticlesFromUserObj art = null;
                        try {
                             art = (AllArticlesFromUserObj) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                        TypedQuery<Korisnik> userQuery = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", art.getUsername());
                       
                        List<Korisnik> userL = new ArrayList<Korisnik>();
                        userL = userQuery.getResultList();
                        
                        Korisnik userA = userL.get(0);
                        
                    
                        //Korisnik u = entityManager.find(Korisnik.class, art.getUsername());
                        
                        TypedQuery<Artikal> allArticlesFromUser = entityManager.createQuery("SELECT a FROM Artikal a WHERE a.idKor = :userId",Artikal.class).setParameter("userId", userA);
                        
                        List<Artikal> articals = new ArrayList<Artikal>();
                        articals = allArticlesFromUser.getResultList();


                        String allArticalsFromUserString = "";
                        if(articals.isEmpty()) allArticalsFromUserString = "Nema korisnika";
                       
                         
                        for (Artikal a : articals){
                            allArticalsFromUserString +="Naziv: " + a.getNaziv() + ", Opis: " + a.getOpis() +"\n";
               
                        }
                        
                        TextMessage textMsg15 = context.createTextMessage();
                        
                        try {
                            textMsg15.setText(allArticalsFromUserString);
                            
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       
                        producer.send(queueReceive_sendResultsToServer_2, textMsg15);
                       
                        
                        break;
                    case 16:
                        
                        CartFromUser cart = null;
                        try {
                             cart = (CartFromUser) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        TextMessage textMsg16 = context.createTextMessage();
                        
                        TypedQuery<Korisnik> userQueryL = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", cart.getUsername());
                       
              
                        List<Korisnik> userLi = new ArrayList<Korisnik>();
                        userLi = userQueryL.getResultList();
                        
                        
                        if(userLi == null || userLi.size() == 0){
                                try {
                                    textMsg16.setText("Korisnik sa datim korisnickim imenom ne postoji! \n");
                                } catch (JMSException ex) {
                                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                        else{
                        
                        
                            Korisnik us = userLi.get(0);



                            //Korisnik us = entityManager.find(Korisnik.class, cart.getUsername());

                            TypedQuery<Korpa> cartFromUser = entityManager.createQuery("SELECT k FROM Korpa k WHERE k.idKorisnik = :userId",Korpa.class).setParameter("userId", us);

                            Korpa cartUser = new Korpa();
                            cartUser = cartFromUser.getSingleResult();

                            List<Stavka> items = new ArrayList<>();
                            items = cartUser.getStavkaList();

                            String cartUserString = "";
                            for (Stavka s : items){
                                
                                
                                if(s.getIdN() == null){
                                    Artikal a =  s.getIdA();

                                    cartUserString += "Naziv: " + a.getNaziv()+ ", Opis:" + a.getOpis() + ", Kolicina:"+ s.getKolicina()+"\n";
                                }

                            }
                            //cartUserString += "Id korpe je: " + cartUser.getIdKorpe()+ " od korisnika sa id: " + cartUser.getIdKorisnik().getIdK() +"\n";
                            if(cartUserString =="") cartUserString="Korpa je prazna! \n";


                            try {
                                textMsg16.setText(cartUserString);

                            } catch (JMSException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        producer.send(queueReceive_sendResultsToServer_2, textMsg16);
                        
                        break;
                        
                        
                        
                    case 20:
                        
                        CartPrice orderObj = null;
                        
                        try {
                             orderObj = (CartPrice) objectMessage.getObject();
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                        TypedQuery<Korisnik> userForOrderUpdateFound = entityManager.createNamedQuery("Korisnik.findByKorisnickoIme", Korisnik.class).setParameter("korisnickoIme", orderObj.getUsername());
                       
                        List<Korisnik> userForOrderUpdateList = new ArrayList<Korisnik>();
                        userForOrderUpdateList = userForOrderUpdateFound.getResultList();
                        
                        Korisnik userOrder = userForOrderUpdateList.get(0);
                        
                        TypedQuery<Korpa> cartFromUserUpdate = entityManager.createQuery("SELECT k FROM Korpa k WHERE k.idKorisnik = :userId",Korpa.class).setParameter("userId", userOrder);
                        Korpa cartUserUpdate = new Korpa();
                        cartUserUpdate = cartFromUserUpdate.getSingleResult();
                        
                        
                        TypedQuery<Stavka> itemsUpdate = entityManager.createQuery("SELECT s FROM Stavka s WHERE s.idKorpe = :cartlId and s.idN is null",Stavka.class).setParameter("cartlId", cartUserUpdate);
                        List<Stavka> itemsCurrentUpdate = new ArrayList<Stavka>();
                        itemsCurrentUpdate = itemsUpdate.getResultList();   
                
                        for(int i = 0; i<itemsCurrentUpdate.size(); i++){
                            
                            EntityTransaction transaction = entityManager.getTransaction();
                            transaction.begin();
                            itemsCurrentUpdate.get(i).setIdN(orderObj.getIdN());
                            
                            
                            entityManager.persist(itemsCurrentUpdate.get(i));
                            entityManager.flush();
                            entityManager.refresh(itemsCurrentUpdate.get(i));
                            
                          
                            transaction.commit(); 
                        }
                        
                        TextMessage textMsg20 = context.createTextMessage();
                        
                        try {
                            textMsg20.setText("Uspesno promenjen id narudzbine! \n");
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        producer.send(queueReceive_sendResultsToServer_2, textMsg20);
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
