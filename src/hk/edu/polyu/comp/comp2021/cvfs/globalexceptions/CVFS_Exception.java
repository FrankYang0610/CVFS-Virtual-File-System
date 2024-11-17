package hk.edu.polyu.comp.comp2021.cvfs.globalexceptions;

/**
 * <h2>The {@code CVFS_Exception} Class</h2>
 * The most general exception class of this CVFS application.
 * All other exceptions in the application should extend this exception.
 */
public class CVFS_Exception extends Exception {
    /**
     * Construct a new exception.
     * @param message the exception message.
     */
    public CVFS_Exception(String message) {
        super(message);
    }
}
