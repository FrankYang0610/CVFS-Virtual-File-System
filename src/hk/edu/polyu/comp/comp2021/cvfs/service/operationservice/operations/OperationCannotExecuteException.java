package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.globalexceptions.CVFS_Exception;

/**
 * <h3>The {@code OperationCannotExecuteOperation} Exception</h3>
 * Thrown to indicate that the the operation cannot be executed due to various reasons.
 */
public class OperationCannotExecuteException extends CVFS_Exception {
    /**
     * Construct a new exception.
     * @param operationName the name of the operation which causes this exception.
     */
    public OperationCannotExecuteException(String operationName) {
        super("Operation cannot execute: " + operationName);
    }
}
