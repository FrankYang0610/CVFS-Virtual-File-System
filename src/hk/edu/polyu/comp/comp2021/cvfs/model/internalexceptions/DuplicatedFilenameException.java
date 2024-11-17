package hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;

/**
 * <h3>The {@code DuplicatedFilenameException} Exception</h3>
 * Thrown to indicate that there is another file in the directory with the same name. The instruction document specifies that, even a directory and a file, or two files of different types, cannot share the same name.
 */
public class DuplicatedFilenameException extends ModelException {
    /**
     * Construct a new exception.
     * @param filename the duplicated filename.
     */
    public DuplicatedFilenameException(String filename) {
      super("Duplicated filename " + "\"" + filename + "\"" + ".");
    }
}
