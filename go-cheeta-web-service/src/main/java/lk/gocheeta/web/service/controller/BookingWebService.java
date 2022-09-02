/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package lk.gocheeta.web.service.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import lk.gocheeta.web.service.dto.Booking;
import lk.gocheeta.web.service.repository.exception.ControlerException;
import lk.gocheeta.web.service.repository.exception.ServiceException;
import lk.gocheeta.web.service.service.BookingService;

/**
 *
 * @author asha
 */
@WebService(serviceName = "BookingWebService")
public class BookingWebService {

    private BookingService bookingService;
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
}
