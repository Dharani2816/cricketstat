package com.cricketstats.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.spi.DirStateFactory.Result;

public class DBConnection {
    private static final String URL =
  "jdbc:mysql://localhost:3306/cricket_stats";
    private static final String USER = "root";
    private static final String PASSWORD = "Alsaking76#";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(" MySQL JDBC Driver not found.");
        } catch (SQLException e) {
            throw new RuntimeException(" Failed to connect: " + e.getMessage());
        }
    }
}
