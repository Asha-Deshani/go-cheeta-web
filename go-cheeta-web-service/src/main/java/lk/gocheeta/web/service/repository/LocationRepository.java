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
import lk.gocheeta.web.service.dto.Location;
import lk.gocheeta.web.service.repository.exception.DatabaseException;

/**
 *
 * @author asha
 */
public class LocationRepository {

    private static LocationRepository instance;
    private static final Logger loger = Logger.getLogger(LocationRepository.class.getName());

    public static LocationRepository getInstance() {
        if (instance == null) {
            instance = new LocationRepository();
        }
        return instance;
    }

    public Location addLocation(Location location) throws DatabaseException {
        String query = "INSERT INTO location (address, branch_id) VALUES (?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setString(1, location.getAddress());
            statement.setInt(2, location.getBranchId());

            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                location.setId(rs.getInt(1));
            }

            return location;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public Location updateLocation(Location location) throws DatabaseException {
        String query = "UPDATE location SET address=?, branch_id=? WHERE id =?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setString(1, location.getAddress());
            statement.setInt(2, location.getBranchId());
            statement.setInt(3, location.getId());

            statement.executeUpdate();
            return location;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public Location getLocation(int id) throws DatabaseException {
        String query = "SELECT address, branch_id FROM location WHERE id =?";
        Location location = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setInt(1, id);

            rs = statement.executeQuery();
            if (rs.next()) {
                location = new Location();

                location.setId(id);
                location.setAddress(rs.getString("address"));
                location.setBranchId(rs.getInt("branch_id"));
            }
            return location;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public boolean deleteLocation(int id) throws DatabaseException {
        String query = "DELETE FROM location WHERE id =?";

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
    
    public List<Location> getLocationsByBranchId(int branchId) throws DatabaseException {
        String query = "SELECT id, address, branch_id FROM location WHERE branch_id =?";
        List<Location> locationList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setInt(1, branchId);

            rs = statement.executeQuery();
             while (rs.next()) {
                Location location = new Location();

                location.setId(rs.getInt("id"));
                location.setAddress(rs.getString("address"));
                location.setBranchId(rs.getInt("branch_id"));
                
                locationList.add(location);
            }
            return locationList;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }
    
       public List<Location> getLocations() throws DatabaseException {
        String query = "SELECT id, address, branch_id FROM location";
        List<Location> locationList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);

            rs = statement.executeQuery();
             while (rs.next()) {
                Location location = new Location();

                location.setId(rs.getInt("id"));
                location.setAddress(rs.getString("address"));
                location.setBranchId(rs.getInt("branch_id"));
                
                locationList.add(location);
            }
            return locationList;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }
}
