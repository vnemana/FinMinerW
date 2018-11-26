package com.mahesh.database.dao;

import com.mahesh.database.FundReportsDb;
import com.mahesh.database.dto.Holding;

import java.sql.*;
import java.util.ArrayList;

public class HoldingDao {
    public void insertHolding(int fundId, int filingId, String cusip,
                              String stock, double position, int numShares)
            throws SQLException {
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String insertString = "insert into Holding (fundId, filingId, " +
                    "cusip, stock, position, numshares) " +
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

    public ArrayList<Holding> getHoldings (long filingId) throws SQLException {
        ArrayList<Holding> holdings = new ArrayList<>();
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String selectString = "select * from Holding where filingId = " + filingId;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectString);

            while (rs.next()) {
                Holding holding = new Holding();
                holding.setFundId(rs.getInt("fundId"));
                holding.setFilingId(rs.getInt("filingId"));
                holding.setHoldingId(rs.getInt("holdingId"));
                holding.setCusip(rs.getString("cusip"));
                holding.setStock(rs.getString("stock"));
                holding.setNumShares(rs.getInt("numShares"));
                holding.setPosition(rs.getDouble("position"));

                holdings.add(holding);
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
        return holdings;
    }

}
