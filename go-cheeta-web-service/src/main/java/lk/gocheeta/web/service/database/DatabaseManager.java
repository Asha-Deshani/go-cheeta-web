/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import lk.gocheeta.web.service.repository.exception.DatabaseException;

/**
 *
 * @author asha
 */
public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/gocheeta?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "icbt";
    private static final String PASSWORD = "icbt";

    public static Statement getStatment() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD).createStatement();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static PreparedStatement getPreparedStatement(Connection connection, String query) throws SQLException {
        return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }

    public static void closeResources(ResultSet rs, PreparedStatement statement, Connection connection) throws DatabaseException {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
