package com.example.appcrud.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:jtds:sqlserver://server-android.database.windows.net:1433;databaseName=db_android;ssl=require";

    private static final String USER = "sqlserver";
    private static final String PASSWORD = "@sql123456";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
