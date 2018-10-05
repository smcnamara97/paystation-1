package paystation.domain

public class BetatownStrategy implements RateStrategy{
    public double calculateTime(int insertedSoFar){
        PlayStationImpl ps = new PlayStationImpl();
        int tempFirstHour, tempSecondHour, tempThirdPlusHour;//temp holders for how many coins will contribut to that hour
        int timeBought1;//for first hour
        double timeBought2;//for second hour
        int timeBought3;//for 3 or more hourse
        
        if(ps.timeBought <= 60){ //first hour is $1.50 with timebought jawn
            ps.timeBought = insertedSoFar / 5 * 2;
        } 
        else if(insertedSoFar > 150 && insertedSoFar <= 350){ //for the second hour $2.00 + $1.50
            tempFirstHour = 150; //$1.50 for the first hour
            tempSecondHour = insertedSoFar - tempFirstHour;
            timeBought1 = tempFirstHour / 5 * 2;
            timeBought2 = tempSecondHour / 5 *1.5 ;
            
            ps.timeBought = timeBought1+timeBought2;
        }
        else if(insertedSoFar >350){ //if its greater than 3 hours
            //tempFirstHour = 150; //$1.50 for the first hour
            tempSecondHour = 350; //amount it takes to pay for 2 hours
            tempThirdPlusHour = insertedSoFar - tempSecondHour;
            timeBought1 = tempFirstHour / 5 * 2;
            timeBought2 = tempSecondHour / 5 *1.5 ;
            timeBought3 = tempThirdPlusHour / 5; //gives $1 for every 5 cents
            ps.timeBought = timeBought1+timeBought2+timeBought3;
        }
    }
}
