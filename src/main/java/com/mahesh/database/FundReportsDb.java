package com.mahesh.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FundReportsDb {
    private Connection conn;

    FundReportsDb(){
        conn = null;
    }

    Connection getConn() {
        conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FundReports?serverTimezone=US/Pacific&useSSL=false", "mahesh", "goldpen62");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
