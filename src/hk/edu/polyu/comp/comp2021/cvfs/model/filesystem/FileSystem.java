package hk.edu.polyu.comp.comp2021.cvfs.model.filesystem;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion.IsDocument;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.vdisk.VDisk;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.*;

import java.io.*;
import java.util.TreeMap;

/**
 * <h3>The {@code FileSystem} Class of the Model Part</h3>
 * The {@code FileSystem} class abstracts an entire file system layer.
 * <p>
 * Functionally speaking, it records the state of the application and process file system requests, including:
 * <ul>
 *     <li>Create new files (requires allocating disk space)</li>
 *     <li>Remove files (requires deallocating disk space)</li>
 *     <li>Rename files (requires disk read & write)</li>
 *     <li>Modify documents (also requires disk read & write)</li>
 *     <li>Find the parent of files (requires disk read)</li>
 *     <li>Find if a file exists in the directory (also requires disk read)</li>
 * </ul>
 * <p>
 * Logically speaking, it provides all interfaces for managing virtual disks, directories, and documents. The Service and other upper parts only know the existence of them, but cannot perform operations on them directly. We created this for simulation. In real file systems, concepts like directories and documents are abstractions created by the file system.
 * <p>
 * This is the important part of the Model, which handles the business logic and interacts with the Controller.
 */
public final class FileSystem {
    /* Current resources of the file system, as well as the application */

    /**
     * The current virtual disk.
     * <p>
     * Initially, this should be {@code null}. Any command shall be invalid if this is {@code null}.
     */
    private VDisk currentVDisk;

    /**
     * The current working directory. It's guaranteed that this directory always exists.
     * <p>
     * Initially, this should be {@code null}. When a VDisk is mounted (i.e., {@code currentVDisk != null}), this should be directed to the root directory of that VDisk.
     */
    private Directory workingDirectory;

    /**
     * The loaded criteria.
     * <p>
     * Initially, this should be empty (instead of {@code null}). This will be, and will only be cleared when the {@code Controller} object is quit.
     *
     * @implNote A {@code HashMap<String, Criterion>} is used to store the criteria, where the {@code String} objects represent the names of the criteria, and the {@code Criterion} objects are the criteria.
     */
    private TreeMap<String, Criterion> criteria;


    /**
     * Constructs a new state. This should only be invoked by the {@code Application} object to start the entire system.
     */
    public FileSystem() {
        criteria = new TreeMap<>();
        criteria.put("IsDocument", new IsDocument());
    }


    /* Methods involving virtual disk mounting and ejecting */

    /**
     * Eject the current virtual disk.
     * This method is obsolete because the inspection tool does not allow the null assignments.
     * Then all attempts to eject the virtual disk without mounting a new one will require restarting the system.
     */
     // public void ejectVDisk() {
     //    workingDirectory = null;
     //    currentVDisk = null;
     // }

    /**
     * Mounting a new virtual disk.
     * @param vDisk the new virtual disk.
     */
    public void mountVDisk(VDisk vDisk) {
        // ejectVDisk();
        currentVDisk = vDisk;
        workingDirectory = vDisk.__INTERNAL__getRootDirectory();
    }


    /* Methods involving overall resource management */

    /**
     * Release the resources.
     */
    public void releaseResource() {
        // ejectVDisk();
        criteria.clear();
    }


    /* Public getters */

    /**
     * Get the root directory of the current virtual disk.
     * @return the {@code Directory} object of the root directory of the current virtual disk.
     * @throws NoMountedDiskOrWorkingDirectoryException if no virtual disk is mounted.
     */
    public Directory getRootDirectory() throws NoMountedDiskOrWorkingDirectoryException {
        if (currentVDisk == null) {
            throw new NoMountedDiskOrWorkingDirectoryException();
        }
        return currentVDisk.__INTERNAL__getRootDirectory();
    }

    /**
     * Get the working directory of the current virtual disk.
     * @return the {@code Directory} object of the current working directory.
     * @throws NoMountedDiskOrWorkingDirectoryException if no virtual disk is mounted.
     * @throws FileNotExistsException if the working directory has been deleted.
     */
    public Directory getWorkingDirectory() throws NoMountedDiskOrWorkingDirectoryException, FileNotExistsException {
        if (workingDirectory == null) {
            throw new NoMountedDiskOrWorkingDirectoryException();
        }
        checkExistence(workingDirectory);
        return workingDirectory;
    }

