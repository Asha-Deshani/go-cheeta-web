/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.dto.Admin;
import lk.gocheeta.web.service.repository.AdminRepository;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import lk.gocheeta.web.service.repository.exception.ServiceException;

/**
 *
 * @author asha
 */
public class AdminService {

    private final AdminRepository customerRepository = AdminRepository.getInstance();
    private static final Logger loger = Logger.getLogger(AdminService.class.getName());

    public Admin addAdmin(Admin admin) throws ServiceException {
        try {
            return customerRepository.addAdmin(admin);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public Admin updateAdmin(Admin admin) throws ServiceException {
        try {
            return customerRepository.updateAdmin(admin);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public Admin getAdmin(int id) throws ServiceException {
        try {
            return customerRepository.getAdmin(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }

    public boolean deleteAdmin(int id) throws ServiceException {
        try {
            return customerRepository.deleteAdmin(id);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new ServiceException(ex.getMessage());
        }
    }
}
