package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.Criterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.InvalidCommandException;

import java.util.Collection;

/**
 * <h3>The {@code PrintAllCriteria} Operation Class</h3>
 * This class encapsulates the operation of the {@code printAllCriteria} command, see {@code [REQ12]}.
 */
public final class PrintAllCriteria implements Operation {
    private final FileSystem fs;


    /**
     * Construct a new {@code PrintAllCriteria} Operation.
     * <p>
     * User command: {@code printAllCriteria}
     *
     * @param fs the reference to the file system.
     * @param command the parsed user command.
     * @throws InvalidCommandException if the parameter(s) in the command are invalid.
     */
    public PrintAllCriteria(FileSystem fs, String[] command) throws InvalidCommandException {
        commandValidityCheck(command);
        this.fs = fs;
    }

    @Override
    public String exec() throws OperationCannotExecuteException {
        StringBuilder result = new StringBuilder();
        Collection<Criterion> criteria = fs.getAllCriteria().values();

        if (!criteria.isEmpty()) {
            result.append("There are ").append(criteria.size()).append(" criteria in the working directory: \n");
            for (Criterion criterion : criteria) {
                result.append(criterion.toString()).append("\n");
            }
            result.deleteCharAt(result.length() - 1);
        } else {
            result.append("There are no criteria in the working directory.");
        }

        return result.toString();
    }

    @Override
    public void commandValidityCheck(String[] command) throws InvalidCommandException {
        if (command.length != 1) {
            throw new InvalidCommandException("Wrong number of parameters: " + (command.length - 1) + ".");
        }
    }
}
