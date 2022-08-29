/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.repository;

import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertTrue;
import lk.gocheeta.web.service.dto.Branch;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author asha
 */
public class BranchRepositoryTest {

    private static BranchRepository branchRepository;
    private static final Logger loger = Logger.getLogger(BranchRepositoryTest.class.getName());

    @BeforeClass
    public static void setup() {
        branchRepository = new BranchRepository();
    }

    @Test
    public void addDeleteBranchSuccess() {
        Branch branch = new Branch();
        branch.setCity("TEST_1 Colombo");
        branch.setName("TEST_1 Colombo City Branch");

        try {
            branch = branchRepository.addBranch(branch);
            assertNotEquals(0, branch.getId());
            
            
            Branch cxc = branchRepository.getBranch(branch.getId());
            assertEquals(branch.getCity(), cxc.getCity());
            
            boolean dd = branchRepository.deleteBranch(branch.getId());
            assertTrue(dd);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
        }
    }
}
