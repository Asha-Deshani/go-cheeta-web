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
import lk.gocheeta.web.service.dto.Customer;
import lk.gocheeta.web.service.repository.exception.ControlerException;
import lk.gocheeta.web.service.repository.exception.ServiceException;
import lk.gocheeta.web.service.service.CustomerService;

/**
 *
 * @author asha
 */
@WebService(serviceName = "CustomerWebService")
public class CustomerWebService {

    private CustomerService  customerService;
    private static final Logger loger = Logger.getLogger(CustomerWebService.class.getName());

    public CustomerWebService() {
        customerService = new CustomerService();
    }
    
    @WebMethod(operationName = "updateCustomer")
    public Customer updateCustomer(@WebParam(name = "customer") Customer customer) throws ControlerException {
        try {
            return customerService.updateCustomer(customer);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "addCustomer")
    public Customer addCustomer(@WebParam(name = "customer") Customer customer) throws ControlerException {
        try {
            return customerService.addCustomer(customer);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "deleteCustomer")
    public boolean deleteCustomer(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return customerService.deleteCustomer(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
}
