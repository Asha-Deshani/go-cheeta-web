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
import lk.gocheeta.web.service.repository.exception.ControlerException;
import lk.gocheeta.web.service.repository.exception.ServiceException;
import lk.gocheeta.web.service.service.DistanceService;

/**
 *
 * @author asha
 */
@WebService(serviceName = "DistanceWebService")
public class DistanceWebService {
    
    private final DistanceService distanceService;
    private static final Logger loger = Logger.getLogger(DistanceWebService.class.getName());

    public DistanceWebService() {
        distanceService = new DistanceService();
    }

    @WebMethod(operationName = "getDistance")
    public float getDistance(@WebParam(name = "locId1") int locId1, @WebParam(name = "locId2") int locId2) throws ControlerException {
        try {
            return distanceService.getDistance(locId1, locId2);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
}
