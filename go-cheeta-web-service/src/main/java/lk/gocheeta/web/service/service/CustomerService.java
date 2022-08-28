/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.dto.Customer;
import lk.gocheeta.web.service.repository.CustomerRepository;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import lk.gocheeta.web.service.repository.exception.ServiceException;

/**
 *
 * @author asha
 */
public class CustomerService {

    private final CustomerRepository customerRepository = CustomerRepository.getInstance();
    private static final Logger loger = Logger.getLogger(CustomerService.class.getName());

    public Customer AddBooking(Customer customer) throws ServiceException {
        try {
            return customerRepository.addCustomer(customer);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
    
    public Customer UpdateBooking(Customer customer) throws ServiceException {
        try {
            return customerRepository.updateCustomer(customer);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
    
    public Customer GetBooking(int id) throws ServiceException {
        try {
            return customerRepository.getCustomer(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
