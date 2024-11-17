package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

import java.util.Collection;

/**
 * <h3>The {@code List} Operation Class</h3>
 * This class encapsulates the operation of the {@code list} command, see {@code [REQ7]}.
 */
public final class List implements Operation {
    private final FileSystem fs;


    /**
     * Construct a new {@code ChangeDir} Operation.
     * <p>
     * User command: {@code list}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public List(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            StringBuilder result = new StringBuilder();

            Directory workingDirectory = fs.getWorkingDirectory();

            // Here, the size of the directory itself is not considered.
            long size = workingDirectory.isRootDirectory() ? workingDirectory.getSize() : (workingDirectory.getSize() - Directory.EMPTY_NON_ROOT_DIRECTORY_SIZE);

            Collection<File> files = fs.getAllFiles(workingDirectory).values();

            if (!files.isEmpty()) {
                result.append(workingDirectory.getPath()).
                        append(":\n");

                for (File file : files) {
                    result.append(file.getFullname()).append(" (").append(file.getSize()).append(")\n");
                }

                result.deleteCharAt(result.length() - 1);

                result.append("\n").
                        append("Report: ").append(files.size()).append(" files, with total size ").append(size).append(".");
            } else {
                result.append("There are no files in the working directory.");
            }

            return result.toString();
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 1) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1));
        }
    }
}
