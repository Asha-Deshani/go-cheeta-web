/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.dto.Booking;
import lk.gocheeta.web.service.repository.BookingRepository;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import lk.gocheeta.web.service.repository.exception.ServiceException;

/**
 *
 * @author asha
 */
public class BookingService {

    private final BookingRepository bookingRepository = BookingRepository.getInstance();
    private static final Logger loger = Logger.getLogger(BookingService.class.getName());

    public Booking addBooking(Booking booking) throws ServiceException {
        try {
            return bookingRepository.addBooking(booking);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public Booking updateBooking(Booking booking) throws ServiceException {
        try {
            return bookingRepository.updateBooking(booking);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public Booking getBooking(int id) throws ServiceException {
        try {
            return bookingRepository.getBooking(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public boolean deleteBooking(int id) throws ServiceException {
        try {
            return bookingRepository.deleteBooking(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
