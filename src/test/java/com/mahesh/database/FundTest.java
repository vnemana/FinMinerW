package com.mahesh.database;

import org.junit.Test;

import static org.junit.Assert.*;

public class FundTest {

    @Test
    public void insert() {
        Fund Fund = new Fund();
        Fund.insert("TestFund");

        Fund rsFund = Fund.getFund("TestFund");
        assert rsFund != null;
        assert rsFund.fundName.equals("TestFund");
    }

    @Test
    public void delete() {
        Fund Fund = new Fund();
        Fund retFund = Fund.getFund("TestFund");
        assert retFund != null;
        Filing filings = new Filing();
        filings.delete((int) retFund.fundId);
        Fund.delete((int) retFund.fundId);
    }
}