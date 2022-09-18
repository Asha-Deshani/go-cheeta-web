/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.service;

import java.util.List;
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

    public Customer addCustomer(Customer customer) throws ServiceException {
        try {
            return customerRepository.addCustomer(customer);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public Customer updateCustomer(Customer customer) throws ServiceException {
        try {
            return customerRepository.updateCustomer(customer);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public Customer getCustomer(int id) throws ServiceException {
        try {
            return customerRepository.getCustomer(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public boolean deleteCustomer(int id) throws ServiceException {
        try {
            return customerRepository.deleteCustomer(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
    
    public List<Customer> getCustomers() throws ServiceException {
        try {
            return customerRepository.getCustomers();
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
