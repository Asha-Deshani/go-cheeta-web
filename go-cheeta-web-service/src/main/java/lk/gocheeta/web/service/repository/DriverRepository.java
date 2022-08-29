/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.database.DatabaseManager;
import lk.gocheeta.web.service.dto.Driver;
import lk.gocheeta.web.service.repository.exception.DatabaseException;

/**
 *
 * @author asha
 */
public class DriverRepository {

    private static DriverRepository instance;
    private static final Logger loger = Logger.getLogger(DriverRepository.class.getName());

    public static DriverRepository getInstance() {
        if (instance == null) {
            instance = new DriverRepository();
        }
        return instance;
    }

    public Driver addDriver(Driver driver) throws DatabaseException {
        String query = "INSERT INTO driver (name, telephone, email, branch_id) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getTelephone());
            statement.setString(3, driver.getEmail());
            statement.setInt(4, driver.getBranchId());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                driver.setId(rs.getInt(1));
            }

            return driver;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public Driver updateDriver(Driver driver) throws DatabaseException {
        String query = "UPDATE driver SET name=?, telephone=?, email=?, branch_id=? WHERE id =?";

        try {
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getTelephone());
            statement.setString(3, driver.getEmail());
            statement.setInt(4, driver.getBranchId());
            statement.setInt(5, driver.getId());

            statement.executeUpdate();
            return driver;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public Driver getDriver(int id) throws DatabaseException {
        String query = "SELECT name, city, branch_id FROM driver WHERE id =?";
        Driver driver = null;

        try {
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                driver = new Driver();

                driver.setId(id);
                driver.setName(rs.getString("name"));
                driver.setTelephone(rs.getString("telephone"));
                driver.setEmail(rs.getString("email"));
                driver.setBranchId(rs.getInt("branch_id"));
            }
            return driver;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public boolean deleteDriver(int id) throws DatabaseException {
        String query = "DELETE FROM driver WHERE id =?";

        try {
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setInt(1, id);
            
            return statement.executeUpdate(query) > 1;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }
}
