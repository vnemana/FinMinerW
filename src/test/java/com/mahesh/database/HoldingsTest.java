package com.mahesh.database;

import org.junit.Test;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.Assert.*;

public class HoldingsTest {

    @Test (expected = SQLIntegrityConstraintViolationException.class)
    public void insert_with_invalid_fund_id() throws SQLException {
        Holdings holdings = new Holdings();
        int fundId = 1;
        int filingId = 1;
        String cusip = "ABCUSIP";
        String stock = "GOOGL";
        double position = 12345.5;
        int numShares = 123;

        holdings.insert(fundId, filingId, cusip, stock, position, numShares);
    }
}