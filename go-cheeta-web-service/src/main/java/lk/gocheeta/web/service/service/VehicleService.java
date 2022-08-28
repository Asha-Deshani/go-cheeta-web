/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.dto.Vehicle;
import lk.gocheeta.web.service.repository.VehicleRepository;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import lk.gocheeta.web.service.repository.exception.ServiceException;

/**
 *
 * @author asha
 */
public class VehicleService {

    private final VehicleRepository vehicleRepository = VehicleRepository.getInstance();
    private static final Logger loger = Logger.getLogger(VehicleService.class.getName());

    public Vehicle AddVehicle(Vehicle vehicle) throws ServiceException {
        try {
            return vehicleRepository.addVehicle(vehicle);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
    
    public Vehicle UpdateVehicle(Vehicle vehicle) throws ServiceException {
        try {
            return vehicleRepository.updateVehicle(vehicle);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
    
    public Vehicle GetVehicle(int id) throws ServiceException {
        try {
            return vehicleRepository.getVehicle(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
