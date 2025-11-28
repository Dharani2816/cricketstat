package com.cricketstats;

import com.cricketstats.dao.DBConnection;
import java.sql.Connection;

public class DBTest {
    public static void main(String[] args) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.println(" Successfully connected to MySQL!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
