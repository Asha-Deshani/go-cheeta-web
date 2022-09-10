/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.database.DatabaseManager;
import lk.gocheeta.web.service.dto.Admin;
import lk.gocheeta.web.service.repository.exception.DatabaseException;

/**
 *
 * @author asha
 */
public class AdminRepository {

    private static AdminRepository instance;
    private static final Logger loger = Logger.getLogger(AdminRepository.class.getName());

    public static AdminRepository getInstance() {
        if (instance == null) {
            instance = new AdminRepository();
        }
        return instance;
    }

    public Admin addAdmin(Admin admin) throws DatabaseException {
        String query = "INSERT INTO admin (name, telephone, email, designation) VALUES (?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getTelephone());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getDesignation());

            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                admin.setId(rs.getInt(1));
            }

            return admin;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public Admin updateAdmin(Admin admin) throws DatabaseException {
        String query = "UPDATE admin SET name=?, telephone=?, email=?, designation=? WHERE id =?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setString(1, admin.getName());
            statement.setString(2, admin.getTelephone());
            statement.setString(3, admin.getEmail());
            statement.setString(4, admin.getDesignation());
            statement.setInt(5, admin.getId());

            statement.executeUpdate();
            return admin;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(null, statement, connection);
        }
    }

    public Admin getAdmin(int id) throws DatabaseException {
        String query = "SELECT name, telephone, email, designation FROM admin WHERE id =?";

        Admin admin = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setInt(1, id);

            rs = statement.executeQuery();
            if (rs.next()) {
                admin = new Admin();

                admin.setId(id);
                admin.setName(rs.getString("name"));
                admin.setTelephone(rs.getString("telephone"));
                admin.setEmail(rs.getString("email"));
                admin.setDesignation(rs.getString("designation"));
            }
            return admin;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public boolean deleteAdmin(int id) throws DatabaseException {
        String query = "DELETE FROM admin WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setInt(1, id);
            
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(null, statement, connection);
        }
    }
}
