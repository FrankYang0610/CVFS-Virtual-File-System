package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

/**
 * <h3>The {@code ModifyContent} Operation Class</h3>
 * This class encapsulates the operation of the {@code modify} command, which is used to modify the file contents. This is not mentioned in the instruction document, but we introduced it because it is an necessary feature of the file system.
 */
public final class ModifyContent implements UndoableOperation {
    private final FileSystem fs;

    private final OperationType type;

    private final String fileName;

    private String oldContent;

    private final String newContent;

    private File file;


    /**
     * Construct a new {@code ChangeDir} Operation. 
     * <p>
     * Constructor for the {@code OperationFactory} and for the {@code modify} command.
     * <p>
     * User command: {@code modify <docName> <newContent>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public ModifyContent(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
        this.fileName = command[1];
        this.newContent = command[2];
        this.type = OperationType.REGULAR;
    }

    /**
     * Constructor for another {@code Operation} objects, for the {@code undo} operation.
     * @param fs the reference to the file system.
     * @param file the reference to the file to modify.
     * @param newContent the new content of the document.
     * @param type the type of the operation.
     * @implNote No {@code commandValidityCheck} required for this method, because an undo operation shall always be legal.
     */
    public ModifyContent(FileSystem fs, File file, String newContent, OperationType type) {
        this.fs = fs;
        this.file = file;
        this.fileName = file.getName();
        this.newContent = newContent;
        this.type = type;
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            if (file == null) {
                file = fs.findFile(fs.getWorkingDirectory(), fileName);
            }

            if (!(file instanceof Document)) {
                throw new OperationCannotExecuteException(fileName + " is not a document.");
            }

            oldContent = ((Document)file).getContent();
            fs.modifyDocument(file, newContent);

            return "The content has been successfully modified.";
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 3) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1) + ".");
        }
    }

    @Override
    public boolean isUndoOperation() {
        return type == OperationType.UNDO;
    }

    @Override
    public boolean isRedoOperation() {
        return type == OperationType.REDO;
    }

    @Override
    public Operation getUndoOperation() {
        return new ModifyContent(fs, file, oldContent, type.getInverseType());
    }
}
