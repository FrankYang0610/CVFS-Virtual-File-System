/**
 * <h3>The {@code Entity} Package</h3>
 * This package includes the entites of the CVFS, including the criterion ({@code criterion} package), the file ({@code file} package) and the virtual disk (the {@code vdisk} package).
 *
 * <h4>Simulation Notes</h4>
 *  * Due to the complexity, it is to hard to completely simulate the file system and storage logic. In this system, both directories and documents are involved at the file system level [and] the disk level, so we can only temporarily treat them as [unified]. However, if the deeper simulation is required, they cannot span across the two levels.
 *
 * @see hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.ModelInternalUse
 */

package hk.edu.polyu.comp.comp2021.cvfs.model.entities;