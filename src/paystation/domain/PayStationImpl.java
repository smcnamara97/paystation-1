package paystation.domain;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    private int timeBought; //**CHANGED FROM INT
    //Keeps track of total
    private double totalPaid; //temp int for timeBought
    //Map to keep track of coins used
    Map<Integer, Integer> coinMap;
    //Gives value of nickel, dime and quarter
    private int nickel = 5;
    private int dime = 10;
    private int quarter = 25;
    //Keeps track of amount of a certain coin used
    private int nickelAmount = 0;
    private int dimeAmount = 0;
    private int quarterAmount = 0;
    
    
    PayStationImpl() {
        coinMap = new HashMap<>();
    }

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        //Use switch cases and when person pays with coin increase amount of certain coin used
        switch (coinValue) {
            case 5:
                nickelAmount++;
                break;
            case 10: 
                dimeAmount++;
                break;
            case 25: 
                quarterAmount++;
                break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }

        insertedSoFar += coinValue;
        timeBought = insertedSoFar / 5 * 2; //for Alphatown
        
        /*FOR BETATOWN
        //NEED TO MAKE timeBought A DOUBLE
        int tempFirstHour, tempSecondHour, tempThirdPlusHour;//temp holders for how many coins will contribut to that hour
        int timeBought1;//for first hour
        double timeBought2;//for second hour
        int timeBought3;//for 3 or more hourse
        
        if(timeBought <= 60){ //first hour is $1.50 with timebought jawn
            timeBought = insertedSoFar / 5 * 2;
        } 
        else if(insertedSoFar > 150 && insertedSoFar <= 350){ //for the second hour $2.00 + $1.50
            tempFirstHour = 150; //$1.50 for the first hour
            tempSecondHour = insertedSoFar - tempFirstHour;
            timeBought1 = tempFirstHour / 5 * 2;
            timeBought2 = tempSecondHour / 5 *1.5 ;
            
            timeBought = timeBought1+timeBought2;
        }
        else if(insertedSoFar >350){ //if its greater than 3 hours
            //tempFirstHour = 150; //$1.50 for the first hour
            tempSecondHour = 350; //amount it takes to pay for 2 hours
            tempThirdPlusHour = insertedSoFar - tempSecondHour;
            timeBought1 = tempFirstHour / 5 * 2;
            timeBought2 = tempSecondHour / 5 *1.5 ;
            timeBought3 = tempThirdPlusHour / 5; //gives $1 for every 5 cents
            timeBought = timeBought1+timeBought2+timeBought3;
        }
        */
        /*DELTATOWN
            IMPLEMENTS ALPHA ON WEEKDAYS AND BETA ON WEEKENDS
        */
    }

    @Override
    public int readDisplay() { //**CHANGED FROM INT
        System.out.println("Display: " + totalPaid);
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        totalPaid = timeBought;
        System.out.println("Bought: " + totalPaid);
        reset();
        return r;
    }

    @Override
    public Map<Integer, Integer> cancel() {
        //if a nickel put it in coin map with nickel amount
        if (nickelAmount != 0) {
            coinMap.put(nickel, nickelAmount);
        }
        //if a dime put it in coin map with dime amount
        if (dimeAmount != 0) {
            coinMap.put(dime, dimeAmount);
        }
        //if a quarter put it in coin map with quarter amount
        if (quarterAmount != 0) {
            coinMap.put(quarter, quarterAmount);
        }
        //make a temp map
        Map<Integer, Integer> temp = new HashMap<>();
        //and put all of the stuff we got from coin map to temp map
        temp.putAll(coinMap);
        //reset coin map
        reset();
        totalPaid = 0;
        //return the temp
        return temp;
    }
    
    private void reset() {
        //Reset all the values back to zero
        timeBought = insertedSoFar = 0;
        nickelAmount = 0;
        dimeAmount = 0;
        quarterAmount = 0;
        //Make sure to remove the nickels, dimes, and quarters from the map too
        coinMap.remove(nickel);
        coinMap.remove(dime);
        coinMap.remove(quarter);
    }
    
    //Empty sets total earned to zero but we return previous total
    //to save that value
    public int empty() {  //**CHANGED FROM INT
        double temp = totalPaid;
        totalPaid = 0;
        return (int)temp;
        
    }
}
