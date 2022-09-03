/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.dto.Login;
import lk.gocheeta.web.service.repository.LoginRepository;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import lk.gocheeta.web.service.repository.exception.ServiceException;

/**
 *
 * @author asha
 */
public class LoginService {

    private final LoginRepository loginRepository = LoginRepository.getInstance();
    private static final Logger loger = Logger.getLogger(LoginService.class.getName());

    public Login addLogin(Login login) throws ServiceException {
        try {
            return loginRepository.addLogin(login);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public Login updateLogin(Login branch) throws ServiceException {
        try {
            return loginRepository.updateLogin(branch);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public List<Login> getLogins() throws ServiceException {
        try {
            return loginRepository.getLogins();
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public boolean deleteLogin(int id) throws ServiceException {
        try {
            return loginRepository.deleteLogin(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
    
     public Login authenticate(String username, String password) throws ServiceException {
        try {
            return loginRepository.authenticate(username, password);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
     
     public boolean changePassword(String username, String password, String newPassword) throws ServiceException {
        try {
            return loginRepository.changePassword(username, password, newPassword);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
