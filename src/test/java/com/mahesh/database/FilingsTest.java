package com.mahesh.database;

import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


public class FilingsTest {

    @Test (expected = SQLIntegrityConstraintViolationException.class)
    public void insert_with_invalid_fund_id() throws SQLException {
        Filings filings = new Filings();
        Date filingDate = new Date(20180301);
        Date reportDate = new Date(20180301);
        String filingType = "13-F";

        filings.insert(1, filingDate, filingType, reportDate);
    }

    @Test
    public void insert_with_valid_fund_id() {
        try {
            Funds funds  = new Funds();
            funds.insert("TestFund");
            Funds retFund = funds.getFund("TestFund");

            Filings filings = new Filings();
            Date filingDate = new Date(20180301);
            Date reportDate = new Date(20180301);
            String filingType = "13-F";

            filings.insert(retFund.fundId, filingDate, filingType, reportDate);
            Filings retFiling = filings.getFiling(retFund.fundId, filingDate, filingType);

            assert(retFiling.fundId == retFund.fundId);
            assert(retFiling.reportDate == reportDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}