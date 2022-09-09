/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.database.DatabaseManager;
import lk.gocheeta.web.service.dto.Login;
import lk.gocheeta.web.service.repository.exception.DatabaseException;

/**
 *
 * @author asha
 */
public class LoginRepository {

    private static LoginRepository instance;
    private static final Logger loger = Logger.getLogger(LoginRepository.class.getName());

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }

    public Login addLogin(Login login) throws DatabaseException {
        String query = "INSERT INTO login (username, password, role, reference_id) "
                + "VALUES (?, ?, ?, ?)";
        
       Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            
            statement.setString(1, login.getUsername());
            statement.setString(2, login.getPassword());
            statement.setString(3, login.getRole());
            statement.setInt(4, login.getReferenceId());

            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                login.setId(rs.getInt(1));
            }

            return login;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public Login updateLogin(Login login) throws DatabaseException {
        String query = "UPDATE login SET username=?, password=?, role=?, reference_id=? WHERE id =?";

         Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            
            statement.setString(1, login.getUsername());
            statement.setString(2, login.getPassword());
            statement.setString(3, login.getRole());
            statement.setInt(4, login.getReferenceId());
            statement.setInt(5, login.getId());

            statement.executeUpdate();
            return login;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(null, statement, connection);
        }
    }

    public Login authenticate(String username, String password) throws DatabaseException {
        String query = "SELECT id, username, role, reference_id "
                + "FROM login WHERE username =? AND password =?";
        Login login = null;
        
       Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setString(1, username);
            statement.setString(2, password);

             rs = statement.executeQuery();
            if (rs.next()) {
                login = new Login();

                login.setId(rs.getInt("id"));
                login.setUsername(rs.getString("username"));
                login.setRole(rs.getString("role"));
                login.setReferenceId(rs.getInt("reference_id"));
            }
            return login;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }
    
    public boolean changePassword(String username, String password, String newPassword) throws DatabaseException {
        String query = "UPDATE login SET password=? WHERE username =?, password =?";

         Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            
            statement.setString(1, newPassword);
            statement.setString(2, username);
            statement.setString(3, password);

            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(null, statement, connection);
        }
    }
    
    public List<Login> getLogins() throws DatabaseException {
        String query = "SELECT id, username, role, reference_id FROM login";
        List<Login> loginList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            rs = statement.executeQuery();

            while (rs.next()) {
                Login login = new Login();
                login.setId(rs.getInt("id"));
                login.setUsername(rs.getString("username"));
                login.setRole(rs.getString("role"));
                login.setReferenceId(rs.getInt("reference_id"));

                loginList.add(login);
            }
            return loginList;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public boolean deleteLogin(int id) throws DatabaseException {
        String query = "DELETE FROM login WHERE id =?";
        
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
