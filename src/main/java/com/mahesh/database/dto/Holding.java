package com.mahesh.database.dto;

import com.mahesh.database.FundReportsDb;
import com.mahesh.utilities.HoldingRecord;

import java.sql.*;
import java.util.ArrayList;

public class Holding {
    int fundId;
    int filingId;
    int holdingId;
    String cusip;
    String stock;
    double position;
    int numShares;

    public Holding(int fundId, int filingId, String cusip, String stock,
                   double position, int numShares) {
        this.fundId = fundId;
        this.filingId = filingId;
        this.cusip = cusip;
        this.stock = stock;
        this.position = position;
        this.numShares = numShares;
    }

    public Holding(int fundId, int filingId, int holdingId, String cusip,
                   String stock, double position, int numShares) {
        this.fundId = fundId;
        this.filingId = filingId;
        this.holdingId = holdingId;
        this.cusip = cusip;
        this.stock = stock;
        this.position = position;
        this.numShares = numShares;
    }

    public Holding() {
    }

    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public int getFilingId() {
        return filingId;
    }

    public void setFilingId(int filingId) {
        this.filingId = filingId;
    }

    public int getHoldingId() {
        return holdingId;
    }

    public void setHoldingId(int holdingId) {
        this.holdingId = holdingId;
    }

    public String getCusip() {
        return cusip;
    }

    public void setCusip(String cusip) {
        this.cusip = cusip;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public int getNumShares() {
        return numShares;
    }

    public void setNumShares(int numShares) {
        this.numShares = numShares;
    }




    public void StoreFilingData(ArrayList<HoldingRecord> holdingRecords, Filing filing, Fund fund) {
        FundReportsDb fundReportsDb = new FundReportsDb();
        fundReportsDb.getConn();

        //If Fund already exists, then retrieve fund id from fund table
        //  Else Insert into Fund table
        //  Get FundId
        //If Filing record already exists, get FilingID from Filing table.
        //  Else insert into Filing table.
        //  Get FilingId
        //For each holding record
        //Check if record exists for filing id, fund id and cusip.
        //If Yes, get Holding Id. Update it.
        //If No, insert Holding record.
    }
}
