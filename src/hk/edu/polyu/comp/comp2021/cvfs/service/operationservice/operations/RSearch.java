package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

import java.util.Collection;

/**
 * <h3>The {@code RSearch} Operation Class</h3>
 * This class encapsulates the operation of the {@code RSearch} command, see {@code [REQ14]}.
 */
public final class RSearch implements Operation {
    private final FileSystem fs;

    private final String criName;

    private int fileCount = 0;

    private long totalSize = 0;


    /**
     * Construct a new {@code RSearch} Operation.
     * <p>
     * User command: {@code rSearch <criName>}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public RSearch(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
        this.criName = command[1];
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            Directory workingDirectory = fs.getWorkingDirectory();
            Criterion criterion = fs.findCriterion(criName);

            if (fs.getAllFiles(workingDirectory).isEmpty()) {
                return "There are no files in the working directory.";
            }

            StringBuilder result = new StringBuilder();
            result.append("These file(s) satisfy the criterion: ").append(criterion).append(":\n").
                    append(recursiveSearch(workingDirectory, criterion));
            result.deleteCharAt(result.length() - 1);
            result.append("\n").
                    append("Report: ").append(fileCount).append(" files, with total size ").append(totalSize).append(".");

            return result.toString();
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 2) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1) + ".");
        }
    }

    private String recursiveSearch(Directory directory, Criterion criterion) throws OperationCannotExecuteException {
        try {
            StringBuilder result = new StringBuilder();
            Collection<File> files = fs.getAllFiles(directory).values();

            for (File file : files) {
                if (criterion.check(file)) {
                    result.append(file.getPath()).
                            append(" (").append(file.getSize()).append(")\n");
                    fileCount++;
                    totalSize += file.getSize();
                }

                if (file instanceof Directory) {
                    result.append(recursiveSearch((Directory) file, criterion));
                }
            }
            return result.toString();
        } catch (ModelException e) {
            throw new OperationCannotExecuteException("Some directories are invalid with unknown reasons.");
        }
    }
}
