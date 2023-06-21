package main;

import java.util.Scanner;

public class JavaClientMain {
    
    private Scanner scanner = new Scanner(System.in);
    private JavaClientQuery client = new JavaClientQuery();
    
    public void processOption(int option) {
        
     
        String username;
        String password;
        String firstName;
        String lastName;
        String address;
        String cityName;
        
        int cityId;
        int userId;
        
        int moneyAmount;
        
        String categoryName;
        
        String articleName;
        String articleAbout;
        int articlePrice;
        int articleDiscount;
        int articleAmount;
       
        switch(option){
            case 1:
                
                System.out.println("Unesite naziv grada:");
                cityName = scanner.nextLine();
                client.createCity(cityName);
                
                break;
                
            case 2:
                
                System.out.println("Unesite korisnicko ime:");
                username = scanner.nextLine();
                
                System.out.println("Unesite sifru:");
                password = scanner.nextLine();
                
                System.out.println("Unesite ime:");
                firstName = scanner.nextLine();
                
                System.out.println("Unesite prezime:");
                lastName = scanner.nextLine();
                
                System.out.println("Unesite adresu");
                address = scanner.nextLine();
                
                System.out.println("Unesite id grada:");
                cityId = Integer.parseInt(scanner.nextLine());
                
                client.createUser(username, password, firstName, lastName, address, cityId);
                
                break;
                
            case 3:
                System.out.println("Unesite korisnicko ime korisnika:");
                username = scanner.nextLine();
                
                System.out.println("Unesite kolicinu novca:");
                moneyAmount = Integer.parseInt(scanner.nextLine());
                
                client.addMoney(username, moneyAmount);
                
                break;
            case 4:
                System.out.println("Unesite korisnicko ime korisnika:");
                username = scanner.nextLine();
                
                System.out.println("Unesite novu adresu:");
                address = scanner.nextLine();
                
                System.out.println("Unesite novi id grada:");
                cityId = Integer.parseInt(scanner.nextLine());
                   
                client.updateAddressAndCity(username, address, cityId);
                
                break;
            case 5:
                System.out.println("Unesite naziv kategorije:");
                categoryName = scanner.nextLine();
                
                client.createCategory(categoryName);           
                
                break;
            case 6:
                System.out.println("Unesite naziv artikla:");
                articleName = scanner.nextLine();
                
                System.out.println("Unesite opis artikla:");
                articleAbout = scanner.nextLine();
                
                System.out.println("Unesite cenu artikla:");
                articlePrice = Integer.parseInt(scanner.nextLine());
                
                System.out.println("Unesite kategoriju artikla:");
                categoryName = scanner.nextLine();
                
                System.out.println("Unesite korisnicko ime korisnika koji prodaje artikal:");
                username = scanner.nextLine();
                
                client.createArticle(articleName, articleAbout, articlePrice, categoryName, username);
                
                break;
            case 7:
                System.out.println("Unesite naziv artikla:");
                articleName = scanner.nextLine();
                
                System.out.println("Unesite novu cenu artikla:");
                articlePrice = Integer.parseInt(scanner.nextLine());
                
                client.updatePriceOfArticle(articleName, articlePrice);
                
                break;
            case 8:
                System.out.println("Unesite naziv artikla:");
                articleName = scanner.nextLine();
                
                System.out.println("Unesite popust za artikal:");
                articleDiscount = Integer.parseInt(scanner.nextLine());
                
                client.setDiscount(articleName, articleDiscount);
                
                break;
            case 9:
                System.out.println("Unesite korisnicko ime korisnika:");
                username = scanner.nextLine();
                
                System.out.println("Unesite naziv artikal:");
                articleName = scanner.nextLine();
                
                System.out.println("Unesite kolicinu artikla:");
                articleAmount = Integer.parseInt(scanner.nextLine());
                
                client.addArticleInCart(username, articleName, articleAmount);
                
                break;
            case 10:
                System.out.println("Unesite korisnicko ime korisnika:");
                username = scanner.nextLine();
                
                System.out.println("Unesite naziv artikal:");
                articleName = scanner.nextLine();
                
                System.out.println("Unesite kolicinu artikla:");
                articleAmount = Integer.parseInt(scanner.nextLine());
                
                client.deleteArticleFromCart(username, articleName, articleAmount);
                
                break;
            case 11:
                System.out.println("Unesite korisnicko ime korisnika koji zeli da plati:");
                username = scanner.nextLine();
                
                client.makeTransaction(username);
                
                break;
            case 12:
                
                client.getAllCities();
                
                break;
            case 13:
                
                client.getAllUsers();
                
                break;
            case 14:
                
                client.getAllCategories();
                
                break;
            case 15:
                System.out.println("Unesite korisnicko ime korisnika:");
                username = scanner.nextLine();
                
                client.getAllArticlesFromUser(username);
                
                break;
            case 16:
                System.out.println("Unesite korisnicko ime korisnika:");
                username = scanner.nextLine();
                
                client.getCartFromUser(username);
                
                break;
            case 17:
                System.out.println("Unesite korisnicko ime korisnika:");
                username = scanner.nextLine();
                
                client.getAllOrdersFromUser(username);
                
                break;
            case 18:
                
                client.getAllOrders();
                
                break;
            case 19:
                
                client.getAllTransactions();
                
                break;
        }
    }
    
    public void printOptions() {
        System.out.println("Izaberite jednu od sledecih mogucnosti:");
        System.out.println("1. Kreiranje grada");
        System.out.println("2. Kreiranje korisnika");
        System.out.println("3. Dodavanje novca korisniku");
        System.out.println("4. Promena adrese i grada za korisnika");
        System.out.println("5. Kreiranje kategorije");
        System.out.println("6. Kreiranje artikla");
        System.out.println("7. Menjanje cene artikla");
        System.out.println("8. Postavljanje popusta za artikal");
        System.out.println("9. Dodavanje artikala u određenoj količini u korpu");
        System.out.println("10. Brisanje artikla u određenoj količini iz korpe");
        System.out.println("11. Plaćanje, koje obuhvata kreiranje transakcije, kreiranje narudžbine sa njenim stavkama, i brisanje sadržaja iz korpe");
        System.out.println("12. Dohvatanje svih gradova");
        System.out.println("13. Dohvatanje svih korisnika");
        System.out.println("14. Dohvatanje svih kategorija");
        System.out.println("15. Dohvatanje svih artikala koje prodaje korisnik koji je poslao zahtev");
        System.out.println("16. Dohvatanje sadržaja korpe korisnika koji je poslao zahtev");
        System.out.println("17. Dohvatanje svih narudžbina korisnika koji je poslao zahtev");
        System.out.println("18. Dohvatanje svih narudžbina");
        System.out.println("19. Dohvatanje svih transakcija");
        System.out.println("0. Kraj programa");
        System.out.println();
    }

    public int getUserInput() {
        int choice = 0;      
        choice = Integer.parseInt(scanner.nextLine());
        return choice;             
    }
    
    
    public static void main(String[] args) {
        int userOption;
        JavaClientMain client = new JavaClientMain();
        
        while(true){
           client.printOptions();
           userOption = client.getUserInput();
           if(userOption == 0) break;
           
           if(userOption < 0 || userOption >19) {
               System.out.println("Nevalidna opcija!");
           }
           else{
  
               client.processOption(userOption);
           }
        }
    }
    
    
}