    /**
     * Get the full path of the working directory safely (without an exception).
     * @return the full path of the working directory. Returns an empty string if there is no disk mounted.
     * @implNote This method will not throw any exceptions.
     */
    public String getWorkingDirectoryPathSafely() {
        try {
            return getWorkingDirectory().getPath();
        } catch (ModelException e) {
            return "";
        }
    }

    /* Public setter */

    /**
     * Set a new working directory.
     * @param newWorkingDirectory the {@code Directory} object of the new directory. It's guaranteed that the new directory exists in the virtual disk.
     * @throws FileNotExistsException if the directory has been deleted or there is no such file in the directory with the given name.
     * @throws WrongAddressSpaceException if the file is not associated with the currently mounted virtual disk.
     */
    public void setNewWorkingDirectory(Directory newWorkingDirectory) throws FileNotExistsException, WrongAddressSpaceException {
        checkExistence(newWorkingDirectory);
        checkAddressSpace(newWorkingDirectory);
        this.workingDirectory = newWorkingDirectory;
    }


    /* File system responsibilities */
    // The implementation details of the following methods should not be exposed to the Service part, and the sub-methods they used should also not be exposed to other parts.

    /**
     * Get all files of a directory.
     * @param directory the directory.
     * @return all files of that directory, in a {@code TreeMap} form.
     * @throws FileNotExistsException if the directory has been deleted.
     */
    public TreeMap<String, File> getAllFiles(Directory directory) throws FileNotExistsException {
        checkExistence(directory);
        return directory.__MODEL_INTERNAL__getFiles();
    }

    /**
     * Find a file with a specific name in a directory.
     * @param directory the directory.
     * @param name the name to find.
     * @return the reference to the file with that specific name.
     * @throws FileNotExistsException if the directory has been deleted or there is no such file in the directory with the given name.
     */
    public File findFile(Directory directory, String name) throws FileNotExistsException {
        checkExistence(directory);
        if (!directory.__INTERNAL__existsName(name)) {
            throw new FileNotExistsException(name);
        }
        return directory.__INTERNAL__findFile(name);
    }

    /**
     * Get the parent reference of a file.
     * @param file the file.
     * @return the reference of the parent of the file.
     * @throws FileNotExistsException if this file has been deleted.
     */
    public File getParent(File file) throws FileNotExistsException {
        checkExistence(file);
        return file.__INTERNAL__getParent();
    }


    /**
     * Store a file in the virtual disk.
     *
     * <h4>Simulation Notes</h4>
     * Here, we simulate how a real file system should manage memory as closely as possible.
     * <ul>
     *     <li>First, the disk accepts a file as the one to be written. It needs to check if there is enough space to store the file.</li>
     *     <li>Second, the higher-level system specifies an expected location for this file (in this system, is the {@code file.getParent()} method), the file system needs to check if this location belongs to it. Or, it cannot store the file. In an actual file system, this might result in a mismatch error in virtual addresses.</li>
     *     <li>Third, the virtual disk will allocate a location to store this file. Simulating blocks or partitions of a disk, as well as file storage structures like B-trees, is too complicated, so we skipped this part. In fact, the {@code add()} method should include these logic.</li>
     *     <li>If we force a write when storage space is not enough, or if the writing involves the address of another virtual disk, it will cause fatal errors. Since this involves address mapping and disk storage structures deeply, we skipped this part. However, ideally, the {@code add()} method would report a fatal error in such cases.</li>
     * </ul>
     *
     * @param file the file to be Added to the virtual disk.
     * @throws WrongAddressSpaceException if the file is not associated with the currently mounted virtual disk.
     * @throws VDiskOutOfSpaceException if the virtual disk is out of space.
     * @throws DuplicatedFilenameException if another file has the same name as this one.
     */
    public void storeFile(File file) throws WrongAddressSpaceException, VDiskOutOfSpaceException, DuplicatedFilenameException {
        checkAddressSpace(file);

        if (file.getSize() > currentVDisk.__INTERNAL__getFreeSpace()) {
            throw new VDiskOutOfSpaceException(file.getSize(), currentVDisk.__INTERNAL__getFreeSpace());
        }

        Directory parent = (Directory)file.__INTERNAL__getParent();
        if (parent.__INTERNAL__existsName(file.getName())) {
            throw new DuplicatedFilenameException(file.getName());
        }
        parent.__INTERNAL__add(file);
    }


