package com.mahesh.database;

import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.*;

public class HoldingTest {

    @Test (expected = SQLIntegrityConstraintViolationException.class)
    public void insert_with_invalid_fund_id() throws SQLException {
        Holding holdings = new Holding();
        int fundId = 1;
        int filingId = 1;
        String cusip = "ABCUSIP";
        String stock = "GOOGL";
        double position = 12345.5;
        int numShares = 123;

        holdings.insert(fundId, filingId, cusip, stock, position, numShares);

//        ArrayList<Holding> retHoldings = holdings.getHoldings(filingId);
//        assert(retHoldings.get(0).filingId == holdings.filingId);
    }

    @Test
    public void insert_with_valid_fund_and_filing_id() {
        try {
            Fund fund = new Fund();
            Fund retFund = fund.getFund("TestFund");

            if (retFund == null) {
                fund.insert("TestFund");
                retFund = fund.getFund("TestFund");
            }

            Filing filing = new Filing();

            Calendar calendar = Calendar.getInstance();
            calendar.set (2018, 03, 01);
            Date filingDate = new Date(calendar.getTimeInMillis());
            Date reportDate = new Date(calendar.getTimeInMillis());

            String filingType = "13-F";
            Filing retFiling = filing.getFiling(retFund.fundId, filingDate, filingType);

            if (retFiling == null) {
                filing.insert(retFund.fundId, filingDate, filingType, reportDate);
                retFiling = filing.getFiling(retFund.fundId, filingDate, filingType);
            }

            String cusip = "ABCUSIP";
            String stock = "GOOGL";
            double position = 12345.6;
            int numShares = 123;

            Holding holding = new Holding();
            holding.insert(retFund.fundId, retFiling.filingId, cusip, stock, position, numShares);
            ArrayList<Holding> holdings = new ArrayList<>();
            holdings = holding.getHoldings(retFiling.filingId);
            assert (holdings.get(0).cusip .equals("ABCUSIP"));

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}