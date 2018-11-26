package com.mahesh.database.dto;

public class Fund {
    private int fundId;
    private String fundName;

    public Fund(){

    }

    public Fund(String fundName){
        this.fundName = fundName;
    }

    public Fund (int fundId, String fundName) {
        this.fundId = fundId;
        this.fundName = fundName;
    }

    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }
}
