/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.dto.Location;
import lk.gocheeta.web.service.repository.LocationRepository;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import lk.gocheeta.web.service.repository.exception.ServiceException;

/**
 *
 * @author asha
 */
public class LocationService {

    private final LocationRepository locationRepository = LocationRepository.getInstance();
    private static final Logger loger = Logger.getLogger(LocationService.class.getName());

    public Location addLocation(Location location) throws ServiceException {
        try {
            return locationRepository.addLocation(location);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public Location updateLocation(Location location) throws ServiceException {
        try {
            return locationRepository.updateLocation(location);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public Location getLocation(int id) throws ServiceException {
        try {
            return locationRepository.getLocation(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public boolean deleteLocation(int id) throws ServiceException {
        try {
            return locationRepository.deleteLocation(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
