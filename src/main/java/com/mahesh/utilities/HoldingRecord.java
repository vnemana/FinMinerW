package com.mahesh.utilities;

//TODO: Ideally HoldingRecord and Holding should be the same thing.
public class HoldingRecord implements Comparable{
    private final String issuerName;
    private final String cusip;

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    private int numberOfShares;
    private long position;


    public String getIssuerName() {
        return issuerName;
    }

    public String getCusip() {
        return cusip;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public long getPosition() { return position; }

    HoldingRecord(String issuerName, String cusip, int numberOfShares, long
            position) {
        this.issuerName = issuerName;
        this.cusip = cusip;
        this.numberOfShares = numberOfShares;
        this.position = position;
    }

    @Override
    public int compareTo(@SuppressWarnings("NullableProblems") Object o) {
        if (this.cusip.equals(((HoldingRecord)o).cusip))
            return 0;
        else return 1;
    }

    @Override
    public String toString() {
        return this.getCusip() + ": " + this.getIssuerName() + ", " + this
                .getNumberOfShares() + ", " + this.getPosition() + "\n";
    }
}
