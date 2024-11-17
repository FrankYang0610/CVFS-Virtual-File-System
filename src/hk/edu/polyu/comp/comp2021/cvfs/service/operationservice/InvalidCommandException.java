package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice;

import hk.edu.polyu.comp.comp2021.cvfs.globalexceptions.CVFS_Exception;

/**
 * <h3>The {@code InvalidCommandException} Exception</h3>
 * Thrown to indicate that the user provided command is invalid. This is also the general exception of the Service part. 
 */
public class InvalidCommandException extends CVFS_Exception {
    /**
     * Construct a new exception.
     * @param command the invalid command which causes this exception. 
     */
    public InvalidCommandException(String command) {
        super("Invalid command: " + command);
    }
}
