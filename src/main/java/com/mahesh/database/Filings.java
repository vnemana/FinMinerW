package com.mahesh.database;

import java.sql.*;

public class Filings {
    long filingId;
    long fundId;
    Date filingDate;
    String filingType;
    Date reportDate;

    public void insert (long fundId, Date filingDate, String filingType, Date reportDate) throws SQLException {
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String insertString = "insert into Filing (fundId, filingDate, filingType, reportDate) values (?, ?, ?, ?)";

            PreparedStatement preparedStatement = conn.prepareStatement(insertString);
            preparedStatement.setLong(1, fundId);
            preparedStatement.setDate(2, filingDate);
            preparedStatement.setString(3, filingType);
            preparedStatement.setDate(4,reportDate);

            preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    public Filings getFiling(long fundId, Date filingDate, String filingType) throws SQLException {
        Filings filings = new Filings();
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String selectString = "select * from Filing where fundId = " + fundId + " and filingDate = " + filingDate + " and filingType = '" + filingType + "'";
            Statement stmt = conn.createStatement();

            System.out.println(selectString);
            ResultSet rs = stmt.executeQuery(selectString);

            while (rs.next()) {
                filings.filingId = rs.getInt("filingId");
                filings.filingDate = rs.getDate("filingDate");
                filings.reportDate = rs.getDate("reportDate");
                filings.filingType = rs.getString("filingType");
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
        return filings;
    }

    public void delete (int fundId) {
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();
            String delString = "delete from Filing where fundId=?";

            PreparedStatement preparedStatement = conn.prepareStatement(delString);
            preparedStatement.setInt (1, fundId);

            preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
