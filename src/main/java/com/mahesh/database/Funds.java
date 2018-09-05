package com.mahesh.database;

import java.sql.*;

public class Funds {
    long fundId;
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

    public Funds getFund (String fundName) {
        Funds funds = new Funds();
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String selectString = "select * from Fund where fundName = '" + fundName + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectString);

            while (rs.next()) {
                funds.fundId = rs.getInt("fundId");
                funds.fundName = rs.getString("fundName");
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funds;
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
}
