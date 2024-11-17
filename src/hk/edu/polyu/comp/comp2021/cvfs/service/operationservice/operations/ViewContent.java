package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

/**
 * <h3>The {@code ViewContent} Operation Class</h3>
 * This class encapsulates the operation of the {@code view} command, which is used to output the file contents. This is not mentioned in the instruction document, but we introduced it because it is an necessary feature of the file system.
 */
public final class ViewContent implements Operation {
    private final FileSystem fs;

    private final String filename;

    /**
     * Construct a new {@code View} Operation.
     * <p>
     * User command: {@code view <docName>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public ViewContent(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
        this.filename = command[1];
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            File document = fs.findFile(fs.getWorkingDirectory(), filename);
            if (document instanceof Document) {
                return "The content of " + document.getFullname() + ":\n" +
                        ((Document) document).getContent();
            } else {
                throw new OperationCannotExecuteException("The file is not a document!");
            }
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 2) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1));
        }
    }
}
