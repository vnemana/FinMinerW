package com.mahesh.database;

import com.mahesh.database.dao.FilingDao;
import com.mahesh.database.dao.FundDao;
import com.mahesh.database.dto.Filing;
import com.mahesh.database.dto.Fund;
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

        FilingDao fDao = new FilingDao();
        fDao.insertFiling(1, filingDate, filingType, reportDate);
    }

    @Test
    public void insert_with_valid_fund_id() {
        try {
            FundDao fundDao = new FundDao();
            fundDao.insertFund("TestFund");
            Fund retFund = fundDao.getFund("TestFund");

            FilingDao filingDao = new FilingDao();
            Date filingDate = new Date(20180301);
            Date reportDate = new Date(20180301);
            String filingType = "13-F";

            filingDao.insertFiling(retFund.getFundId(), filingDate, filingType,
                    reportDate);
            Filing retFiling = filingDao.getFiling(retFund.getFundId(), filingDate,
                    filingType);

            assert(retFiling.getFundId() == retFund.getFundId());
            assert(retFiling.getReportDate() == reportDate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}