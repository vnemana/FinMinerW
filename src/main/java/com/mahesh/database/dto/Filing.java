package com.mahesh.database.dto;

import java.time.LocalDate;

public class Filing {
    private int filingId;
    private int fundId;
    private LocalDate filingDate;
    private String filingType;
    private LocalDate reportDate;

    public Filing() {
    }

    public Filing(int fundId, LocalDate filingDate, String filingType,
                  LocalDate reportDate) {
        this.fundId = fundId;
        this.filingDate = filingDate;
        this.filingType = filingType;
        this.reportDate = reportDate;
    }

    public Filing(int filingId, int fundId, LocalDate filingDate, String
            filingType, LocalDate reportDate) {
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

    public LocalDate getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(LocalDate filingDate) {
        this.filingDate = filingDate;
    }

    public String getFilingType() {
        return filingType;
    }

    public void setFilingType(String filingType) {
        this.filingType = filingType;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }
}
