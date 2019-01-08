package com.mahesh.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//TODO: Add a connection pool
public class FundReportsDb {
    private Connection conn;

    public FundReportsDb(){
        conn = null;
    }

    public Connection getConn() {
        conn = null;
        try {
            conn = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/FundReports?serverTimezone=" +
                            "US/Pacific&useSSL=false", "mahesh", "goldpen62");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
