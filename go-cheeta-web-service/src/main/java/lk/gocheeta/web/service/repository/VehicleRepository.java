/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.repository;

import java.sql.Connection;
import lk.gocheeta.web.service.dto.Vehicle;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.database.DatabaseManager;
import lk.gocheeta.web.service.repository.exception.DatabaseException;

/**
 *
 * @author asha
 */
public class VehicleRepository {

    private static VehicleRepository instance;
    private static final Logger loger = Logger.getLogger(VehicleRepository.class.getName());

    public static VehicleRepository getInstance() {
        if (instance == null) {
            instance = new VehicleRepository();
        }
        return instance;
    }

    public Vehicle addVehicle(Vehicle vehicle) throws DatabaseException {
        String query = "INSERT INTO vehicle (make, model, year, driver_id, branch_id) VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setString(1, vehicle.getMake());
            statement.setString(2, vehicle.getModel());
            statement.setString(3, vehicle.getYear());
            statement.setInt(4, vehicle.getDriverId());
            statement.setInt(5, vehicle.getBranchId());

            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                vehicle.setId(rs.getInt(1));
            }

            return vehicle;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public Vehicle updateVehicle(Vehicle vehicle) throws DatabaseException {
        String query = "UPDATE vehicle SET make=?, model=?, year=?, driver_id=?, branch_id=? WHERE id =?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setString(1, vehicle.getMake());
            statement.setString(2, vehicle.getModel());
            statement.setString(3, vehicle.getYear());
            statement.setInt(4, vehicle.getDriverId());
            statement.setInt(5, vehicle.getBranchId());
            statement.setInt(6, vehicle.getId());

            statement.executeUpdate();
            return vehicle;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(null, statement, connection);
        }
    }

    public Vehicle getVehicle(int id) throws DatabaseException {
        String query = "SELECT make, model, year, driver_id, branch_id FROM vehicle WHERE id =?";
        Vehicle vehicle = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setInt(1, id);

            rs = statement.executeQuery();
            if (rs.next()) {
                vehicle = new Vehicle();

                vehicle.setId(id);
                vehicle.setMake(rs.getString("make"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setYear(rs.getString("year"));
                vehicle.setDriverId(rs.getInt("driver_id"));
                vehicle.setBranchId(rs.getInt("branch_id"));
            }
            return vehicle;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public boolean deleteVehicle(int id) throws DatabaseException {
        String query = "DELETE FROM vehicle WHERE id =?";

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
    
    public Vehicle getVehicleByBranchId(int branchId) throws DatabaseException {
        String query = "SELECT id, make, model, year, driver_id, branch_id FROM vehicle WHERE branch_id =?";
        Vehicle vehicle = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setInt(1, branchId);

            rs = statement.executeQuery();
            if (rs.next()) {
                vehicle = new Vehicle();

                vehicle.setId(rs.getInt("id"));
                vehicle.setMake(rs.getString("make"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setYear(rs.getString("year"));
                vehicle.setDriverId(rs.getInt("driver_id"));
                vehicle.setBranchId(rs.getInt("branch_id"));
            }
            return vehicle;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }
}