    /**
     * Remove a file from the virtual disk.
     *
     * <h4>Simulation Notes</h4>
     * This is similar to the {@code storeFile()} method.
     *
     * @param file the file to be Added to the virtual disk.
     * @throws WrongAddressSpaceException if the file is not associated with the currently mounted virtual disk.
     * @throws FileNotExistsException if the file has been deleted.
     * @throws CannotEditRootDirectoryException if the Service part is attempting to remove the root directory.
     */
    public void removeFile(File file) throws WrongAddressSpaceException, FileNotExistsException, CannotEditRootDirectoryException {
        checkAddressSpace(file);
        checkExistence(file);
        if ((file instanceof Directory) && ((Directory)file).isRootDirectory()) {
            throw new CannotEditRootDirectoryException();
        }
        ((Directory)file.__INTERNAL__getParent()).__INTERNAL__delete(file);
    }

    /**
     * Rename a file.
     * @param file the file to rename.
     * @param newName the new name of the file.
     * @throws WrongAddressSpaceException if the file is not associated with the currently mounted virtual disk.
     * @throws FileNotExistsException if the file has been deleted.
     * @throws DuplicatedFilenameException if another file has the same name as the expected new name.
     * @throws CannotEditRootDirectoryException if the Service part is attempting to remove the root directory.
     */
    public void renameFile(File file, String newName) throws WrongAddressSpaceException, FileNotExistsException, DuplicatedFilenameException, CannotEditRootDirectoryException {
        checkAddressSpace(file);
        checkExistence(file);

        if ((file instanceof Directory) && ((Directory)file).isRootDirectory()) {
            throw new CannotEditRootDirectoryException();
        }

        Directory parent = (Directory)file.__INTERNAL__getParent();
        if (parent.__INTERNAL__existsName(newName)) {
            throw new DuplicatedFilenameException(newName);
        }

        parent.__INTERNAL__delete(file);
        file.__INTERNAL__setName(newName);
        parent.__INTERNAL__add(file);
    }

    /**
     * Modify the content of a file.
     * @param file the file to modify.
     * @param newContent the new content of the file.
     * @throws WrongAddressSpaceException if the file is not associated with the currently mounted virtual disk.
     * @throws FileNotExistsException if the file has been deleted.
     * @throws VDiskOutOfSpaceException if the virtual disk is out of space.
     */
    public void modifyDocument(File file, String newContent) throws WrongAddressSpaceException, FileNotExistsException, VDiskOutOfSpaceException {
        checkAddressSpace(file);
        checkExistence(file);

        if (!(file instanceof Document)) {
            throw new FileNotExistsException(file.getFullname() + " as a document");
        }

        // Pre-calculate the required space.
        if ((Document.EMPTY_DOCUMENT_SIZE + newContent.length() * 2L) > currentVDisk.__INTERNAL__getFreeSpace()) {
            throw new VDiskOutOfSpaceException((Document.EMPTY_DOCUMENT_SIZE + newContent.length() * 2L), currentVDisk.__INTERNAL__getFreeSpace());
        }

        ((Document)file).__INTERNAL__setContent(newContent);
    }

    /**
     * Check if the file exists in the currently mounted virtual disk, or if it is going to be added in the currently mounted virtual disk.
     * @throws WrongAddressSpaceException if the file is not associated with the currently mounted virtual disk.
     */
    private void checkAddressSpace(File file) throws WrongAddressSpaceException {
        if (currentVDisk != null && file.__INTERNAL__getRoot() != currentVDisk.__INTERNAL__getRootDirectory()) {
            throw new WrongAddressSpaceException();
        }
    }

