/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.database.DatabaseManager;
import lk.gocheeta.web.service.dto.Booking;
import lk.gocheeta.web.service.dto.BookingDetail;
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
                + "driver_feedback, distance, vehicle_id, customer_id, branch_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
       Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            
            statement.setBigDecimal(1, booking.getFare());
            statement.setString(2, booking.getStatus());
            statement.setString(3, booking.getCustomerFeedback());
            statement.setString(4, booking.getDriverFeedback());
            statement.setFloat(5, booking.getDistance());
            statement.setInt(6, booking.getVehicleId());
            statement.setInt(7, booking.getCustomerId());
            statement.setInt(8, booking.getBranchId());
            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                booking.setId(rs.getInt(1));
            }

            return booking;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public Booking updateBooking(Booking booking) throws DatabaseException {
        String query = "UPDATE booking SET fare=?, status=?, customer_feedback=?,"
                + "driver_feedback=?, distance=?, starttime=?, endtime=?, vehicle_id=?, "
                + "customer_id=?, branch_id=?  WHERE id =?";

        Connection connection = null;
        PreparedStatement statement = null;

        try { 
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            
            statement.setBigDecimal(1, booking.getFare());
            statement.setString(2, booking.getStatus());
            statement.setString(3, booking.getCustomerFeedback());
            statement.setString(4, booking.getDriverFeedback());
            statement.setFloat(5, booking.getDistance());
            statement.setTimestamp(6, booking.getStarttime() != null ? new Timestamp(booking.getStarttime().getTime()) : null);
            statement.setTimestamp(7, booking.getEndtime()!= null ? new Timestamp(booking.getEndtime().getTime()) : null);
            statement.setInt(8, booking.getVehicleId());
            statement.setInt(9, booking.getCustomerId());
            statement.setInt(10, booking.getBranchId());
            statement.setInt(11, booking.getId());

            statement.executeUpdate();
            return booking;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(null, statement, connection);
        }
    }

    public Booking getBooking(int id) throws DatabaseException {
        String query = "SELECT fare, status, customer_feedback, driver_feedback, distance,"
                + " booktime, starttime, endtime, vehicle_id, customer_id, branch_id FROM booking WHERE id =?";
        Booking booking = null;
        
       Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setInt(1, id);

             rs = statement.executeQuery();
            if (rs.next()) {
                booking = new Booking();

                booking.setId(id);
                booking.setFare(rs.getBigDecimal("fare"));
                booking.setStatus(rs.getString("status"));
                booking.setCustomerFeedback(rs.getString("customer_feedback"));
                booking.setDriverFeedback(rs.getString("driver_feedback"));
                booking.setDistance(rs.getFloat("distance"));
                booking.setBooktime(rs.getTimestamp("booktime"));
                Timestamp startDate = rs.getTimestamp("starttime");
                if(startDate != null) {
                   booking.setStarttime(startDate);
                }
                Timestamp endDate = rs.getTimestamp("endtime");
                if(endDate != null) {
                   booking.setEndtime(endDate);
                }
                booking.setVehicleId(rs.getInt("vehicle_id"));
                booking.setCustomerId(rs.getInt("customer_id"));
                booking.setBranchId(rs.getInt("branch_id"));
            }
            return booking;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }
    
    public List<Booking> getBookings() throws DatabaseException {
        String query = "SELECT id, fare, status, customer_feedback, driver_feedback, distance,"
                + " booktime, starttime, endtime, vehicle_id, customer_id, branch_id FROM booking";
        List<Booking> bookingList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            rs = statement.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setFare(rs.getBigDecimal("fare"));
                booking.setStatus(rs.getString("status"));
                booking.setCustomerFeedback(rs.getString("customer_feedback"));
                booking.setDriverFeedback(rs.getString("driver_feedback"));
                booking.setDistance(rs.getFloat("distance"));
                booking.setBooktime(rs.getTimestamp("booktime"));
                Timestamp startDate = rs.getTimestamp("starttime");
                if(startDate != null) {
                   booking.setStarttime(startDate);
                }
                Timestamp endDate = rs.getTimestamp("endtime");
                if(endDate != null) {
                   booking.setEndtime(endDate);
                }
                booking.setVehicleId(rs.getInt("vehicle_id"));
                booking.setCustomerId(rs.getInt("customer_id"));
                booking.setBranchId(rs.getInt("branch_id"));

                bookingList.add(booking);
            }
            return bookingList;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }

    public boolean deleteBooking(int id) throws DatabaseException {
        String query = "DELETE FROM booking WHERE id =?";
        
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
    
    public List<Booking> getBookingsByCustomerId(int customerId) throws DatabaseException {
        String query = "SELECT id, fare, status, customer_feedback, driver_feedback, distance,"
                + "  booktime, starttime, endtime, vehicle_id, customer_id, branch_id FROM booking where customer_id =?";
        List<Booking> bookingList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setInt(1, customerId);
            rs = statement.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setFare(rs.getBigDecimal("fare"));
                booking.setStatus(rs.getString("status"));
                booking.setCustomerFeedback(rs.getString("customer_feedback"));
                booking.setDriverFeedback(rs.getString("driver_feedback"));
                booking.setDistance(rs.getFloat("distance"));
                booking.setBooktime(rs.getTimestamp("booktime"));
                Timestamp startDate = rs.getTimestamp("starttime");
                if(startDate != null) {
                   booking.setStarttime(startDate);
                }
                Timestamp endDate = rs.getTimestamp("endtime");
                if(endDate != null) {
                   booking.setEndtime(endDate);
                }
                booking.setVehicleId(rs.getInt("vehicle_id"));
                booking.setCustomerId(rs.getInt("customer_id"));
                booking.setBranchId(rs.getInt("branch_id"));

                System.out.println("Booking time: " + booking.getBooktime());
                bookingList.add(booking);
            }
            return bookingList;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }
    
    public List<Booking> getBookingsByDriverId(int driverId) throws DatabaseException {
        String query = "SELECT b.id, b.fare, b.status, b.customer_feedback, b.driver_feedback, b.distance, " +
                       "b.booktime, b.starttime, b.endtime, b.vehicle_id, b.customer_id, b.branch_id FROM booking b " +
                       "INNER JOIN vehicle v ON b.vehicle_id = v.id " +
                       "INNER JOIN driver d ON v.driver_id = d.id " +
                       "WHERE d.id =?";
        
        List<Booking> bookingList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setInt(1, driverId);

            rs = statement.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("b.id"));
                booking.setFare(rs.getBigDecimal("fare"));
                booking.setStatus(rs.getString("status"));
                booking.setCustomerFeedback(rs.getString("customer_feedback"));
                booking.setDriverFeedback(rs.getString("driver_feedback"));
                booking.setDistance(rs.getFloat("distance"));
                booking.setBooktime(rs.getTimestamp("booktime"));
                Timestamp startDate = rs.getTimestamp("starttime");
                if(startDate != null) {
                   booking.setStarttime(startDate);
                }
                Timestamp endDate = rs.getTimestamp("endtime");
                if(endDate != null) {
                   booking.setEndtime(endDate);
                }
                booking.setVehicleId(rs.getInt("vehicle_id"));
                booking.setCustomerId(rs.getInt("customer_id"));
                booking.setBranchId(rs.getInt("branch_id"));

                bookingList.add(booking);
            }
            return bookingList;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }
    
    public List<Booking> getBookingsByBranchId(int branchId) throws DatabaseException {
        String queryWhere = "";
        if(branchId > 0) {
            queryWhere = "where branch_id =?";
        }
        
        String query = "SELECT id, fare, status, customer_feedback, driver_feedback, distance,"
                + " booktime, starttime, endtime, vehicle_id, customer_id, branch_id FROM booking " + queryWhere;
        List<Booking> bookingList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            if(branchId > 0) {
                statement.setInt(1, branchId);
            }
            
            rs = statement.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setFare(rs.getBigDecimal("fare"));
                booking.setStatus(rs.getString("status"));
                booking.setCustomerFeedback(rs.getString("customer_feedback"));
                booking.setDriverFeedback(rs.getString("driver_feedback"));
                booking.setDistance(rs.getFloat("distance"));
                booking.setBooktime(rs.getTimestamp("booktime"));
                Timestamp startDate = rs.getTimestamp("starttime");
                if(startDate != null) {
                   booking.setStarttime(startDate);
                }
                Timestamp endDate = rs.getTimestamp("endtime");
                if(endDate != null) {
                   booking.setEndtime(endDate);
                }
                booking.setVehicleId(rs.getInt("vehicle_id"));
                booking.setCustomerId(rs.getInt("customer_id"));
                booking.setBranchId(rs.getInt("branch_id"));

                bookingList.add(booking);
            }
            return bookingList;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }
    
    public List<BookingDetail> getBookingsDetailsByBranchId(int branchId, Date starttime, Date endtime) throws DatabaseException {
        String query = " SELECT concat(v.make, ' ', v.model) as vehicledetail, d.name as drivername, c.name as customername, b.id, b.fare, b.status, b.customer_feedback, b.driver_feedback, b.distance, " +
                       "    b.booktime, b.starttime, b.endtime, b.vehicle_id, b.customer_id, b.branch_id FROM booking b " +
                       "    INNER JOIN customer c ON b.customer_id = c.id " +
                       "    INNER JOIN vehicle v ON b.vehicle_id = v.id " +
                       "    INNER JOIN driver d ON v.driver_id = d.id " +
                       "    WHERE (b.branch_id = ? OR -1 = ?) AND (b.booktime BETWEEN ? AND ?) ";
        
        List<BookingDetail> bookingDetailsList = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            connection = DatabaseManager.getConnection();
            statement = DatabaseManager.getPreparedStatement(connection, query);
            statement.setInt(1, branchId);
            statement.setInt(2, branchId);
            statement.setTimestamp(3, new Timestamp(starttime.getTime()));
            statement.setTimestamp(4, new Timestamp(endtime.getTime()));
            
            rs = statement.executeQuery();

            while (rs.next()) {
                BookingDetail booking = new BookingDetail();
                booking.setId(rs.getInt("id"));
                booking.setFare(rs.getBigDecimal("fare"));
                booking.setStatus(rs.getString("status"));
                booking.setCustomerFeedback(rs.getString("customer_feedback"));
                booking.setDriverFeedback(rs.getString("driver_feedback"));
                booking.setDistance(rs.getFloat("distance"));
                booking.setBooktime(rs.getTimestamp("booktime"));
                Timestamp startDate = rs.getTimestamp("starttime");
                if(startDate != null) {
                   booking.setStarttime(startDate);
                }
                Timestamp endDate = rs.getTimestamp("endtime");
                if(endDate != null) {
                   booking.setEndtime(endDate);
                }
                booking.setVehicleId(rs.getInt("vehicle_id"));
                booking.setCustomerId(rs.getInt("customer_id"));
                booking.setBranchId(rs.getInt("branch_id"));
                
                booking.setVehicleDetail(rs.getString("vehicledetail"));
                booking.setDriverName(rs.getString("drivername"));
                booking.setCustomerName(rs.getString("customername"));

                bookingDetailsList.add(booking);
            }
            return bookingDetailsList;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        } finally {
            DatabaseManager.closeResources(rs, statement, connection);
        }
    }
}
