# The COMP Virtual File System (CVFS) Project
### _"Peregrine Falcon"_


The goal of this project is to develop an in-memory Virtual File System (VFS), named the COMP VFS (CVFS), in Java. 

A VFS is usually built on top of a host file system to enable uniform access to files located in different host file systems, **while the CVFS simulates a file system in memory.**

COMP Virtual System (CVFS) is a virtual file system where you can create virtual disks and manage files within them. CVFS supports you to create criteria to filter specific files. You can also load/save the virtual disks and criteria file from/to your local file system. 


> This project not only meets the requirements,
> but also explores the design principles and programming philosophies,
> and brings them to reality.
> I'm very happy that this project was designed and completed perfectly.
> 
> YANG Xikun, Nov 2024

> #### Please run the `Application` class to start the CVFS application.


## Authors and Contributions
* **YANG Xikun (25%)**
  * Overall Design, Coding and Report
* **YANG Jinkun (25%)**
  * Model Validating and Improving
* **REN Yixiao (25%)**
  * Test Engineer
* **Arda EREN (25%)**
  * User Manual

## Design Principles
* **Modularity:** The project application is divided into four parts: Model, Controller, Service, and View. Each part is highly cohesive and lowly coupled, interacting through well-defined interfaces. We strictly adhere to the MVC design pattern. 
* **Separation of Responsibilities:** The four parts have distinct and clear responsibilities. Each part focuses on its own duties, without concern about issues not in its scope.
* **Isolation:** Each part [strictly] limits the interfaces provided to other parts, so to prevent excessive interference and coupling.
* **High-Fidelity Simulation:** Since the CVFS is a virtual file system, we aim to make it [as closely as possible] to real file systems or existing virtual file systems. We try our best in our simulations, including interface and responsibility simulations. Some details are too complicated to simulate, so we compromise by making them [appear] realistic to other parts, rather than focusing on every internal detail. 
* **Safest structure:** We require all structures to be safe, especially ensuring that the safety of lower-level structures does not depend on upper-level structures. Reasonable but redundant safety checks are allowed. 

## Project Structure
### The Overall File System Model
Since the core of CVFS is a file system, the very basic part is to **simulate a file system**. 

**A file system abstracts the concepts of files for the users.** More specifically, in a disk, there is not a clear concept of files: a file might be split into multiple parts and stored separately. 

The users, as well as other parts above the file system, such as the View, Controller, and Service in the MVC model mentioned later, all rely on the file system to interact with the virtual disk.


### _The Fetch-Decode-Execute Cycle_
Similar to _the fetch-decode-execute cycle_ of a CPU, our system uses a cycle to keep handling user commands:
1. Receive user's input.
2. Parse the input.
3. Check if the input is valid.
4. Convert the input into an operation.
5. Execute the operation.
6. Present the result back to user.
7. Go back to 1.

This cycle led us to develop an Enhanced MVC Model. See next section.

### The Enhanced MVC Model
There are four parts in our application, the <b>Model</b>, <b>Controller</b>, <b>Service</b> and <b>View</b>. 

