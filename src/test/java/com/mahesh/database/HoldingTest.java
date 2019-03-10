package com.mahesh.database;

import com.mahesh.database.dao.FilingDao;
import com.mahesh.database.dao.FundDao;
import com.mahesh.database.dao.HoldingDao;
import com.mahesh.database.dto.Filing;
import com.mahesh.database.dto.Fund;
import com.mahesh.database.dto.Holding;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

public class HoldingTest {

    @Test (expected = SQLIntegrityConstraintViolationException.class)
    public void insert_with_invalid_fund_id() throws SQLException {
        HoldingDao hDao = new HoldingDao();
        int fundId = 1;
        int filingId = 1;
        String cusip = "ABCUSIP";
        String stock = "GOOGL";
        double position = 12345.5;
        int numShares = 123;

        hDao.insertHolding(fundId, filingId, cusip, stock, position, numShares);
    }
    @Test
    public void insert_with_valid_fund_and_filing_id() {
        try {
            FundDao fundDao = new FundDao();
            Fund retFund = fundDao.getFund("TestFund");

            if (retFund == null) {
                fundDao.insertFund("TestFund");
                retFund = fundDao.getFund("TestFund");
            }

            FilingDao filingDao = new FilingDao();

            Calendar calendar = Calendar.getInstance();
            calendar.set(2018, 03, 01);
            LocalDate filingDate = LocalDate.of(2018,03,01);
            LocalDate reportDate = LocalDate.of(2018,03,01);

            String filingType = "13-F";
            Filing retFiling = filingDao.getFiling(retFund.getFundId(),
                    filingDate, filingType);

            if (retFiling == null) {
                filingDao.insertFiling(retFund.getFundId(), filingDate,
                        filingType, reportDate);
                retFiling = filingDao.getFiling(retFund.getFundId(),
                        filingDate, filingType);
            }

            String cusip = "ABCUSIP";
            String stock = "GOOGL";
            double position = 12345.6;
            int numShares = 123;

            HoldingDao holdingDao = new HoldingDao();
            holdingDao.insertHolding(retFund.getFundId(), retFiling
                            .getFilingId(), cusip, stock, position, numShares);

            ArrayList<Holding> holdings = new ArrayList<>();
            holdings = holdingDao.getHoldings(retFiling.getFilingId());
            assert (holdings.get(0).getCusip().equals("ABCUSIP"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test (expected = SQLIntegrityConstraintViolationException.class)
    public void insert_two_holdings_same_cusip() throws SQLException {
        try {
            FundDao fundDao = new FundDao();
            Fund retFund = fundDao.getFund("TestFund");

            if (retFund == null) {
                fundDao.insertFund("TestFund");
                retFund = fundDao.getFund("TestFund");
            }

            FilingDao filingDao = new FilingDao();

            Calendar calendar = Calendar.getInstance();
            calendar.set (2018, 03, 01);
            LocalDate filingDate = LocalDate.of(2018,03,01);
            LocalDate reportDate = LocalDate.of(2018,03,01);

            String filingType = "13-F";
            Filing retFiling = filingDao.getFiling(retFund.getFundId(), filingDate,
                    filingType);

            if (retFiling == null) {
                filingDao.insertFiling(retFund.getFundId(), filingDate, filingType,
                        reportDate);
                retFiling = filingDao.getFiling(retFund.getFundId(), filingDate,
                        filingType);
            }

            String cusip = "ABCUSIP";
            String stock = "GOOGL";
            double position = 12345.6;
            int numShares = 123;

            HoldingDao holdingDao = new HoldingDao();
            holdingDao.insertHolding(retFund.getFundId(),  retFiling
                            .getFilingId(), cusip, stock, position, numShares);

            holdingDao.insertHolding(retFund.getFundId(),  retFiling
                            .getFilingId(), cusip, stock, position, numShares);
            ArrayList<Holding> holdings = new ArrayList<>();
            holdings = holdingDao.getHoldings(retFiling.getFilingId());
            assert (holdings.get(0).getCusip().equals("ABCUSIP"));

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}