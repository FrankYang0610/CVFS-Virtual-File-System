package hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;

import java.io.Serializable;

/**
 * <h3>The {@code Criterion} Interface</h3>
 * This interface represents the criteria.
 */
public interface Criterion extends Serializable {
    /**
     * Check if a file satisfies the criterion.
     * @param file the file to check.
     * @return if the file satisfies the criterion. 
     */
    public boolean check(File file);

    /**
     * Get the name of the criterion.
     * @return the name of the criterion.
     */
    public String getName();

    /**
     * To prevent the deletion of this criterion when other criteria depend on it, we implemented the reference counting. The criterion can only be safely deleted when the reference count is zero. This is similar to the {@code ON DELETE RESTRICT} action in databases. This is mainly for the stability of our system.
     * <p>
     * The reference counts is only updated when another criterion actually loaded into the criteria list of the File System. 
     * @return the current reference count of the criterion.
     */
    public int getReferenceCount();

    /**
     * Increase the reference count (by 1).
     */
    public void increaseReferenceCount();

    /**
     * Decrease the reference count (by 1).
     */
    public void decreaseReferenceCount();

    /**
     * Get the dependencies of this criterion.
     * @return the dependencies of this criterion.
     */
    public Criterion[] getDependencies();
}
