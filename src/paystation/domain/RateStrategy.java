package paystation.domain;

public interface RateStrategy extends PayStationImpl{
    public double calculateTime(int insertedSoFar);
}
