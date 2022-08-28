/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.dto.Branch;
import lk.gocheeta.web.service.repository.BranchRepository;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import lk.gocheeta.web.service.repository.exception.ServiceException;

/**
 *
 * @author asha
 */
public class BranchService {

    private final BranchRepository branchRepository = BranchRepository.getInstance();
    private static final Logger loger = Logger.getLogger(BranchService.class.getName());

    public Branch AddBranch(Branch branch) throws ServiceException {
        try {
            return branchRepository.addBranch(branch);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
    
    public Branch UpdateBranch(Branch branch) throws ServiceException {
        try {
            return branchRepository.updateBranch(branch);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
    
    public Branch GetBranch(int id) throws ServiceException {
        try {
            return branchRepository.getBranch(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
