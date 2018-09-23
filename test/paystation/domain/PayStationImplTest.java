/**
 * Testcases for the Pay Station system.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
package paystation.domain;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import java.util.Map;
import java.util.HashMap;

public class PayStationImplTest {

    PayStation ps;

    @Before
    public void setup() {
        ps = new PayStationImpl();
    }

    /**
     * Entering 5 cents should make the display report 2 minutes parking time.
     */
    @Test
    public void shouldDisplay2MinFor5Cents()
            throws IllegalCoinException {
        ps.addPayment(5);
        assertEquals("Should display 2 min for 5 cents",
                2, ps.readDisplay());
    }

    /**
     * Entering 25 cents should make the display report 10 minutes parking time.
     */
    @Test
    public void shouldDisplay10MinFor25Cents() throws IllegalCoinException {
        ps.addPayment(25);
        assertEquals("Should display 10 min for 25 cents",
                10, ps.readDisplay());
    }

    /**
     * Verify that illegal coin values are rejected.
     */
    @Test(expected = IllegalCoinException.class)
    public void shouldRejectIllegalCoin() throws IllegalCoinException {
        ps.addPayment(17);
    }

    /**
     * Entering 10 and 25 cents should be valid and return 14 minutes parking
     */
    @Test
    public void shouldDisplay14MinFor10And25Cents()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Should display 14 min for 10+25 cents",
                14, ps.readDisplay());
    }

    /**
     * Buy should return a valid receipt of the proper amount of parking time
     */
    @Test
    public void shouldReturnCorrectReceiptWhenBuy()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Receipt receipt;
        receipt = ps.buy();
        assertNotNull("Receipt reference cannot be null",
                receipt);
        assertEquals("Receipt value must be 16 min.",
                16, receipt.value());
    }

    /**
     * Buy for 100 cents and verify the receipt
     */
    @Test
    public void shouldReturnReceiptWhenBuy100c()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);

        Receipt receipt;
        receipt = ps.buy();
        assertEquals(40, receipt.value());
    }

    /**
     * Verify that the pay station is cleared after a buy scenario
     */
    @Test
    public void shouldClearAfterBuy()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.buy(); // I do not care about the result
        // verify that the display reads 0
        assertEquals("Display should have been cleared",
                0, ps.readDisplay());
        // verify that a following buy scenario behaves properly
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Next add payment should display correct time",
                14, ps.readDisplay());
        Receipt r = ps.buy();
        assertEquals("Next buy should return valid receipt",
                14, r.value());
        assertEquals("Again, display should be cleared",
                0, ps.readDisplay());
    }

    /**
     * Verify that cancel clears the pay station
     */
    @Test
    public void shouldClearAfterCancel()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.cancel();
        assertEquals("Cancel should clear display",
                0, ps.readDisplay());
        ps.addPayment(25);
        assertEquals("Insert after cancel should work",
                10, ps.readDisplay());
    }
    
    //Call to empty should return total amount entered
    @Test
    public void callEmptyReturnAmountEntered()
            throws IllegalCoinException {
        //add payments
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        //then use buy
        ps.buy();
        //do more add payments
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        //buy again
        ps.buy();
        //assert equals
        assertEquals("Return 85 and then empty", 85, ps.empty());
    }
    
    //Canceled entry does not add to the amount returned by empty.
    @Test
    public void cancelNoAddToEmpty()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.buy();
        //After buy do another add payment
        ps.addPayment(25);
        //But then cancel
        ps.cancel();
        //That last 25 should not be added
        assertEquals("Return should be 40 and empty", 40, ps.empty());
    }
    
    //Call to empty resets the total to zero.
    @Test
    public void totalToZero()
            throws IllegalCoinException {
        //add payment of 5
        ps.addPayment(5);
        //use buy
        ps.buy();
        //do an assertEquals to check that there is 5 and call empty
        assertEquals("Check for 5", 5, ps.empty());
        //do another assertEquals to see that it is now 0
        assertEquals("Check total for 0 now after empty", 0, ps.empty());
    }
    
    
    //Call to cancel returns a map containing one coin entered.
    @Test
    public void cancelReturnMapWithOneCoin() 
            throws IllegalCoinException{
        //add payment of 5
        ps.addPayment(5);
        //set map results to cancel (sets to 5)
        Map<Integer, Integer> total = ps.cancel();
        //make another map with and put one nickel in it
        Map<Integer, Integer> test = new HashMap<>();
        test.put(5, 1);
        //assert equal to see if the test and total are the same
        assertEquals("Test and total should be equal", test, total);
    }
    

    //Call to cancel returns a map containing a mixture of coins entered
    @Test
    public void mapContainsCoinMixture()
            throws IllegalCoinException {
        //add payments of nickel, dime, and quarter
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        //run the cancel to total map to the payments added
        Map<Integer, Integer> total = ps.cancel();
        Map<Integer, Integer> test = new HashMap<>();
        test.put (5, 1);
        test.put (10, 1);
        test.put (25, 1);
        //assertEquals to make sure that test and total match
        assertEquals("Should match the same with one nickel, one dime, and one quarter", test, total);
    }
    

    //Call to cancel returns a map that does not contain a key for a coin not entered
    //Ex: Enter two dimes and a quater, returns those three exact ones no nickels
    @Test
    public void mapReturnsCoinsOnlyEntered()
            throws IllegalCoinException {
        //add payments
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);
        //make our total map for the coins entered
        Map<Integer, Integer> total = ps.cancel();
        //make another map that we will use to test our total
        Map<Integer, Integer> test = new HashMap<>();
        test.put (10, 1);
        test.put (25, 2);
        //assertEquals to see if they match
        assertEquals("Return one dime and two quarters and nothing else (no nickels or a bunch of dimes)", test, total);
    }
    

    //Call to cancel clears the map
    @Test
    public void cancelClearsMap() 
            throws IllegalCoinException {
        //add payments
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        //use cancel which should clear total now
        ps.cancel();
        //set map to cancel
        Map<Integer, Integer> total = ps.cancel();
        //make an empty map
        Map<Integer, Integer> test = new HashMap<>();
        //assert equals to see if the match
        assertEquals("Return no coins and match to test", test, total);
    }
    

    //Call to buy clears the map
    @Test
    public void buyClearsMap()
            throws IllegalCoinException {
        //add payments
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        //use buy which should clear total
        ps.buy();
        //make map total
        Map<Integer, Integer> total = ps.cancel();
        //make test map to compare with total
        Map<Integer, Integer> test = new HashMap<>();
        //assertEquals to make sure total and test match to prove buy now clears
        assertEquals("Should return no coins because buy clears the map", test, total);
    }
}
