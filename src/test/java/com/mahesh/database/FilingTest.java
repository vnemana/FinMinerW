package com.mahesh.database;

import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


public class FilingTest {

    @Test (expected = SQLIntegrityConstraintViolationException.class)
    public void insert_with_invalid_fund_id() throws SQLException {
        Filing filing = new Filing();
        Date filingDate = new Date(20180301);
        Date reportDate = new Date(20180301);
        String filingType = "13-F";

        filing.insert(1, filingDate, filingType, reportDate);
    }

    @Test
    public void insert_with_valid_fund_id() {
        try {
            Fund fund = new Fund();
            fund.insert("TestFund");
            Fund retFund = fund.getFund("TestFund");

            Filing filing = new Filing();
            Date filingDate = new Date(20180301);
            Date reportDate = new Date(20180301);
            String filingType = "13-F";

            filing.insert(retFund.fundId, filingDate, filingType, reportDate);
            Filing retFiling = filing.getFiling(retFund.fundId, filingDate, filingType);

            assert(retFiling.fundId == retFund.fundId);
            assert(retFiling.reportDate == reportDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}