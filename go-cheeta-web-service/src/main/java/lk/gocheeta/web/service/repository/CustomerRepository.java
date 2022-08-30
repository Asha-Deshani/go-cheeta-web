/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.database.DatabaseManager;
import lk.gocheeta.web.service.dto.Customer;
import lk.gocheeta.web.service.repository.exception.DatabaseException;

/**
 *
 * @author asha
 */
public class CustomerRepository {

    private static CustomerRepository instance;
    private static final Logger loger = Logger.getLogger(CustomerRepository.class.getName());

    public static CustomerRepository getInstance() {
        if (instance == null) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    public Customer addCustomer(Customer customer) throws DatabaseException {
        String query = "INSERT INTO customer (name, telephone, email) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getTelephone());
            statement.setString(3, customer.getEmail());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                customer.setId(rs.getInt(1));
            }

            return customer;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public Customer updateCustomer(Customer customer) throws DatabaseException {
        String query = "UPDATE customer SET name=?, telephone=?, email=? WHERE id =?";

        try {
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getTelephone());
            statement.setString(3, customer.getEmail());
            statement.setInt(4, customer.getId());

            statement.executeUpdate();
            return customer;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public Customer getCustomer(int id) throws DatabaseException {
        String query = "SELECT name, telephone, email FROM customer WHERE id =?";

        Customer customer = null;

        try {
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                customer = new Customer();

                customer.setId(id);
                customer.setName(rs.getString("name"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
            }
            return customer;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public boolean deleteCustomer(int id) throws DatabaseException {
        String query = "DELETE FROM customer WHERE id = ?";

        try {
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setInt(1, id);
            
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }
}
