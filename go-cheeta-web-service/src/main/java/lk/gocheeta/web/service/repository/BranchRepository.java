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
import lk.gocheeta.web.service.dto.Branch;
import lk.gocheeta.web.service.repository.exception.DatabaseException;

/**
 *
 * @author asha
 */
public class BranchRepository {

    private static BranchRepository instance;
    private static final Logger loger = Logger.getLogger(BranchRepository.class.getName());

    public static BranchRepository getInstance() {
        if (instance == null) {
            instance = new BranchRepository();
        }
        return instance;
    }

    public Branch addBranch(Branch branch) throws DatabaseException {
        String query = "INSERT INTO branch (name, city) VALUES (?, ?)";

        try {
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
        String query = "UPDATE branch SET name=?, city=? WHERE id =?";

        try {
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
        String query = "SELECT name, city FROM branch WHERE id =?";
        Branch branch = null;

        try {
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

    public boolean deleteBranch(int id) throws DatabaseException {
        String query = "DELETE FROM branch WHERE id =?";

        try {
            Statement statement = DatabaseManager.getStatment();
            return statement.executeUpdate(query) > 1;
        } catch (SQLException ex) {
            loger.log(Level.SEVERE, null, ex);
            throw new DatabaseException(ex.getMessage());
        }
    }
}