Below is a simple diagram showing their relationships, where arrows indicate dependencies (e.g., `A → B` means `A` depends on `B`'s interfaces).

```
+-------------------------+
|    +------------+       |          +------------+          +------------------+
|    | Controller |  ------------->  |  Service   |  ----->  |                  |
|    +------------+       |          +------------+          |   Model          |
|         | |             |                                  |    (FileSystem)  |
|         |  --------------------------------------------->  |                  |
|         |               |                                  +------------------+
|         |               |
|    +------------+       |
|    |    View    |       |
|    +------------+       |
|                         |
| Command Line Interface  |
|            (Console)    |
+-------------------------+
```

#### Further explanation
 * **Application** (as `Application` class) is the highest layer. 
   * It provides a main entry point.
   * All other parts should be booted by it.
* The **Model** (as `model` package) is the core part of the system.
   * It implements several **Entities** (as `entities` package). 
     * The entities include:
         * **Files** (as `File` interface)
         * **Directories** (as `Directory` class)
         * **Documents** (as `Document` class)
         * **Criteria** (as `Criterion` class)
         * **Virtual Disks** (as `VDisk` class)
     * In the view of other parts, entities are only piles of read-only information. For example, a `File` is just a file, and it cannot control is organization on the virtual disk. 
     * Constructors like `new VDisk(...)` and `new Document(...)` are allowed, but nominally, they must call the File System to take effect.
   * It has another part to maintain the application's state and process all file system requests, defined as the **File System** (as `FileSystem` class). 
     * Any attempts to modify the Entities or find their relational context [must] request the File System first. The attempts include:
       * Create new files (requires allocating disk space)
       * Remove files (requires deallocating disk space)
       * Rename files (requires disk read & write)
       * Modify documents (also requires disk read & write)
       * Find the parent of files (requires disk read)
       * Find if a file exists in the directory (also requires disk read)
 * The **Controller** (as `Controller` class) is the application's controller. 
   * It handles user input, hence it is partially in the Console. 
   * It requests operations (as `Operation` class) from the Service, and then executes them. 
* The **Service** (as `service` and `operationservice` package) converts the commands into operations and provides simple and unified interfaces to the Controller, so the Controller can perform the operation in few steps. 
   * It is designed to alleviate the Controller's load, because there are many kinds of operations.
   * It needs to use the interfaces provided by the Model. However, it does not know the implementation details. 
   * It serves as a bridge between the Controller and the Model.
* The **Console** (as `Console` class) is the core of the command line interface. 
   * It includes the entire **View** part, because it handles all information displayed to the user. 
   * It is also a part of the Controller because users need to provide commands through the Console.


### The Cycle with The Enhanced MVC Model
After introducing the enhanced MVC model, we could show you a more detailed running cycle. This is the core of our program.

1. **[Controller, Console]** Receive user's input.
2. **[Controller, Console]** Parse the input.
   * Split the input by spacebars.
   * Process the quote marks and backticks.
3. **[Controller, Service]** Check if the input is valid.
   * Check if the command exists.
   * Check if the parameters of the command are correct.
   * If all correct, save the command's parameter information.
4. **[Service]** Convert the input into an operation.
   * Get the application's current state.
   * Generate an `Operation` object using the state information and the parameter information.
5. **[Controller]** Execute the operation.
   * Simply call the `exec()` method of the `Operation` object.
6. **[View, Console]** Present the result back to user.
   * If the operation executes successfully, present the success information.
   * Or, present the error information.
7. Go back to 1.


### High Cohesion and Low Coupling
We introduce the principle of **high cohesion and low coupling**. In our program, each part having clear responsibilities, and the interfaces to other parts are strictly limited.

* The Model is the core part, it does not depend on any other parts. The Model's interfaces to other parts include:
  * **[For Service and Controller]** The reference to the File System. 
    ```java
    // It is allowed to use such statements in the Service and Controller part.
    FileSystem fs = ...;
    ```
  * **[Only for Service]** File system interfaces (such as retrieving files, creating files / allocating space, deleting files / deallocating space, and other file management operations).
    ```java
    // These interfaces are provided, but only for the Service part:
    
    /* Methods involving virtual disk mounting and ejecting */
    // public void ejectVDisk(); // currently unavailable
    public void mountVDisk(VDisk vDisk);
    public void releaseResource();
    
    /* Public getters */
    public Directory getRootDirectory();
    public Directory getWorkingDirectory();
    public String getWorkingDirectoryPathSafely();
    
    /* Public setter */
    public void setNewWorkingDirectory(Directory newWorkingDirectory);
    
    /* File system responsibilities */
    public TreeMap<String, File> getAllFiles(Directory directory);
    public File findFile(Directory directory, String name);
    public File getParent(File file);
    public void storeFile(File file);
    public void removeFile(File file);
    public void renameFile(File file, String newName);
    public void modifyDocument(File file, String newContent);
    
    /* Methods related to the criteria */
    public void addCriterion(Criterion criterion);
    public TreeMap<String, Criterion> getAllCriteria();
    public Criterion findCriterion(String name);
    public void removeCriterion(Criterion criterion);
    ```
  * **[Only for Service and Read-only]** The Entities.
    ```java
    // Virtual disk interfaces: NONE
    
    // File interfaces:
    public String getName();
    public String getFullname();
    public long getSize();
    public String getPath();
    
    // Additional interfaces in Document:
    public Document(String name, String type, String content, Directory parent);
    public String getType();
    public String getContent();
    
    // Hence in the view of other parts, 
    // Files and Directories are just piles read-only information, 
    // looks like the abstract structures provided by the file system.
    ```
  * **[Exception]** A general exception class (as `ModelException` class).
* The Service encapsulates the second and third interfaces of the Model, greatly reducing the coupling between the Controller and the Model. The Service provides the following interfaces to the Controller:
  * **[For Controller]** An `exec()` method to execute operations.
    ```java
    // You may see the following statements in the Controller:
    String result = operation.exec();
    console.printInformation(result);
    ```
  * **[Exception]** Two exception classes (as `InvalidCommandException` exception and `OperationCannotExecuteException` exception), indicating that user commands from the Controller are invalid or operations cannot be executed.
* It is clear that the Controller hardly depends on the Model; it only needs a reference to the file system. The Controller slightly depends on the Service, but it is minimal.
* The View does not involve the Model or Service. It simply outputs the content as requested by the Controller.

Noted that to ensure functionalities, the minimal dependency and coupling must exist between parts.

### High-Fidelity Simulaiton
As we mentioned before, we try our best to simulate as much as possible. In our discussion above, it is clear that **the Controller, Service, and View fully aligns with the real file systems**. However, the entities part of the Model needs clarification.
* Our program does not maintain a space to simulate the actual storage of a virtual disk. Instead, our virtual disks store files by storing references. We have not simulated complex mechanisms, such as virtual page tables and space maps. 
* And in order to keep code simple, some operations involving the disk read & write are not fully implemented in the `FileSystem` class. Instead, they might be in the `File` interface and `Directory` class.  
* Luckily, we do not allow the files to operate tasks involving disk read & write in other parts, which greatly simulates the reality where real disk read & writes exist. 
* We introduced a `@ModelInternalUse` annotation to indicate that, to those things whose details are not aligned to the real world, and/or could cause serious issues if exposed to other parts, **they should remain internal in the Model.** The method with this annotation will also have a `__INTERNAL__` prefix. For example, 
    ```java
    public void storeFile(File file) throws ... {
        checkAddressSpace(file);
    
        if (file.getSize() > currentVDisk.getFreeSpace()) {
            throw new ...;
        }
    
        // The following should be the actual disk storage logic.
        Directory parent = (Directory)file.__INTERNAL__getParent();
        if (parent.__INTERNAL__existsName(file.getName())) {
            throw new ...;
        }
        parent.__INTERNAL__add(file);
    }
    ```
* **Overall, in the view of other parts,**  
  * The `FileSystem`  class manages all responsibilities of the File System, 
  * The Entities just represents entities themselves, along with their information read from the virtual disk. More specifically,
    * The Entities themselves do not participate in any disk reading & writing activities.
    * The Entities look like the structures abstracted from the file system.


This is the best we can do, and we believe that not to over-simulate is compatible with the project's expectations. 


## General Implementations
### Class list
Below, we have listed all the classes of our application.
* The Application
  * `Application` - The Application
* The Model
  * The Entities
    * `File` - Files
    * `Directories` - Directories
    * `Document` - Documents
    * Criteria
      * `Criterion` - Criteria
      * `CriterionFactory` - The Criterion Factory, used to generate `Criterion` objects
      * `IsDocument` - The `IsDocument` criterion
      * `NameCriterion` - The simple criterion with `attrType == name`
      * `TypeCriterion` - The simple criterion with `attrType == type`
      * `SizeCriterion` - The simple criterion with `attrType == size`
      * `NegationCriterion` - The negation criterion
      * `LogicAndCriterion` - The binary criterion with `logicOp == &&`
      * `LogicOrCriterion` - The binary criterion with `logicOp == ||`
    * `VDisk` - Virtual Disks
  * The File System
    * `FileSystem` - The File System
* The Controller
  * `Controller` - The Controller
* The Service
  * `OperationFactory` - The Operation Factory, used to generate `Operation` objects
  * `OperationRecord` - The Operation Record System, used to record undoable operations
  * Operations:
    * `Operation` - The general operations
    * `UndoableOperation` - The undoable operations
    * `FileUnrelatedUndoableOperation` - The undoable operations which are not related to files.
    * `NewDisk` - The operation of `newDisk` command
    * `NewDir` - The operation of `newDir` command
    * `NewDoc` - The operation of `newDoc` command
    * `ViewContent` - The operation of `view` command
    * `Remove` - The operation of `delete` command, the inverse operation of `PutBack`, `NewDir` and `NewDoc`
    * `PutBack` - The inverse operation of `Remove`
    * `ModifyContent` - The operation of `modify` command
    * `Rename` - The operation of `rename` command
    * `ChangeDir` - The operation of `changeDir` command
    * `List` - The operation of `list` command
    * `RList` - The operation of `rList` command
    * `NewSimpleCri` - The operation of `newSimpleCri` command
    * `NewNegation` - The operation of `newNegation` command
    * `NewBinaryCri` - The operation of `newBinaryCri` command
    * `RemoveCri` - The operation of `removeCri` command, the inverse operation of `PutBackCri`, `NewSimpleCri`, `NewNegation` and `NewBinaryCri`
    * `PutBackCri` - The inverse operation of `RemoveCri`
    * `PrintAllCriteria` - The operation of `printAllCriteria` command
    * `Search` - The operation of `search` command
    * `RSearch` - The operation of `rSearch` command
    * `Save` - The operation of `save` command
    * `Load` - The operation of `load` command
    * `Quit` - The operation of `quit` command
* Console
  * `Console` - The Console

### Information Flows
We have completed outlining our overall structure, but it still may not be easy to understand the information flow.

We will further refine the cycle introduced in section 2.2.4. This time, we will specifically associate with the classes.

1. `Console` receives user's input.
2. `Console` parses the input, then returns it back to `Controller`.
3. `Controller` gives the parsed input to `CriterionFactory`.
4. `CriterionFactory` converts the input into an `Operation` object, and returns it back to `Controller`.
   * The `Operation` object corresponds to the command.
   * The `Operation` object includes the application’s state information and the command parameter information.
5. `Controller` call the `exec()` method of the `Operation` object to execute the operation.
6. The `Operation` object would call the methods in the File System. Once its execution is finished, it returns the successful result back to `Controller`.
7. `Controller` provides the result to `Console`.
8. `Console` presents the result to the user.
9. Go back to 1.

### The `@ModelInternalUse` Annotation
This annotation indicates that a method should only be used within the Model part (i.e., the Entities and the File System), and should not be exposed to other parts.

The main reasons for using the `@ModelInternalUse` annotation include:
1. Some complex mechanisms, such as the actual disk storage logic, are not simulated. It is best not to expose such unsimulated things to other parts, and related methods should add this annotation and only be used internally. In addition, even with complete simulation, such methods should keep this annotation, refer to the second and third points.

2. Many methods are safely used within the Model part, but exposing it to other parts could lead to risks, such as unpredictable data changes. These methods should add this annotation.

3. Many methods are intended to be controlled and managed by the file system, and they are designed not to be exposed to other parts. These methods should add this annotation.

It is required to check the contexts and perform necessary error checking before using methods with this annotation.


### Implementation: Application Entry
We use a set of simple commands to start the application:

```java
FileSystem fs = new FileSystem();
Console console = new Console();
Controller controller = new Controller(fs, console);
Application app = new Application(fs, console, controller);
```

### Implementation: Controller
We focus on how the Controller repeatedly executes the Cycle. It is clear that this is the function of the method work(). Here we provide the general logic of work(), and important codes are highlighted:

```java
private void work() {
    while (true) {
        try {
            String[] command = console.getNextCommand(...);
            Operation operation = operationFactory.createOperation(...);

            String result = operation.exec();
            console.printInformation(result);

            if (operation instanceof UndoableOperation) {
                operationRecord.record((UndoableOperation) operation);
            }
        } catch (...) {
            console.printErrorStream(e);
        } 
    }
}
```

### Implementation: The Operation Factory
The Operation Factory is used to generate Operation objects. As mentioned, it needs to store the application’s state information and the command’s parameter information. The only method in this class, and the most important one, is createOperation(). Its general logic is:

```java
public Operation createOperation(FileSystem fs, OperationRecord operationRecord, String[] command) {
    switch (command[0]) {
        case "newDisk":
            return new NewDisk(fs, operationRecord, command);
        case "newDoc":
            return new NewDoc(fs, command);
        ...
    }
}
```

### Implementation: The Undo and Redo Operations
To implement undo and redo features, we need some definitions. These definitions are all integrated into an enum type, called `OperationType`.

* Each undoable operation has three types:
  * `REGULAR`: Neither an undo nor a redo version of another operation.
  * `UNDO`: The undo version of another operation.
  * `REDO`: The redo version of another operation.
* There are state transitions between operations. If a type results from the state transition of another type, it is called the _inverse type_ of that type.
  * The inverse type of `REGULAR` operation is of type `UNDO`.
  * The inverse type of `UNDO` operation is of type `REDO`.
  * The inverse type of `REDO` operation is of type `UNDO`.
* The enum type `OperationType` provides a `getInverseType()` method.

Then we need to find the inverse operation for each undoable operation. Since `UndoableOperation` interface provides a `getInverseOperation()` method, the following part is very simple.

Each time undo or redo command is called, the Operation Factory takes a similar approach, returns the _inverse operation_ of the _latest_ executed operation.
```java
case "undo": return operationRecord.popForUndo() .getInverseOperation();
case "redo": return operationRecord.popForRedo().getInverseOperation();
```

### Implementation: Undoes and Redoes and the Operation Record
When an undoable operation is executed, the Controller will attempt to record it:
```java
if (operation instanceof UndoableOperation) {
    operationRecord.record((UndoableOperation) operation);
}
```
So the operation is given to an Operation Record for further processing. The Operation Record is a structure used to record undoable operations. Basically, it maintains two stacks:
1. An undo stack for recording `REGULAR` and `REDO` operations.
2. A redo stack for recording `UNDO` operations.

Then it comes to the recording logic, which is handled by the `record()` method:
```java
public void record(UndoableOperation o) {
    if (!o.isUndoOperation()) { // type is REGULAR or REDO
        undo.push(o);
        if (!o.isRedoOperation()) { // type is REGULAR
            redo.clear();
        }
    } else { // type is UNDO
        redo.push(o);
    }
}
```

Additionally, let us briefly mention `UndoableOperation` and `FileUnrelatedUndoableOperation`. These interfaces are designed for certain special operations (like `Load` and `Save`, or `LoadCri` and `SaveCri`) where it is necessary to clear some operation records but not all. The separation between these two interfaces makes the application possible to clear part of the records. 


### Implementation: Operation Execution and the Interfaces of the Model
In this section, we introduce the core of the Service part: the `exec()` method of the Operation object, which executes the operation.

Overall, the implementation details of each operation will be provided in later. Now we only provide an example: the `NewDir` operation. Recall that `NewDir` corresponds to the `newDir` command, which creates a new directory.

```java
@Override
public String exec() throws OperationCannotExecuteException {
    try {
        directory = new Directory(name, fs.getWorkingDirectory());
        fs.storeFile(directory);
        return ...
    } catch (ModelException e) {
        throw new OperationCannotExecuteException(e.getMessage());
    }
}
```

Note that `storeFile(File)` is a method of the File System. As part of the Service part, NewDir should not focus on its implementation.


### Implementation: File System Methods
In previous sections, we listed all the File System interfaces, and,

In previous sections, we introduced the implementation details of those interfaces and explained how we simulate a real file system as closely as possible.

We will not elaborate more here.


### Implementation: Read-Only Entities
In previous sections, we emphasized that for the Service part and other parts, entities including virtual disks, files, directories and documents are read-only and appear abstracted by the File System. These rules are set to better simulate a real file system.

These rules also indicates that, Entities cannot modify themselves or attempt to do tasks that are the responsibility of the File System. In previous sections, we listed the tasks that only the File System can do.

We will not elaborate more here.


### Implementation: Other Implementations
In previous sections, we only introduced some of our core components. These components are innovative, but understanding them alone is not enough.

We list some other key components and provide brief introductions.
* Implementations of the Entities. In previous sections, we showed the interfaces they provide to the File Systm as well as the other parts, and we showed the restrictions and requirements for their interaction with other parts, ensuring they seem abstracted by the File System.
* Implementations of the Criteria. The File System maintains a criteria list to record the saved criteria. Each criterion has a `check(File)` method to check if a file meets the criterion.
* Implementation of the Criterion Factory. In previous sections, we explained the use of the Criterion Factory. In fact, it is the same as the Operation Factory. Given some parameters, it generates the corresponding `Criterion` object. We also showed the interfaces it provides.


### Exceptions
* Model
  * Model external exception
    * `ModelException` - The general exception class of the Model part, and other parts shall catch this exception. All exceptions to the Model part should extend this exception. 
  * Model internal exceptions (all extends `ModelException`)
    * `CannotDeleteCriterionException` - Indicate that the criterion cannot be deleted. Generally, this is because other criteria depend on this criterion.
    * `CannotEditRootDirectoryException` - Indicate that the root directory cannot be edited. 
    * `CannotInitializeFileException` - Indicate that the file cannot be initialized. This is most likely because the provided parameters do not meet the system requirements.
    * `CannotInitializeVDiskException` - Indicate that the virtual disk cannot be initialized. This is most likely because the provided parameters do not meet the system requirements.
    * `CriterionNotExistsException` - Indicate that the criterion with the specific name does not exist in the criterion list.
    * `DuplicateCriterionNameException` - Indicate that the criterion with the specific name does not exist in the criterion list.
    * `DuplicateFilenameException` - Indicate that there is another file in the directory with the same name. The instruction document specifies that, even a directory and a file, or two files of different types, cannot share the same name.
    * `FileNotExistsException` - Indicate that the file does not exist in the directory.
    * `InvalidCriterionParametersException` - Indicate that the parameter is invalid for creating a criterion.
    * `LocalFileSystemException` - Indicate that the there are error(s) from the local file system, which prevents the virtual disk and criteria from being loaded or saved.
    * `NoMountedDiskOrWorkingDirectoryException` - Indicate that the command requires a mounted virtual disk and an available working directory, while there is not.
    * `VDiskOutOfSpaceException` - Indicate that the virtual disk is out of space to store the new file.
    * `WrongAddressSpaceException` - Indicate that the directory does not belong to the virtual disk. From a simulation perspective, this is an error related to address mapping.
* Service
  * `InvalidCommandException` - Indicate that the user provided command is invalid. This is also the general exception in the Service part.
  * `OperationCannotExecuteException` - Indicate that the operation cannot be executed due to various reasons.
* The global exception
  * `CVFS_Exception` - The most general exception class of this CVFS application. All other exceptions in the application should extend this exception.