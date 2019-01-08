package com.mahesh.database.dto;

import com.mahesh.database.FundReportsDb;
import com.mahesh.database.dao.FilingDao;
import com.mahesh.database.dao.FundDao;
import com.mahesh.database.dao.HoldingDao;
import com.mahesh.utilities.HoldingRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

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
        FundDao fundDao = new FundDao();
        Fund dbFund = fundDao.getFund(fund.getFundName());
        if (dbFund == null) {
            //  Else Insert into Fund table
            //  Get FundId
            fundDao.insertFund(fund.getFundName());
            dbFund = fundDao.getFund(fund.getFundName());
        }
        //If Filing record already exists, get FilingID from Filing table.
        FilingDao filingDao = new FilingDao();
        Filing dbFiling = filingDao.getFiling(dbFund.getFundId(), filing
                .getFilingDate(), filing.getFilingType());
        if (dbFiling == null) {
            //  Else insert into Filing table.
            //  Get FilingId
            try {
                filingDao.insertFiling(dbFund.getFundId(), filing.getFilingDate(),
                        filing.getFilingType(), filing.getReportDate());
                dbFiling = filingDao.getFiling(dbFund.getFundId(), filing
                        .getFilingDate(), filing.getFilingType());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //For each holding record
        //Check if record exists for filing id, fund id and cusip.
        //If Yes, get Holding Id. Update it.
        HoldingDao holdingDao = new HoldingDao();
        for (int ii=0; ii<holdingRecords.size(); ii++) {
            HoldingRecord hRecord = holdingRecords.get(ii);
            try {
                holdingDao.deleteHoldings(dbFiling.getFilingId());

                //If No, insert Holding record.
                holdingDao.insertHolding(dbFund.getFundId(),dbFiling.getFilingId
                        (), hRecord.getCusip(), hRecord.getIssuerName(), hRecord.getPosition(),
                        hRecord.getNumberOfShares());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
