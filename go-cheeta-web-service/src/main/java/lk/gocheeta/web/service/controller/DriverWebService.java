/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package lk.gocheeta.web.service.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import lk.gocheeta.web.service.dto.Driver;
import lk.gocheeta.web.service.repository.exception.ControlerException;
import lk.gocheeta.web.service.repository.exception.ServiceException;
import lk.gocheeta.web.service.service.DriverService;

/**
 *
 * @author asha
 */
@WebService(serviceName = "DriverWebService")
public class DriverWebService {

    private final DriverService  driverService;
    private static final Logger loger = Logger.getLogger(DriverWebService.class.getName());

    public DriverWebService() {
        driverService = new DriverService();
    }
    
    @WebMethod(operationName = "updateDriver")
    public Driver updateDriver(@WebParam(name = "driver") Driver driver) throws ControlerException {
        try {
            return driverService.updateDriver(driver);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "addDriver")
    public Driver addDriver(@WebParam(name = "driver") Driver driver) throws ControlerException {
        try {
            return driverService.addDriver(driver);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "deleteDriver")
    public boolean deleteDriver(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return driverService.deleteDriver(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getDriver")
    public Driver getDriver(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return driverService.getDriver(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getDrivers")
    public List<Driver> getDrivers() throws ControlerException {
        try {
            return driverService.getDrivers();
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getDriversByBranchId")
    public List<Driver> getDriversByBranchId(@WebParam(name = "branchid") int branchid) throws ControlerException {
        try {
            return driverService.getDriversByBranchId(branchid);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
}
