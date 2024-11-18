package hk.edu.polyu.comp.comp2021.cvfs.model.entities.vdisk;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.ModelInternalUse;
import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.CannotInitializeVDiskException;

// import java.io.Serial;
import java.io.Serializable;

/**
 * <h3>The {@code VDisk} Class</h3>
 * This class represents the virtual disk, including the disk management program.
 */
public final class VDisk implements Serializable {
    // @Serial
    private static final long serialVersionUID = 1L;

    private final long diskSize;

    private final Directory rootDirectory;


    /**
     * <h4>Simulation Notes</h4>
     *
     * This simulates a new virtual disk of size {@code diskSize} is created. However, many details are not yet simulated, such as address range allocation and virtual address mapping tables. We simply specify the disk size, and several methods use this value to ensure that we will not over-allocate beyond the capacity. If we do exceed it, the program actually will [not] spontaneously generate an error (because all memory is handled by JVM, which sounds frustrating), but we could assume that doing so would damage existing data.
     *
     * @param diskSize the size of the new disk.
     * @throws CannotInitializeVDiskException if the {@code diskSize} parameter is invalid.
     */
    public VDisk(long diskSize) throws CannotInitializeVDiskException {
        if (diskSize <= 0) {
            throw new CannotInitializeVDiskException("Disk size must be greater than zero.");
        }

        this.diskSize = diskSize;
        this.rootDirectory = new Directory(true);
    }

    
    /* Part of the simulation: interfaces involving accessing the disk */

    /**
     * Get the free space of the virtual disk.
     * @return the free space of the virtual disk.
     */
    @ModelInternalUse
    public long __INTERNAL__getFreeSpace() {
        return diskSize - rootDirectory.getSize();
    }

    /**
     * Get the root directory of the virtual disk.
     * @return the root directory of the virtual disk.
     */
    @ModelInternalUse
    public Directory __INTERNAL__getRootDirectory() {
        return rootDirectory;
    }
}

