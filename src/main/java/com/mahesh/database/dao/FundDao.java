package com.mahesh.database.dao;

import com.mahesh.database.FundReportsDb;
import com.mahesh.database.dto.Fund;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class FundDao {
    public void insertFund (String fundName) {
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String insertString = "insert into Fund (fundName) values (?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertString);
            preparedStatement.setString(1, fundName);

            preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Fund getFund(final String fundName) {
        Fund fund = new Fund();
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String selectString = "select * from Fund where fundName = '" + fundName + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectString);

            if (!rs.next()) {
                return null;
            }
            else {
                do {
                    fund.setFundId(rs.getInt("fundId"));
                    fund.setFundName(rs.getString("fundName"));
                } while (rs.next());
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return fund;
    }

    public void updateFund(final int fundId, final String fundName) {
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();
            String delString = "update Fund set fundName=? where fundId=?";

            PreparedStatement preparedStatement = conn.prepareStatement(delString);
            preparedStatement.setString (1, fundName);
            preparedStatement.setLong(2, fundId);

            preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFund (final String fundName) {
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();
            String delString = "delete from Fund where fundName=?";

            PreparedStatement preparedStatement = conn.prepareStatement(delString);
            preparedStatement.setString (1, fundName);

            preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFund (final int fundId) {
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();
            String delString = "delete from Fund where fundId=?";

            PreparedStatement preparedStatement = conn.prepareStatement(delString);
            preparedStatement.setInt(1, fundId);

            preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
