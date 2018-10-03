package com.mahesh.database;

import java.sql.*;

public class Fund {
    int fundId;
    String fundName;

    public void insert (String fundName) {
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

    public Fund getFund (String fundName) {
        Fund Fund = new Fund();
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String selectString = "select * from Fund where fundName = '" + fundName + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectString);

            if (rs.next() == false) {
                return null;
            }
            else {
                do {
                    Fund.fundId = rs.getInt("fundId");
                    Fund.fundName = rs.getString("fundName");
                } while (rs.next());
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return Fund;
    }

    public void delete (String fundName) {
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

    public void delete(int fundId) {
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
