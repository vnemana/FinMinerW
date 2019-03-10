package com.mahesh.database.dao;

import com.mahesh.database.FundReportsDb;
import com.mahesh.database.dto.Cusip;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class CusipDao {
    public void insertStockCusip (Cusip cusipDto) {
        FundReportsDb fundReportsDb = new FundReportsDb();
        Connection conn = fundReportsDb.getConn();

        String insertString = "insert into Cusip (cusip, stock, company_name) values " +
                "(?, ?, ?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = conn.prepareStatement
                    (insertString);
            preparedStatement.setString(1, cusipDto.getCusip());
            preparedStatement.setString(2, cusipDto.getStock());
            preparedStatement.setString(3, cusipDto.getCompanyName());

            preparedStatement.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getStockFromCusip (String cusip) {
        String stock = null;
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String selectString = "select * from Cusip where cusip = " +
                    "'" + cusip + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectString);

            if (!rs.next()) {
                return null;
            }
            else {
                stock = rs.getString("stock");
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock;
    }

    public String getCompanyNameFromCusip (String cusip) {
        String companyName = null;
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String selectString = "select * from Cusip where cusip = " +
                    "'" + cusip + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectString);

            if (!rs.next()) {
                return null;
            }
            else {
                companyName = rs.getString("company_name");
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companyName;
    }

    public Cusip getCusipData (String cusip) {
        Cusip cusipData = new Cusip(cusip, "", "");
        try {
            FundReportsDb db = new FundReportsDb();
            Connection conn = db.getConn();

            String selectString = "select * from Cusip where cusip = " +
                    "'" + cusip + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectString);

            if (!rs.next()) {
                return null;
            }
            else {
                cusipData.setStock(rs.getString("stock"));
                cusipData.setCompanyName(rs.getString("company_name"));
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cusipData;
    }
}
