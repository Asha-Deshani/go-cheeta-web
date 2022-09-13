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
import lk.gocheeta.web.service.dto.Location;
import lk.gocheeta.web.service.repository.exception.ControlerException;
import lk.gocheeta.web.service.repository.exception.ServiceException;
import lk.gocheeta.web.service.service.LocationService;

/**
 *
 * @author asha
 */
@WebService(serviceName = "LocationWebService")
public class LocationWebService {
    
    private LocationService locationService;
    private static final Logger loger = Logger.getLogger(LocationWebService.class.getName());

    public LocationWebService() {
        locationService = new LocationService();
    }

    @WebMethod(operationName = "updateLocation")
    public Location updateLocation(@WebParam(name = "location") Location location) throws ControlerException {
        try {
            return locationService.updateLocation(location);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "addLocation")
    public Location addLocation(@WebParam(name = "branch") Location location) throws ControlerException {
        try {
            return locationService.addLocation(location);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "deleteLocation")
    public boolean deleteLocation(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return locationService.deleteLocation(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getLocationsByBranchId")
    public List<Location> getLocationsByBranch(@WebParam(name = "branchId") int branchId) throws ControlerException {
        try {
            return locationService.getLocationsByBranchId(branchId);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getLocations")
    public List<Location> getLocations() throws ControlerException {
        try {
            return locationService.getLocations();
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getLocation")
    public Location getLocation(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return locationService.getLocation(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
}
