/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author asha
 */
public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/gocheeta?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "icbt";
    private static final String PASSWORD = "icbt";
    
    public static Statement getStatment() throws SQLException{
        return DriverManager.getConnection(URL, USERNAME, PASSWORD).createStatement();
    }
    
    public static PreparedStatement getPreparedStatement(String query) throws SQLException{
        return DriverManager.getConnection(URL, USERNAME, PASSWORD).prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }
    
    public static PreparedStatement getDeletePreparedStatement(String query) throws SQLException{
        return DriverManager.getConnection(URL, USERNAME, PASSWORD).prepareStatement(query);
    }
}
