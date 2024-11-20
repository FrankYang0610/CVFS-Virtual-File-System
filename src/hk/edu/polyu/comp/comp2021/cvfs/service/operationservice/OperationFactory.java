package hk.edu.polyu.comp.comp2021.cvfs.service.operationservice;

import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations.*;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;

/**
 * <h3>The {@code OperationFactory} Class</h3>
 * The {@code OperationFactory} class is designed to create {@code Operation} objects of the operations specified by user commands.
 * <p>
 * The only method {@code createOperation()} takes the file system (a {@code FileSystem} object), an operation record (an {@code OperationRecord} object) and a command (a {@code String[]} object) as inputs, and generates the corresponding {@code Operation} object.
 * @see Operation
 */
public final class OperationFactory {
    /**
     * Create an {@code Operation} object from the parsed user command.
     * @param fs the reference to the file system.
     * @param operationRecord the reference to the operation record.
     * @param command the parsed user command.
     * @return the {@code Operation} object of the operation specified by the user command.
     * @throws InvalidCommandException if the user command is invalid.
     */
    public Operation createOperation(FileSystem fs,
                                     OperationRecord operationRecord,
                                     String[] command) throws InvalidCommandException {
        if (command == null || command.length == 0) {
            throw new InvalidCommandException("Empty command.");
        }

        switch (command[0]) {
            case "newDisk": // [REQ1]
                return new NewDisk(fs, operationRecord, command);
            case "newDoc": // [REQ2]
                return new NewDoc(fs, command);
            case "newDir": // [REQ3]
                return new NewDir(fs, command);
            case "view":
                return new ViewContent(fs, command);
            case "delete": // [REQ4]
                return new Remove(fs, command);
            case "rename": // [REQ5]
                return new Rename(fs, command);
            case "modify":
                return new ModifyContent(fs, command);
            case "changeDir": // [REQ6]
                return new ChangeDir(fs, command);
            case "list": // [REQ7]
                return new List(fs, command);
            case "rList": // [REQ8]
                return new RList(fs, command);
            case "newSimpleCri": // [REQ9]
                return new NewSimpleCri(fs, command);
            // case "isDocument": // [REQ10]
            case "newNegation": // [REQ11]
                return new NewNegation(fs, command);
            case "newBinaryCri": // [REQ11]
                return new NewBinaryCri(fs, command);
            case "deleteCri":
                return new RemoveCri(fs, command);
            case "printAllCriteria": // [REQ12]
                return new PrintAllCriteria(fs, command);
            case "search": // [REQ13]
                return new Search(fs, command);
            case "rSearch": // [REQ14]
                return new RSearch(fs, command);
            case "save": // [REQ15]
                return new Save(fs, command);
            case "load": // [REQ16]
                return new Load(fs, operationRecord, command);
            case "saveCri": // [BON1]
                return new SaveCri(fs, command);
            case "loadCri": // [BON1]
                return new LoadCri(fs, operationRecord, command);
            case "undo": // [BON2]
                return operationRecord.popForUndo().getInverseOperation();
            case "redo": // [BON2]
                return operationRecord.popForRedo().getInverseOperation();
            // case "reset": // obsoleted, since ejectVDisk() is no longer supported.
            //     return new Reset(fs, operationRecord, command);
            case "quit": // [REQ17]
                operationRecord.clearFileRelated();
                return new Quit(fs, operationRecord, command);
            default:
                throw new InvalidCommandException("Unknown command: " + command[0] + ".");
        }
    }
}