    /**
     * Check if the file still exists in its parent directory, i.e., if the file has been deleted.
     * @param file the file to check.
     * @throws FileNotExistsException if the file no longer exists in its parent directory.
     */
    private void checkExistence(File file) throws FileNotExistsException {
        // It is unable to check the existence of a file when its parent is null.
        // For the root directories, we assume they always exists.
        if (file.__INTERNAL__getParent() != null) {
            if (!((Directory)(file.__INTERNAL__getParent())).__MODEL_INTERNAL__getFiles().containsValue(file)) {
                throw new FileNotExistsException(file.getFullname());
            }
        }
    }


    /* Methods related to the criteria */

    /**
     * Add a new criterion to the criterion list.
     * @param criterion the new criterion to add.
     * @throws DuplicatedCriterionNameException if another criterion in the list shares the same name as the new one.
     */
    public void addCriterion(Criterion criterion) throws DuplicatedCriterionNameException {
        String name = criterion.getName();
        if (criteria.containsKey(name)) {
            throw new DuplicatedCriterionNameException(criterion.getName());
        }

        for (Criterion dependency : criterion.getDependencies()) {
            dependency.__INTERNAL__increaseReferenceCount();
        }

        criteria.put(name, criterion);
    }

    /**
     * Get all criteria.
     * @return all criteria.
     */
    public TreeMap<String, Criterion> getAllCriteria() {
        return criteria;
    }

    /**
     * Find the existing criterion by its name.
     * @param name the name of the criterion to find.
     * @return the criterion object with the specific name.
     * @throws CriterionNotExistsException if the criterion with that specific name is not in the list.
     */
    public Criterion findCriterion(String name) throws CriterionNotExistsException {
        Criterion criterion = criteria.get(name);
        if (criterion == null) {
            throw new CriterionNotExistsException(name);
        }
        return criterion;
    }

    /**
     * Remove the criterion from the criterion list.
     * @param criterion the criterion to delete.
     * @throws CriterionNotExistsException if the criterion is not in the list.
     * @throws CannotDeleteCriterionException if another criteria depend on this criterion.
     */
    public void removeCriterion(Criterion criterion) throws CriterionNotExistsException, CannotDeleteCriterionException {
        if (!criteria.containsValue(criterion)) {
            throw new CriterionNotExistsException(criterion.getName());
        }

        if (criterion.getReferenceCount() > 0) {
            throw new CannotDeleteCriterionException();
        }

        for (Criterion dependency : criterion.getDependencies()) {
            dependency.__INTERNAL__decreaseReferenceCount();
        }

        criteria.remove(criterion.getName());
    }


    /* Methods related to the local file system */

    /**
     * Load the virtual disk from the local file system.
     * @param path the path of the local file system where the virtual disk will be loaded.
     * @throws LocalFileSystemException if any local file system errors are met.
     */
    public void loadVDisk(String path) throws LocalFileSystemException {
        // ejectVDisk();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            mountVDisk((VDisk)ois.readObject());
        } catch (ClassNotFoundException | IOException e) {
            throw new LocalFileSystemException(e.getMessage());
        }
    }

    /**
     * Load criteria from the local file system.
     * @param path the path of the local file system where the criteria will be loaded.
     * @throws LocalFileSystemException if any local file system errors are met.
     */
    public void loadCriteria(String path) throws LocalFileSystemException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            criteria = (TreeMap<String, Criterion>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new LocalFileSystemException(e.getMessage());
        }
    }

    /**
     * Save the virtual disk to the local file system.
     * @param path the path of the local file system where the virtual disk will be saved.
     * @throws NoMountedDiskOrWorkingDirectoryException if no virtual disk is mounted.
     * @throws LocalFileSystemException if any local file system errors are met.
     */
    public void saveVDisk(String path) throws NoMountedDiskOrWorkingDirectoryException, LocalFileSystemException {
        if (currentVDisk == null) {
            throw new NoMountedDiskOrWorkingDirectoryException();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(currentVDisk);
        } catch (IOException e) {
            throw new LocalFileSystemException(e.getMessage());
        }
    }

    /**
     * Save all criteria to the local file system.
     * @param path the path of the local file system where all criteria will be saved.
     * @throws LocalFileSystemException if any local file system errors are met.
     */
    public void saveAllCriteria(String path) throws LocalFileSystemException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(criteria);
        } catch (IOException e) {
            throw new LocalFileSystemException(e.getMessage());
        }
    }
}
