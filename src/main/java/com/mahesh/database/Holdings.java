package com.mahesh.database;

import com.mahesh.utilities.HoldingRecord;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Holdings {
    int fundId;
    int filingId;
    int holdingId;
    String cusip;
    String stock;
    double position;
    int numShares;

    public void insert(int fundId, int filingId, String cusip, String stock, double position, int numShares) throws SQLException {
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String insertString = "insert into Holding (fundId, filingId, cusip, stock, position, numshares) " +
                    "values (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(insertString);
            preparedStatement.setInt(1, fundId);
            preparedStatement.setInt(2, filingId);
            preparedStatement.setString(3, cusip);
            preparedStatement.setString(4, stock);
            preparedStatement.setDouble(5, position);
            preparedStatement.setInt(6, numShares);

            preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    //If Fund already exists, then retrieve fund id from fund table
    //  Else Insert into Fund table
    //  Get FundId
    //If Filing record already exists, get FilingID from Filing table.
    //  Else insert into Filing table.
    //  Get FilingId
    //For each holding record
    //Check if record exists for filing id, fund id and cusip.
    //If Yes, get Holding Id. Update it.
    //If No, insert Holding record.

    public void StoreFilingData(ArrayList<HoldingRecord> holdingRecords) {
        FundReportsDb fundReportsDb = new FundReportsDb();
        fundReportsDb.getConn();
    }
}
