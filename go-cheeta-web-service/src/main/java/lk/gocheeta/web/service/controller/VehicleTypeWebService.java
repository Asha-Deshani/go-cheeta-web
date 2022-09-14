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
import lk.gocheeta.web.service.dto.VehicleType;
import lk.gocheeta.web.service.repository.exception.ControlerException;
import lk.gocheeta.web.service.repository.exception.ServiceException;
import lk.gocheeta.web.service.service.VehicleTypeService;

/**
 *
 * @author asha
 */
@WebService(serviceName = "VehicleTypeWebService")
public class VehicleTypeWebService {
    
    private final VehicleTypeService vehicleTypeService;
    private static final Logger loger = Logger.getLogger(VehicleTypeService.class.getName());

    public VehicleTypeWebService() {
        vehicleTypeService = new VehicleTypeService();
    }


   @WebMethod(operationName = "updateVehicleType")
    public VehicleType updateVehicleType(@WebParam(name = "vehicleType") VehicleType vehicleType) throws ControlerException {
        try {
            return vehicleTypeService.updateVehicleType(vehicleType);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "addVehicleType")
    public VehicleType addVehicleType(@WebParam(name = "vehicleType") VehicleType vehicleType) throws ControlerException {
        try {
            return vehicleTypeService.addVehicleType(vehicleType);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "deleteVehicleType")
    public boolean deleteVehicleType(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return vehicleTypeService.deleteVehicleType(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getVehicleType")
    public VehicleType getVehicleType(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return vehicleTypeService.getVehicleType(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getVehicleTypes")
    public List<VehicleType> getVehicleTypes() throws ControlerException {
        try {
            return vehicleTypeService.getVehicleTypes();
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
}
