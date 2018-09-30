//Main function
package paystation.domain;

import java.util.Scanner;


public class Main extends PayStationImpl {
    
    
    public static void main(String[] args) 
                    throws IllegalCoinException{
        System.out.println("Main Menu\n 1:Deposit Coins\n 2:Dispaly\n "
                + "3:Buy Ticket\n 4:Cancel\n 5:Change Rate Strategy\n");
        Scanner menu = new Scanner(System.in);
        int choice = menu.nextInt();
        
       PayStationImpl ps = new PayStationImpl(); //create an instance for the transaction
       // System.out.println("Your choice is: " + choice);
        int coin; //coin that user inserts
        Scanner coinSlot = new Scanner(System.in);
        switch(choice){
            case 1: //Deposit Coins
                System.out.println("Enter Your Coins - type '0' to stop: ");
                
                while(coinSlot.nextInt() != 0){
                    if(coinSlot.nextInt() == 0){
                        break;
                    }
                    coin = coinSlot.nextInt();
                    ps.addPayment(coin);
                }
                break;
            case 2: //Display
                System.out.println("Display: " + ps.readDisplay());
                break;
            case 3: //Buy Ticket
                break;
            case 4: //Cancel
                break;
            case 5://Change Rate Strategy
                break;
        }
        
        
        
        
    }
}
