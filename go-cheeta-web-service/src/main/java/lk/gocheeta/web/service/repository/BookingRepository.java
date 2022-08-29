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
import lk.gocheeta.web.service.dto.Booking;
import lk.gocheeta.web.service.repository.exception.DatabaseException;

/**
 *
 * @author asha
 */
public class BookingRepository {

    private static BookingRepository instance;
    private static final Logger loger = Logger.getLogger(BookingRepository.class.getName());

    public static BookingRepository getInstance() {
        if (instance == null) {
            instance = new BookingRepository();
        }
        return instance;
    }

    public Booking addBooking(Booking booking) throws DatabaseException {
        String query = "INSERT INTO booking (fare, status, customer_feedback, "
                + "driver_feedback, distance, duration_minute, vehicle_id, customer_id, branch_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setBigDecimal(1, booking.getFare());
            statement.setString(2, booking.getStatus());
            statement.setString(3, booking.getCustomerFeedback());
            statement.setString(4, booking.getDriverFeedback());
            statement.setFloat(5, booking.getDistance());
            statement.setInt(6, booking.getDurationMinute());
            statement.setInt(7, booking.getVehicleId());
            statement.setInt(8, booking.getCustomerId());
            statement.setInt(9, booking.getBranchId());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                booking.setId(rs.getInt(1));
            }

            return booking;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public Booking updateBooking(Booking booking) throws DatabaseException {
        String query = "UPDATE booking SET fare=?, status=?, customer_feedback=?,"
                + "driver_feedback=?, distance=?, duration_minute=?, vehicle_id=?, "
                + "customer_id=?, branch_id=?  WHERE id =?";

        try {
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setBigDecimal(1, booking.getFare());
            statement.setString(2, booking.getStatus());
            statement.setString(3, booking.getCustomerFeedback());
            statement.setString(4, booking.getDriverFeedback());
            statement.setFloat(5, booking.getDistance());
            statement.setInt(6, booking.getDurationMinute());
            statement.setInt(7, booking.getVehicleId());
            statement.setInt(8, booking.getCustomerId());
            statement.setInt(9, booking.getBranchId());
            statement.setInt(10, booking.getId());

            statement.executeUpdate();
            return booking;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public Booking getBooking(int id) throws DatabaseException {
        String query = "SELECT fare, status, customer_feedback, driver_feedback, distance,"
                + " duration_minute, vehicle_id, customer_id, branch_id FROM booking WHERE id =?";
        Booking booking = null;
        
        try {
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                booking = new Booking();

                booking.setId(id);
                booking.setFare(rs.getBigDecimal("fare"));
                booking.setStatus(rs.getString("status"));
                booking.setCustomerFeedback(rs.getString("customer_feedback"));
                booking.setDriverFeedback(rs.getString("driver_feedback"));
                booking.setDistance(rs.getFloat("distance"));
                booking.setDurationMinute(rs.getInt("duration_minute"));
                booking.setVehicleId(rs.getInt("vehicle_id"));
                booking.setCustomerId(rs.getInt("customer_id"));
                booking.setBranchId(rs.getInt("branch_id"));
            }
            return booking;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public boolean deleteBooking(int id) throws DatabaseException {
        String query = "DELETE FROM booking WHERE id =?";
        
        try {
            Statement statement = DatabaseManager.getStatment();
            return statement.executeUpdate(query) > 1;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }
}
