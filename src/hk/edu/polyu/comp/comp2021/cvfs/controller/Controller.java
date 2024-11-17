package hk.edu.polyu.comp.comp2021.cvfs.controller;

import hk.edu.polyu.comp.comp2021.cvfs.cli.Console;

import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.OperationFactory;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.OperationRecord;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations.Operation;
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations.UndoableOperation;

// The Controller part relies on the existence of the Quit operation in the Service part.
// Although this is not perfect, it is the most convenient solution now.
// Quit, as an essential operation, also tends to be exposed externally.
import hk.edu.polyu.comp.comp2021.cvfs.service.operationservice.operations.Quit;

import hk.edu.polyu.comp.comp2021.cvfs.globalexceptions.CVFS_Exception;

import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;

/**
 * <h2>The {@code Controller} Class</h2>
 * {@code Controller} is the Controller part this application.
 * <p>
 * The Controller firstly receives user's input, in this project, is the commands. Then, it converts the commands into operations. The operations are encapsulated into {@code Operation} objects and thus can be executed and managed easily. The Controller will also provide the results of the Operation to the Console, so the user is informed.
 * @see Console
 * @see FileSystem
 */
public final class Controller {
    /**
     * The state of the application, created by the {@code Application} object.
     */
    private final FileSystem fs;

    /**
     * The CLI console, part of the Controller and the View, created by the {@code Application} object.
     */
    private final Console console;

    /**
     * The factory to generate the {@code Operation} objects from user commands.
     */
    private final OperationFactory operationFactory;

    /**
     * The container to record executed operations in an elegant way, which is the core of the undo and redo.
     */
    private final OperationRecord operationRecord;


    /**
     * Constructs a new Controller with a {@code State} object and a {@code Console} object, representing the state of the application and the CLI console.
     * @param fs the {@code State} object representing the state of the application.
     * @param console the {@code Console} object of the CLI console.
     */
    public Controller(FileSystem fs, Console console) {
        this.fs = fs;
        this.console = console;
        operationFactory = new OperationFactory();
        operationRecord = new OperationRecord();
    }

    /**
     * Boot the controller.
     * @implNote  This method should be invoked by the {@code Application} project.
     */
    public void boot() {
        console.boot();
        work();
    }

    /**
     * Let the Controller start working.
     * @implNote This method involves an infinite loop to keep it continuously receiving and executing user commands.
     */
    private void work() {
        while (true) {
            try {
                String[] command = console.getNextCommand(fs.getWorkingDirectoryPathSafely());
                
                Operation operation = operationFactory.createOperation(fs, operationRecord, command);

                assert operation != null;
                String result = operation.exec();
                console.printInformation(result);


                // if (operation instanceof Quit) {
                //     operationRecord.clearFileRelated(); // Automatically 'shut-down' in chain.
                //     return;
                // }

                if (operation instanceof UndoableOperation) {
                    operationRecord.record((UndoableOperation) operation);
                }
            } catch (CVFS_Exception e) {
                console.printErrorStream(e);
            } // end try-catch
        } // end while
    }
}

