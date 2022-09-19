/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.gocheeta.web.service.repository;

import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertTrue;
import lk.gocheeta.web.service.dto.Branch;
import lk.gocheeta.web.service.dto.Location;
import lk.gocheeta.web.service.repository.exception.DatabaseException;
import org.junit.AfterClass;
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
public class LocationRepositoryTest {

    private static LocationRepository locationRepository;
    private static BranchRepository branchRepository;

    private static final Logger loger = Logger.getLogger(LocationRepository.class.getName());
    private static Branch branch;

    @BeforeClass
    public static void setup() {
        locationRepository = new LocationRepository();
        branchRepository = new BranchRepository();

        branch = new Branch();
        branch.setCity("TEST_1 Colombo");
        branch.setName("TEST_1 Colombo City Branch");

        try {
            branch = branchRepository.addBranch(branch);
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
        }
    }

    @AfterClass
    public static void cleanup() {
        try {
            branchRepository.deleteBranch(branch.getId());
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void addGetUpdateDeleteLocation() {
        Location location = new Location();
        location.setAddress("TEST_1 Address");
        location.setBranchId(branch.getId());

        try {
            location = locationRepository.addLocation(location);
            assertNotEquals(0, location.getId());

            Location dblocation = locationRepository.getLocation(location.getId());
            assertEquals(location.getAddress(), dblocation.getAddress());

            dblocation.setAddress("TEST_1 ADD 2");
            locationRepository.updateLocation(dblocation);

            Location upDatedlocation = locationRepository.getLocation(location.getId());
            assertEquals(dblocation.getAddress(), upDatedlocation.getAddress());
            assertNotEquals(location.getAddress(), upDatedlocation.getAddress());

            assertTrue(locationRepository.deleteLocation(location.getId()));
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void addDuplicateLocationSameAddressFail() {
        Location location = new Location();
        location.setAddress("TEST_1 Address");
        location.setBranchId(branch.getId());

        try {
            location = locationRepository.addLocation(location);
            assertNotEquals(0, location.getId());

            Location locationNew = new Location();
            locationNew.setAddress("TEST_1 Address");
            locationNew.setBranchId(branch.getId());
            assertThrows(DatabaseException.class, () -> locationRepository.addLocation(locationNew));

            assertTrue(locationRepository.deleteLocation(location.getId()));
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void deleteNonExistsLocationFail() {
        try {
            assertFalse(locationRepository.deleteLocation(-999));
        } catch (DatabaseException ex) {
            loger.log(Level.SEVERE, null, ex);
        }
    }
}
