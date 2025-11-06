package com.cricketstats.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBUtil {
    private static final String PROPERTIES_PATH = "config/db.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(PROPERTIES_PATH)) {
            PROPERTIES.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load DB properties from " + PROPERTIES_PATH, e);
        }
    }

    private DBUtil() {}

    public static Connection getConnection() throws SQLException {
        String url = PROPERTIES.getProperty("url");
        String user = PROPERTIES.getProperty("user");
        String password = PROPERTIES.getProperty("password");
        return DriverManager.getConnection(url, user, password);
    }
}


