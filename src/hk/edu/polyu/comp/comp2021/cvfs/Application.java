package hk.edu.polyu.comp.comp2021.cvfs;

import hk.edu.polyu.comp.comp2021.cvfs.cli.Console;
import hk.edu.polyu.comp.comp2021.cvfs.controller.Controller;
import hk.edu.polyu.comp.comp2021.cvfs.model.filesystem.FileSystem;

/**
 * <h2>The {@code Application} Class</h2>
 * This is the main class (including the entry point) of the CVFS application.
 * <p>
 * Please run this class to start the application.
 */
public final class Application {
    /**
     * The file system of the application. According to the MVC design pattern, it is part of the Model.
     */
    private final FileSystem fs;

    /**
     * The console of the application. According to the MVC design pattern, it is part of the View.
     */
    private final Console console;

    /**
     * The controller of the application. According to the MVC design pattern, the controller should boot first.
     */
    private final Controller controller;


    /**
     * Construct a new Application.
     * @param fs the reference to the file system.
     * @param console the reference to the console.
     * @param controller the reference to the controller.
     */
    public Application(FileSystem fs, Console console, Controller controller) {
        this.fs = fs;
        this.console = console;
        this.controller = controller;
    }

    /**
     * The main method of booting the system.
     */
    public void boot() {
        controller.boot();
    }

    /**
     * The entry of the CVFS Application.
     * @param args the arguments for the main entry. Should not use this.
     */
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        Console console = new Console();
        Controller controller = new Controller(fs, console);

        Application app = new Application(fs, console, controller);

        app.boot();
    }
}

