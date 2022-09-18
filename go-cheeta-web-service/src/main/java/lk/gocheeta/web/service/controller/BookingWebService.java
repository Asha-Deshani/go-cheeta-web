/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package lk.gocheeta.web.service.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import lk.gocheeta.web.service.dto.Booking;
import lk.gocheeta.web.service.dto.BookingDetail;
import lk.gocheeta.web.service.repository.exception.ControlerException;
import lk.gocheeta.web.service.repository.exception.ServiceException;
import lk.gocheeta.web.service.service.BookingService;

/**
 *
 * @author asha
 */
@WebService(serviceName = "BookingWebService")
public class BookingWebService {

    private final BookingService bookingService;
    private static final Logger loger = Logger.getLogger(BookingService.class.getName());

    public BookingWebService() {
        bookingService = new BookingService();
    }

    @WebMethod(operationName = "updateBooking")
    public Booking updateBooking(@WebParam(name = "booking") Booking booking) throws ControlerException {
        try {
            return bookingService.updateBooking(booking);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }

    @WebMethod(operationName = "addBooking")
    public Booking addBooking(@WebParam(name = "booking") Booking booking) throws ControlerException {
        try {
            return bookingService.addBooking(booking);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getBooking")
    public Booking getBooking(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return bookingService.getBooking(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getBookings")
    public List<Booking> getBookings() throws ControlerException {
        try {
            return bookingService.getBookings();
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getBookingsByCustomerId")
    public List<Booking> getBookingsByCustomerId(@WebParam(name = "customerId") int customerId) throws ControlerException {
        try {
            return bookingService.getBookingsByCustomerId(customerId);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getBookingsByDriverId")
    public List<Booking> getBookingsByDriverId(@WebParam(name = "driverId") int driverId) throws ControlerException {
        try {
            return bookingService.getBookingsByDriverId(driverId);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getBookingsByBranchId")
    public List<Booking> getBookingsByBranchId(@WebParam(name = "branchId") int branchId) throws ControlerException {
        try {
            return bookingService.getBookingsByBranchId(branchId);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getBookingsDetailsByBranchId")
    public List<BookingDetail> getBookingsDetailsByBranchId(@WebParam(name = "branchId") int branchId, @WebParam(name = "starttime") Date starttime, @WebParam(name = "endtime") Date endtime) throws ControlerException {
        try {
            return bookingService.getBookingsDetailsByBranchId(branchId, starttime, endtime);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
}
