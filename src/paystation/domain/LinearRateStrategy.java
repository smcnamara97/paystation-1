package paystation.domain;

public class LinearRateStrategy implements RateStrategy{
    public double calculateTime(int insertedSoFar){
        PlayStationImpl ps = new PlayStationImpl();
        ps.timeBought = insertedSoFar / 5 * 2; //for Alphatown
    }
}
