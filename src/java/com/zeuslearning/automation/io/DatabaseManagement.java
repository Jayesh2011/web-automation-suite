package com.zeuslearning.automation.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManagement {

    public void connect(String urlString, String userName, String password, String sqlQuery) throws SQLException, ClassNotFoundException {

        // Load Microsoft SQL Server JDBC driver.
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        // Prepare connection url. Get connection to DB.
        Connection con = DriverManager.getConnection(urlString, userName, password);

        // Create statement object which would be used in writing DDL and DML
        // SQL statement.
        Statement stmt = con.createStatement();

        // Send SQL SELECT statements to the database via the
        // Statement.executeQuery method which returns the requested information
        // as rows of data in a ResultSet object.

        // define query to read data
        try {
            ResultSet result = stmt.executeQuery(sqlQuery);
            while (result.next()) {
                // Fetch value of "userName" and "password" from "result"
                // object; this will return 2 existing users in the DB.

                String guidAssetId = result.getString("guidAssetId");
                String guidAssetTypeId = result.getString("guidAssetTypeId");
                // print them on the console
                System.out.println("guidAssetId :" + guidAssetId);
                System.out.println("guidAssetTypeId: " + guidAssetTypeId);
            }
            result.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            con.close();
            stmt.close();
        }
    }
}
