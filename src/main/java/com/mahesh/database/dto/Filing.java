package com.mahesh.database.dto;

import java.sql.*;

public class Filing {
    int filingId;
    int fundId;
    Date filingDate;
    String filingType;
    Date reportDate;

    public Filing() {
    }

    public Filing(int fundId, Date filingDate, String filingType, Date reportDate) {
        this.fundId = fundId;
        this.filingDate = filingDate;
        this.filingType = filingType;
        this.reportDate = reportDate;
    }

    public Filing(int filingId, int fundId, Date filingDate, String filingType, Date reportDate) {
        this.filingId = filingId;
        this.fundId = fundId;
        this.filingDate = filingDate;
        this.filingType = filingType;
        this.reportDate = reportDate;
    }

    public int getFilingId() {
        return filingId;
    }

    public void setFilingId(int filingId) {
        this.filingId = filingId;
    }

    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public Date getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(Date filingDate) {
        this.filingDate = filingDate;
    }

    public String getFilingType() {
        return filingType;
    }

    public void setFilingType(String filingType) {
        this.filingType = filingType;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }


}
