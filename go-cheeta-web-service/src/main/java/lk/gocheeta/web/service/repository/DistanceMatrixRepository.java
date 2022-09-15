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
import lk.gocheeta.web.service.repository.exception.DatabaseException;

/**
 *
 * @author asha
 */
public class DistanceMatrixRepository {

    private static DistanceMatrixRepository instance;
    private static final Logger loger = Logger.getLogger(DistanceMatrixRepository.class.getName());

    public static DistanceMatrixRepository getInstance() {
        if (instance == null) {
            instance = new DistanceMatrixRepository();
        }
        return instance;
    }

    public float getDistance(int locId1, int locId2) throws DatabaseException {
        String query = "SELECT distance FROM distance_matrix WHERE (locId1 =? AND locId2 =?) OR (locId2 =? AND locId1 =?)";
        float distance = -1f;
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setInt(1, locId1);
            statement.setInt(2, locId2);
            statement.setInt(3, locId1);
            statement.setInt(4, locId2);

            rs = statement.executeQuery();
            if (rs.next()) {
                distance = rs.getFloat("distance");
            }
            return distance;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }
}
