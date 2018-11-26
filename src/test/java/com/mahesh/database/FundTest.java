package com.mahesh.database;

import com.mahesh.database.dao.FilingDao;
import com.mahesh.database.dao.FundDao;
import com.mahesh.database.dto.Filing;
import com.mahesh.database.dto.Fund;
import org.junit.Test;

public class FundTest {

    @Test
    public void insert() {
        FundDao fDao = new FundDao();
        fDao.insertFund("TestFund");

        Fund rsFund = fDao.getFund("TestFund");
        assert rsFund != null;
        assert rsFund.getFundName().equals("TestFund");
    }

    @Test
    public void delete() {
        FundDao fDao= new FundDao();
        Fund retFund = fDao.getFund("TestFund");
        assert retFund != null;
        FilingDao filingDao = new FilingDao();
        filingDao.deleteFiling(retFund.getFundId());
        fDao.deleteFund(retFund.getFundId());
    }
}