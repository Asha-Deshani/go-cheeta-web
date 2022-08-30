/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.repository;

import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertTrue;
import lk.gocheeta.web.service.dto.Customer;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author asha
 */
public class CustomerRepositoryTest {

    private static CustomerRepository customerRepository;
    private static final Logger loger = Logger.getLogger(CustomerRepository.class.getName());

    @BeforeClass
    public static void setup() {
        customerRepository = new CustomerRepository();
    }

    @Test
    public void addGetUpdateDeleteCustomerSuccess() {
        Customer customer = new Customer();
        customer.setName("TEST_1 Peter");
        customer.setTelephone("TEST_1 07765231458");
        customer.setEmail("peter@gmail.com");

        try {
            customer = customerRepository.addCustomer(customer);
            assertNotEquals(0, customer.getId());

            Customer dbcustomer = customerRepository.getCustomer(customer.getId());
            assertEquals(customer.getEmail(), dbcustomer.getEmail());

            dbcustomer.setTelephone("TEST_1 08823231458");
            customerRepository.updateCustomer(dbcustomer);

            Customer upDatedCustomer = customerRepository.getCustomer(customer.getId());
            assertEquals(dbcustomer.getTelephone(), upDatedCustomer.getTelephone());
            assertNotEquals(customer.getTelephone(), upDatedCustomer.getTelephone());

            assertTrue(customerRepository.deleteCustomer(customer.getId()));
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void addDuplicateBranchCityFail() {
        Customer customer = new Customer();
        customer.setName("TEST_1 Peter");
        customer.setTelephone("TEST_1 07765231458");
        customer.setEmail("peter@gmail.com");

        try {
            customer = customerRepository.addCustomer(customer);
            assertNotEquals(0, customer.getId());

            Customer customerNew = new Customer();
            customerNew.setName("TEST_1 Peter");
            customerNew.setTelephone("TEST_1 07765231458");
            assertThrows(DatabaseException.class, () -> customerRepository.addCustomer(customerNew));

            Customer customerNewEmail = new Customer();
            customerNewEmail.setName("TEST_1 Peter");
            customerNewEmail.setEmail("peter@gmail.com");
            assertThrows(DatabaseException.class, () -> customerRepository.addCustomer(customerNewEmail));

            assertTrue(customerRepository.deleteCustomer(customer.getId()));
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void deleteNonExistsBranchFail() {
        try {
            assertFalse(customerRepository.deleteCustomer(-999));
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
        }
    }

}
