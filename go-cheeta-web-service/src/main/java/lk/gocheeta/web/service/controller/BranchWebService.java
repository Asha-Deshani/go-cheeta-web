/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package lk.gocheeta.web.service.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import lk.gocheeta.web.service.dto.Branch;
import lk.gocheeta.web.service.repository.exception.ControlerException;
import lk.gocheeta.web.service.repository.exception.ServiceException;
import lk.gocheeta.web.service.service.BranchService;

/**
 *
 * @author asha
 */
@WebService(serviceName = "BranchWebService")
public class BranchWebService {

    private final BranchService branchService;
    private static final Logger loger = Logger.getLogger(BranchWebService.class.getName());

    public BranchWebService() {
        branchService = new BranchService();
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello kamal" + txt + " !";
    }

    @WebMethod(operationName = "getBranches")
    public List<Branch> getBranches() throws ControlerException {
        try {
            return branchService.getBranches();
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "getBranch")
    public Branch getBranch(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return branchService.getBranch(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "updateBranch")
    public Branch updateBranch(@WebParam(name = "branch") Branch branch) throws ControlerException {
        try {
            return branchService.updateBranch(branch);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "addBranch")
    public Branch addBranch(@WebParam(name = "branch") Branch branch) throws ControlerException {
        try {
            return branchService.addBranch(branch);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "deleteBranch")
    public boolean deleteBranch(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return branchService.deleteBranch(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
}
