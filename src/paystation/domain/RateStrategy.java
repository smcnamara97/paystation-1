package paystation.domain;


public interface RateStrategy {
    public double calculateTime(int insertedSoFar);
}
