/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.dto.Driver;
import lk.gocheeta.web.service.repository.DriverRepository;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import lk.gocheeta.web.service.repository.exception.ServiceException;

/**
 *
 * @author asha
 */
public class DriverService {

    private final DriverRepository driverRepository = DriverRepository.getInstance();
    private static final Logger loger = Logger.getLogger(DriverService.class.getName());

    public Driver addDriver(Driver driver) throws ServiceException {
        try {
            return driverRepository.addDriver(driver);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public Driver updateDriver(Driver driver) throws ServiceException {
        try {
            return driverRepository.updateDriver(driver);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public Driver getDriver(int id) throws ServiceException {
        try {
            return driverRepository.getDriver(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public boolean deleteDriver(int id) throws ServiceException {
        try {
            return driverRepository.deleteDriver(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
