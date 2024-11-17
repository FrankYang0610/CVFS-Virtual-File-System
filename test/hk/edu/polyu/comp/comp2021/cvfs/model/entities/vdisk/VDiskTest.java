package hk.edu.polyu.comp.comp2021.cvfs.model.entities.vdisk;

import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.CannotInitializeVDiskException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class VDiskTest {
    @Test
    void testInvalidParameters() {
        try {
            VDisk vDisk = new VDisk(-1000);
        } catch (CannotInitializeVDiskException e) {
            assertTrue(e.getMessage().contains("Cannot initialize the virtual disk"));
        }
    }

    @Test
    void testNewVDisk() {
        try {
            VDisk vDisk = new VDisk(1000);
            assertEquals(1000, vDisk.__INTERNAL__getFreeSpace());
            assertNotNull(vDisk.__INTERNAL__getRootDirectory());
        } catch (CannotInitializeVDiskException ignored) { }
    }
}