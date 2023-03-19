package org.example.domain;

import java.sql.*;

public class DatabaseConnectivity {

    private final Connection connection;

    public DatabaseConnectivity() {
        this.connection = DatabaseConnectivity.getMySqlConnection();
    }

    public static Connection getMySqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            String url = "jdbc:mysql://localhost:3306/servlet?user=servlet&password=servlet-password";
            return DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

}