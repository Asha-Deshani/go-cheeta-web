/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.repository;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.database.DatabaseManager;
import lk.gocheeta.web.service.dto.VehicleType;
import lk.gocheeta.web.service.repository.exception.DatabaseException;

/**
 *
 * @author asha
 */
public class VehicleTypeRepository {

    private static VehicleTypeRepository instance;
    private static final Logger loger = Logger.getLogger(VehicleTypeRepository.class.getName());

    public static VehicleTypeRepository getInstance() {
        if (instance == null) {
            instance = new VehicleTypeRepository();
        }
        return instance;
    }

    public VehicleType addVehicleType(VehicleType vehicleType) throws DatabaseException {
        String query = "INSERT INTO vehicle_type (name, rate) VALUES (?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setString(1, vehicleType.getName());
            statement.setFloat(2, vehicleType.getRate());

            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                vehicleType.setId(rs.getInt(1));
            }

            return vehicleType;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public VehicleType updateVehicleType(VehicleType vehicleType) throws DatabaseException {
        String query = "UPDATE vehicle_type SET name=?, rate=? WHERE id =?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setString(1, vehicleType.getName());
            statement.setFloat(2, vehicleType.getRate());
            statement.setInt(3, vehicleType.getId());

            statement.executeUpdate();
            return vehicleType;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public VehicleType getVehicleType(int id) throws DatabaseException {
        String query = "SELECT name, rate FROM vehicle_type WHERE id =?";
        VehicleType vehicleType = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setInt(1, id);

            rs = statement.executeQuery();
            if (rs.next()) {
                vehicleType = new VehicleType();

                vehicleType.setId(id);
                vehicleType.setName(rs.getString("name"));
                vehicleType.setRate(rs.getFloat("rate"));
            }
            return vehicleType;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public boolean deleteVehicleType(int id) throws DatabaseException {
        String query = "DELETE FROM vehicle_type WHERE id =?";

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
    
    public List<VehicleType> getVehicleTypes() throws DatabaseException {
        String query = "SELECT id, name, rate FROM vehicle_type";
        List<VehicleType> vehicleTypeList = new ArrayList();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);

            rs = statement.executeQuery();
            while (rs.next()) {
                VehicleType vehicleType = new VehicleType();

                vehicleType.setId(rs.getInt("id"));
                vehicleType.setName(rs.getString("name"));
                vehicleType.setRate(rs.getFloat("rate"));
                
                vehicleTypeList.add(vehicleType);
            }
            return vehicleTypeList;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }
}
