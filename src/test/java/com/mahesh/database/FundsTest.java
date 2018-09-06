package com.mahesh.database;

import org.junit.Test;

import static org.junit.Assert.*;

public class FundsTest {

    @Test
    public void insert() {
        Funds funds = new Funds();
        funds.insert("TestFund");

        Funds rsFunds = funds.getFund("TestFund");
        assert rsFunds.fundName.equals("TestFund");
    }

    @Test
    public void delete() {
        Funds funds = new Funds();
        Funds retFunds = funds.getFund("TestFund");
        Filings filings = new Filings();
        filings.delete((int) retFunds.fundId);
        funds.delete((int) retFunds.fundId);
    }
}