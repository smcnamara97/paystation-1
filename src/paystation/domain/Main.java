//Main function
package paystation.domain;

import java.util.Scanner;
import paystation.domain.LinearRateStrategy;
import paystation.domain.ProgressiveRateStrategy;
import paystation.domain.Alternation;


public class Main extends PayStationImpl {

    public static void main(String[] args)
            throws IllegalCoinException {
        LinearRateStrategy lrs = new LinearRateStrategy();
        ProgressiveRateStrategy prs = new ProgressiveRateStrategy();
        PayStationImpl ps = new PayStationImpl(lrs); //create an instance for the transaction
        while (true) {

            System.out.println("Main Menu\n 1:Deposit Coins\n 2:Display\n "
                    + "3:Buy Ticket\n 4:Cancel\n 5:Change Rate Strategy\n");
            Scanner menu = new Scanner(System.in);
            int choice = menu.nextInt();

            
            // System.out.println("Your choice is: " + choice);
            int coin; //coin that user inserts
            Scanner coinSlot = new Scanner(System.in);
            switch (choice) {
                case 1: //Deposit Coins
                    System.out.println("Enter Your Coins - type '0' to stop: ");
                    

                    while (true) {
                        coin = coinSlot.nextInt();
                        if (coin == 0) {
                            break;
                        }
                        //System.out.println("COIN: " + coin);
                        ps.addPayment(coin);
                    }
                    break;
                case 2: //Display
                    ps.readDisplay();
                    break;
                case 3: //Buy Ticket
                    ps.buy();
                    break;
                case 4: //Cancel
                    ps.cancel();
                    System.out.println("Purchase Canceled");
                    break;
                case 5://Change Rate Strategy
                    System.out.println("Change Rate Strategy\n");
                    System.out.println("1: Linear Rate \n2: Progressive Rate \n3:Alternative Rate");
                    int rate = menu.nextInt();
                    switch(rate){
                        case 1: //Linear Rate
                            lrs = new LinearRateStrategy();
                            ps = new PayStationImpl(lrs);
                            
                            break; 
                        case 2: //Progressive Rate
                            prs = new ProgressiveRateStrategy();
                            ps = new PayStationImpl(prs);
                            break;
                        case 3://Alternative Rate
                            System.out.println("Is it a weekend? 1 for yes and 2 for no");
                            int yn = menu.nextInt();
        
                            switch(yn){
                                case 1: //weekend
                                    ps = new PayStationImpl(prs);//implement progressive rate strategy
                                    break;
                
                                case 2: //weekday
                                    ps = new PayStationImpl(lrs); //implement linear rate strategy
                                    break;                  
                            }    
                        }
                    
                        break;
            }

        }

    }
}