package com.mahesh.database.dao;

import com.mahesh.database.FundReportsDb;
import com.mahesh.database.dto.Filing;

import java.sql.*;

public class FilingDao {
    public FilingDao() {
    }

    public void insertFiling (int fundId, Date filingDate, String filingType,
                              Date reportDate) throws SQLException {
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String insertString = "insert into Filing (fundId, filingDate, " +
                    "filingType, reportDate) values (?, ?, ?, ?)";

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

    public Filing getFiling(int fundId, Date filingDate, String filingType) {
        Filing filing = new Filing();
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String selectString = "select * from Filing where fundId = " +
                    fundId + " and filingDate = '" + filingDate.toString()
                    + "' and filingType = '" + filingType + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectString);

            if (rs.next() == false) {
                return null;
            }
            else {
                do {
                    filing.setFilingId(rs.getInt("filingId"));
                    filing.setFilingDate(rs.getDate("filingDate"));
                    filing.setReportDate(rs.getDate("reportDate"));
                    filing.setFilingType(rs.getString("filingType"));
                } while (rs.next());
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
        return filing;
    }

    public void deleteFiling (int fundId) {
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
