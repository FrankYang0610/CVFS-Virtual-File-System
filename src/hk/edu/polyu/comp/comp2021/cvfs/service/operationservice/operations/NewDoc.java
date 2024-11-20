package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;
import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Document;

/**
 * <h3>The {@code NewDoc} Operation Class</h3>
 * This class encapsulates the operation of the {@code newDoc} command, see {@code [REQ2]}.
 * <p>
 * The undo operation of this is {@code Remove}.
 */
public final class NewDoc implements UndoableOperation {
    private final FileSystem fs;

    /**
     * The name of the new document.
     */
    private final String name;

    /**
     * The type of the new document.
     */
    private final String type;

    /**
     * The content of the new document.
     */
    private final String content;

    /**
     * The document object, should be created in the exec() method.
     */
    private Document document;


    /**
     * Construct a new {@code NewDoc} Operation.
     * <p>
     * User command: {@code newDoc <docName> <docType> <docContent>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public NewDoc(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
        this.name = command[1];
        this.type = command[2];
        this.content = command[3];
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            document = new Document(name, type, content, fs.getWorkingDirectory());
            fs.storeFile(document);
            return "New document " + document.getFullname() + " has been created successfully and added into " + fs.getParent(document).getFullname() + ".";
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 4) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1) + ".");
        }

        if (command[1].length() > 10) {
            throw new InvalidCommandException("The length of the filename cannot be more than 10, with provided length: " + command[1].length() + ".");
        }
    }

    /**
     * {@code NewDoc} is not the undo operation of any operation, hence the {@code isUndoOperation()} shall always return false.
     */
    @Override
    public boolean isUndoOperation() {
        return false;
    }

    /**
     * {@code NewDoc} is not the redo operation of any operation, hence the {@code isRedoOperation()} shall always return false.
     */
    @Override
    public boolean isRedoOperation() {
        return false;
    }

    @Override
    public Operation getInverseOperation() {
        return new Remove(fs, document, OperationType.UNDO);
    }
}
