package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.ModelException;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.File;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

import java.util.*;
import java.util.List;

/**
 * <h3>The {@code RList} Operation Class</h3>
 * This class encapsulates the operation of the {@code rList} command, see {@code [REQ8]}.
 */
public final class RList implements Operation {
    private final FileSystem fs;

    private int fileCount = 0;


    /**
     * Construct a new {@code RList} Operation.
     * <p>
     * User command: {@code rList}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public RList(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        try {
            Directory workingDirectory = fs.getWorkingDirectory();

            // Here, the size of the directory itself is not considered.
            long size = workingDirectory.isRootDirectory() ? workingDirectory.getSize() : (workingDirectory.getSize() - Directory.EMPTY_NON_ROOT_DIRECTORY_SIZE);

            if (fs.getAllFiles(workingDirectory).isEmpty()) {
                return "There are no files in the working directory.";
            }

            StringBuilder result = new StringBuilder();
            result.append(workingDirectory.getPath()).
                    append("\n").
                    append(getDirectoryTreeStructure(workingDirectory, ""));
            result.deleteCharAt(result.length() - 1);

            result.append("\n").
                    append("Report: ").append(fileCount).append(" files, with total size ").append(size).append(".");

            return result.toString();
        } catch (ModelException e) {
            throw new OperationCannotExecuteException(e.getMessage());
        }
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 1) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1) + ".");
        }
    }

    /**
     * The method prints the file tree structure recursively.
     * @param directory the directory currently being processed.
     * @param prefix the prefix to add before getting all tree structure of this directory.
     * @return the multiline string of the tree structure of this directory.
     * @implNote assert that the directory is not {@code null}.
     */
    private String getDirectoryTreeStructure(Directory directory, String prefix) throws OperationCannotExecuteException {
        try {
            StringBuilder result = new StringBuilder();

            List<String> documentList = new ArrayList<>();
            List<Directory> subdirectoryList = new ArrayList<>();

            // Get all documents.
            for (File file : fs.getAllFiles(directory).values()) {
                if (file instanceof Document) {
                    documentList.add(file.getFullname() + " (" + file.getSize() + ")");
                    fileCount++;
                }
            }

            // Get all subdirectories.
            for (File file : fs.getAllFiles(directory).values()) {
                if (file instanceof Directory) {
                    subdirectoryList.add((Directory) file);
                    fileCount++;
                }
            }

            // Print out all documents.
            for (int i = 0; i < documentList.size(); i++) {
                result.append(prefix).
                        append(((i < documentList.size() - 1) || (!subdirectoryList.isEmpty()))
                                ? "├──"  // is not the last of this level
                                : "└──"). // is the last of this level
                        append(documentList.get(i)).
                        append("\n");
            }

            // Print out all directories (recursion).
            for (int i = 0; i < subdirectoryList.size(); i++) {
                Directory subdirectory = subdirectoryList.get(i);

                result.append(prefix).
                        append((i < subdirectoryList.size() - 1)
                                ? "├──"  // is not the last of this level
                                : "└──"). // is the last of this level
                        append(subdirectory.getFullname()).
                        append(" (").append(subdirectory.getSize()).append(")\n");

                result.append(getDirectoryTreeStructure(subdirectory,
                        (i < subdirectoryList.size() - 1)
                                ? (prefix + "│\t") // is not the last of this level
                                : (prefix + "\t"))); // is the last of this level
            }

            return result.toString();
        } catch (ModelException e) {
            throw new OperationCannotExecuteException("Some directories are invalid with unknown reasons.");
        }
    }
}
