/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private final Logger loger = Logger.getLogger(VehicleTypeRepository.class.getName());

    public VehicleType addVehicleType(VehicleType vehicleType) throws DatabaseException {
        try {
            String query = "INSERT INTO vehicle_type (name, rate) VALUES (?, ?)";

            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setString(1, vehicleType.getName());
            statement.setFloat(2, vehicleType.getRate());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                vehicleType.setId(rs.getInt(1));
            }

            return vehicleType;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public VehicleType updateVehicleType(VehicleType vehicleType) throws DatabaseException {
        try {
            String query = "UPDATE vehicle_type SET name=?, rate=? WHERE id =?";

            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setString(1, vehicleType.getName());
            statement.setFloat(2, vehicleType.getRate());

            statement.executeUpdate();
            return vehicleType;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public VehicleType getVehicleType(int id) throws DatabaseException {
        try {
            String query = "SELECT name, rate FROM vehicle_type WHERE id =?";

            VehicleType vehicleType = null;
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                vehicleType = new VehicleType();

                vehicleType.setId(id);
                vehicleType.setName(rs.getString("name"));
                vehicleType.setRate(rs.getFloat("rate"));
            }
            return VehicleType;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }
}