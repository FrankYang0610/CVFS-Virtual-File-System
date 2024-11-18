package hk.edu.polyu.comp.comp2021.cvfs.model.entities.file;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.ModelInternalUse;
import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.CannotInitializeFileException;

// import java.io.Serial;
import java.util.TreeMap;

/**
 * <h3>The {@code Directory} Class</h3>
 * This class represents the directories.
 */
public final class Directory implements File {
    // @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The size of an empty non-root directory. According to the instruction document, this is 40.
     */
    public static final long EMPTY_NON_ROOT_DIRECTORY_SIZE = 40;

    /**
     * The name of the directory.
     */
    private String name;

    /**
     * The size of the directory.
     */
    private long size;

    /**
     * The files containing in this directory (subdirectories and documents).
     */
    private final TreeMap<String, File> files = new TreeMap<>();

    /**
     * The reference to the parent of the document. Noted this should always be a directory, but the actual type is {@code File} here to reduce dependencies.
     */
    private File parent;

    /**
     * The full path to the directory. For example, {@code $:main:thisDirectory}.
     */
    private String path;


    /**
     * Constructs a root directory. This constructor can only be invoked by the constructor of the {@code VDisk} class.
     * @param rootIdentifier an identifier that must be {@code true}.
     */
    @ModelInternalUse
    public Directory(boolean rootIdentifier) {
        if (rootIdentifier) {
            name = "$";
            size = 0;
            // parent = null;
            path = name;
        }
    }

    /**
     * Constructs a new directory with a name and a given parent folder.
     * @param name the name of the new directory.
     * @param parent the expected parent of the new directory.
     * @throws CannotInitializeFileException if any errors are met.
     */
    public Directory(String name, Directory parent) throws CannotInitializeFileException {
        if ((name == null) || !name.matches("[a-zA-Z0-9]+") || (parent == null)) {
            throw new CannotInitializeFileException("Invalid parameter(s) for initializing the directory.");
        }

        this.name = name;
        this.size = EMPTY_NON_ROOT_DIRECTORY_SIZE;
        this.parent = parent;
        this.path = (parent.getPath() + ":" + getFullname()); // root folder doesn't have a parent.
    }

    /**
     * Returns if a directory is the root.
     * @return if the directory is the root.
     */
    public boolean isRootDirectory() {
        return name.equals("$") && parent == null;
    }

    /**
     * Find the file with its pure name (not fullname).
     * @param name the name of the file.
     * @return the {@code File} object of the file.
     */
    @ModelInternalUse
    public File __INTERNAL__findFile(String name) {
        return files.get(name);
    }

    /**
     * Check if there is a file called {@code targetName} in this directory.
     * @param name the target directory name to check.
     * @return if there exists a directory called {@code targetName} in this directory.
     */
    @ModelInternalUse
    public boolean __INTERNAL__existsName(String name) {
        return files.get(name) != null;
    }

    /**
     * Add a new file into the current directory and update all the size information.
     * @param file the file to add.
     */
    @ModelInternalUse
    public void __INTERNAL__add(File file) {
        files.put(file.getName(), file);
        __INTERNAL__updateSize(file.getSize());
    }

    /**
     * Update the size with a known change {@code delta}, so there's no more need to count the size of the whole directory again.
     * @param delta the size change.
     */
    @ModelInternalUse
    public void __INTERNAL__updateSize(long delta) {
        size += delta;
        if (parent != null) {
            ((Directory) parent).__INTERNAL__updateSize(delta);
        }
    }

    /**
     * Delete a file under the directory and update all the size information.
     * <p>
     * This should be a pakcage private method because it needs to be guaranteed that {@code file} is [exactly in] this directory.
     *
     * @param file the name of the file to be deleted.
     */
    @ModelInternalUse
    public void __INTERNAL__delete(File file) {
        files.remove(file.getName());
        __INTERNAL__updateSize(-file.getSize());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFullname() {
        return name;
    }

    @Override
    public long getSize() {
        return size;
    }

    /**
     * Get all files.
     * @return all files in this directory.
     */
    @ModelInternalUse
    public TreeMap<String, File> __MODEL_INTERNAL__getFiles() {
        return files;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    @ModelInternalUse
    public File __INTERNAL__getParent() {
        return parent;
    }

    /**
     * Noted that the directory may be already the root, then return {@code this}.
     */
    @Override
    @ModelInternalUse
    public File __INTERNAL__getRoot() {
        Directory toRoot = this;
        while (toRoot.__INTERNAL__getParent() != null) {
            toRoot = (Directory)toRoot.__INTERNAL__getParent();
        }
        return toRoot;
    }

    @Override
    @ModelInternalUse
    public void __INTERNAL__setName(String newName) {
        name = newName;
    }
}
