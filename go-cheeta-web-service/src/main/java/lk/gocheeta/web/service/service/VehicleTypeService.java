/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.dto.VehicleType;
import lk.gocheeta.web.service.repository.VehicleTypeRepository;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import lk.gocheeta.web.service.repository.exception.ServiceException;

/**
 *
 * @author asha
 */
public class VehicleTypeService {

    private final VehicleTypeRepository vehicleTypeRepository = VehicleTypeRepository.getInstance();
    private static final Logger loger = Logger.getLogger(VehicleTypeService.class.getName());

    public VehicleType addVehicleType(VehicleType vehicleType) throws ServiceException {
        try {
            return vehicleTypeRepository.addVehicleType(vehicleType);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public VehicleType updateVehicleType(VehicleType vehicleType) throws ServiceException {
        try {
            return vehicleTypeRepository.updateVehicleType(vehicleType);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public VehicleType getVehicleType(int id) throws ServiceException {
        try {
            return vehicleTypeRepository.getVehicleType(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public boolean deleteVehicleType(int id) throws ServiceException {
        try {
            return vehicleTypeRepository.deleteVehicleType(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
