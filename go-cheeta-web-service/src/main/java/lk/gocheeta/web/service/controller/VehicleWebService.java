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
import lk.gocheeta.web.service.dto.Vehicle;
import lk.gocheeta.web.service.repository.exception.ControlerException;
import lk.gocheeta.web.service.repository.exception.ServiceException;
import lk.gocheeta.web.service.service.VehicleService;

/**
 *
 * @author asha
 */
@WebService(serviceName = "VehicleWebService")
public class VehicleWebService {
    
    private VehicleService vehicleService;
    private static final Logger loger = Logger.getLogger(VehicleService.class.getName());

    public VehicleWebService() {
        vehicleService = new VehicleService();
    }


   @WebMethod(operationName = "updateVehicle")
    public Vehicle updateVehicle(@WebParam(name = "vehicle") Vehicle vehicle) throws ControlerException {
        try {
            return vehicleService.updateVehicle(vehicle);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "addVehicle")
    public Vehicle addVehicle(@WebParam(name = "vehicle") Vehicle vehicle) throws ControlerException {
        try {
            return vehicleService.addVehicle(vehicle);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "deleteVehicle")
    public boolean deleteVehicle(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return vehicleService.deleteVehicle(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getVehicleByBranchId")
    public Vehicle getVehicleByBranchId(@WebParam(name = "branchId") int branchId) throws ControlerException {
        try {
            return vehicleService.getVehicleByBranchId(branchId);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
}
