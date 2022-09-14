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
import lk.gocheeta.web.service.dto.Admin;
import lk.gocheeta.web.service.repository.exception.ControlerException;
import lk.gocheeta.web.service.repository.exception.ServiceException;
import lk.gocheeta.web.service.service.AdminService;

/**
 *
 * @author asha
 */
@WebService(serviceName = "AdminWebService")
public class AdminWebService {

    private final AdminService  adminService;
    private static final Logger loger = Logger.getLogger(AdminWebService.class.getName());

    public AdminWebService() {
        adminService = new AdminService();
    }
    
    @WebMethod(operationName = "updateAdmin")
    public Admin updateCustomer(@WebParam(name = "customer") Admin admin) throws ControlerException {
        try {
            return adminService.updateAdmin(admin);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "addAdmin")
    public Admin addAdmin(@WebParam(name = "customer") Admin admin) throws ControlerException {
        try {
            return adminService.addAdmin(admin);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "deleteAdmin")
    public boolean deleteAdmin(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return adminService.deleteAdmin(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "geteAdmin")
    public Admin getAdmin(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return adminService.getAdmin(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
}
