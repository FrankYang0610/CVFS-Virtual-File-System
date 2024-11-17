package hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;

/**
 * <h3>The {@code FileNotExistsException} Exception</h3>
 * Thrown to indicate that the file does not exist in the directory.
 */
public class FileNotExistsException extends ModelException {
    /**
     * Construct a new exception.
     * @param filename the name of the unexisted file.
     */
    public FileNotExistsException(String filename) {
        super("File not exists: " + "\"" + filename + "\"" + ".");
    }
}
