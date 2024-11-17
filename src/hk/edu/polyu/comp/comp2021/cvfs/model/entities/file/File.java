package hk.edu.polyu.comp.comp2021.cvfs.model.entities.file;

import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.DuplicatedFilenameException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * <h3>The {@code File} Interface</h3>
 * This interface specifies the file system entities, in this project, which are the <b>directories</b> and <b>documents</b>, hence the {@code Directory} class and {@code Document} class shall implement this interface.
 * @see Directory
 * @see Document
 * @see ModelInternalUse
 */
public interface File extends Serializable {
    /**
     * Get the name of the {@code File} object.
     * @return the name of the {@code File} object.
     */
    public String getName();

    /**
     * Get the name with the typename of the {@code File} object.
     * <p>
     * This is not mentioned by the instruction document, this is added just for better looking in {@code [REQ7]}, {@code [REQ8]}, {@code [REQ13]} and {@code [REQ14]}.
     *
     * @return the name of the {@code File} object.
     */
    public String getFullname();

    /**
     * Get the size of the {@code File} object.
     * @return the size of the {@code File} object. More specifically, if the {@code File} object is a document, then {@code getSize()} should return the size of the document; or if the {@code File} object is a directory, then {@code getSize()} should return the total size of the directory, which is the sum of the sizes of all its subdirectories and documents.
     */
    public long getSize();

    /**
     * Get the full path of the {@code File} object.
     * @return the full path to this {@code File} object from the root directory.
     */
    public String getPath();

    /**
     * Get the parent {@code File} object of the current {@code File} object. This is useful for backtracking when the size is updated.
     * @return the parent {@code File} object of this {@code File} object. Noted this method should always return a {@code Directory} object, but it's not ideal to declare here.
     * @implNote The parent information is usually maintained by the inode, but for convenience, we include a direct getter of this information.
     */
    @ModelInternalUse
    public File __INTERNAL__getParent();

    /**
     * Get the root of a {@code File} object.
     * @return the root {@code File} object of this {@code File} object. Noted this method should always return a {@code Directory} object, but it's not ideal to declare here.
     * @implNote The parent information is usually not maintained by the file directly, but for convenience, we still include the getter of this information.
     */
    @ModelInternalUse
    public File __INTERNAL__getRoot();

    /**
     * Set the name of the {@code File} object. This is useful for renaming. Noted that even if this method sets the name, a {@code File} object should have a default name, and a {@code String} object should also be used to initialize the name.
     * @param newName the new name of the file.
     * @throws DuplicatedFilenameException if there is another file with the same name in the directory of this file.
     */
    public void __INTERNAL__setName(String newName) throws DuplicatedFilenameException;
}
