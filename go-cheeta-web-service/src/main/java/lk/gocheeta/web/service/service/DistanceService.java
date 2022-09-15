/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.repository.DistanceMatrixRepository;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import lk.gocheeta.web.service.repository.exception.ServiceException;

/**
 *
 * @author asha
 */
public class DistanceService {

    private final DistanceMatrixRepository distanceMatrixRepository = DistanceMatrixRepository.getInstance();
    private static final Logger loger = Logger.getLogger(DistanceService.class.getName());

    
    public float getDistance(int id1, int id2) throws ServiceException {
        try {
            return distanceMatrixRepository.getDistance(id1, id2);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
