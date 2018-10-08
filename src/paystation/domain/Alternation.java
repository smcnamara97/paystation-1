package paystation.domain;
import java.util.Scanner;
import paystation.domain.LinearRateStrategy;
import paystation.domain.ProgressiveRateStrategy;


public class Alternation implements RateStrategy{
    PayStationImpl ps;
    Alternation(){ //constructor
        Scanner menu = new Scanner(System.in);
         LinearRateStrategy lrs = new LinearRateStrategy();
         ProgressiveRateStrategy prs = new ProgressiveRateStrategy();
         
        System.out.println("Is it a weekend? 1 for yes and 2 for no");
        int yn = menu.nextInt();
        
        switch(yn){
            case 1: //weekend
                ps = new PayStationImpl(lrs); //implement linear rate strategy
                break;
                
            case 2: //weekday
                ps = new PayStationImpl(prs);//implement progressive rate strategy
                break;                  
        }      
    }
    
    /*We gotta figure this one out*/
    public double calculateTime(int insertedSoFar){
        
        return ps.timeBought;
    }

    
    
    
    
    
}
