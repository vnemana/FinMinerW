package com.mahesh.database.dto;

public class Cusip {
    private String cusip;
    private String stock;
    private String companyName;

    public Cusip(String cusipArg, String stock, String companyName) {
        this.cusip = cusipArg;
        this.stock = stock;
        this.companyName = companyName;
    }

    public String getCusip() { return cusip; }

    public void setCusip(String cusip) { this.cusip = cusip; }

    public String getStock() { return stock; }

    public void setStock(String stock) { this.stock = stock; }

    public String getCompanyName() { return companyName; }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
