/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lk.gocheeta.web.service.database.DatabaseManager;
import lk.gocheeta.web.service.dto.Branch;
import lk.gocheeta.web.service.repository.exception.DatabaseException;

/**
 *
 * @author asha
 */
public class BranchRepository {

    private final Logger loger = Logger.getLogger(BranchRepository.class.getName());

    public Branch addBranch(Branch branch) throws DatabaseException {
        try {
            String query = "INSERT INTO branch (name, city) VALUES (?, ?)";

            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setString(1, branch.getName());
            statement.setString(2, branch.getCity());

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                branch.setId(rs.getInt(1));
            }

            return branch;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public Branch updateBranch(Branch branch) throws DatabaseException {
        try {
            String query = "UPDATE branch SET name=?, city=? WHERE id =?";

            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setString(1, branch.getName());
            statement.setString(2, branch.getCity());
            statement.setInt(3, branch.getId());

            statement.executeUpdate();
            return branch;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }

    public Branch getBranch(int id) throws DatabaseException {
        try {
            String query = "SELECT name, city FROM branch WHERE id =?";

            Branch branch = null;
            PreparedStatement statement = DatabaseManager.getPreparedStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                branch = new Branch();

                branch.setId(id);
                branch.setName(rs.getString("name"));
                branch.setCity(rs.getString("city"));
            }
            return branch;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }
}
