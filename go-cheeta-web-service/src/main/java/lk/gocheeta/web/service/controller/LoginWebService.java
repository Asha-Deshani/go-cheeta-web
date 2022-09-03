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
import lk.gocheeta.web.service.dto.Login;
import lk.gocheeta.web.service.repository.exception.ControlerException;
import lk.gocheeta.web.service.repository.exception.ServiceException;
import lk.gocheeta.web.service.service.BranchService;
import lk.gocheeta.web.service.service.LoginService;

/**
 *
 * @author asha
 */
@WebService(serviceName = "LoginWebService")
public class LoginWebService {

     private LoginService loginService;
    private static final Logger loger = Logger.getLogger(LoginWebService.class.getName());

    public LoginWebService() {
        loginService = new LoginService();
    }

    @WebMethod(operationName = "getLogins")
    public List<Login> getLogins() throws ControlerException {
        try {
            return loginService.getLogins();
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "updateLogin")
    public Login updateLogin(@WebParam(name = "login") Login login) throws ControlerException {
        try {
            return loginService.updateLogin(login);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "addLogin")
    public Login addLogin(@WebParam(name = "login") Login login) throws ControlerException {
        try {
            return loginService.addLogin(login);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "deleteLogin")
    public boolean deleteLogin(@WebParam(name = "id") int id) throws ControlerException {
        try {
            return loginService.deleteLogin(id);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "authenticate")
    public Login authenticate(@WebParam(name = "username") String username, 
            @WebParam(name = "password") String password) throws ControlerException {
        try {
            return loginService.authenticate(username, password);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
    
    @WebMethod(operationName = "changePassword")
    public Login changePassword(@WebParam(name = "username") String username, 
            @WebParam(name = "password") String password, @WebParam(name = "newPassword") String newPassword) throws ControlerException {
        try {
            return loginService.authenticate(username, password);
        } catch (ServiceException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ControlerException(ex.getMessage());
        }
    }
}
