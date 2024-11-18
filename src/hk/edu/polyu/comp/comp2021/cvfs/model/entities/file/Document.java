package hk.edu.polyu.comp.comp2021.cvfs.model.entities.file;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.ModelInternalUse;
import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.CannotInitializeFileException;

// import java.io.Serial;
import java.util.Arrays;
import java.util.List;

/**
 * <h3>The {@code Document} Class</h3>
 * This class represents the documents.
 */
public final class Document implements File {
    // @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The size of an empty document. According to the instruction document, this is 40.
     */
    public static final long EMPTY_DOCUMENT_SIZE = 40;

    /**
     * The valid types of a document.
     */
    private static final List<String> validTypes = Arrays.asList("txt", "java", "html", "css");

    /**
     * The name of the doucment.
     */
    private String name;

    /**
     * The size of the document.
     */
    private long size;

    /**
     * The type of the document, in a string form.
     */
    private final String type;

    /**
     * The content of the document.
     */
    private String content;

    /**
     * The reference to the parent of the document. Noted this should always be a directory, but the actual type is {@code File} here to reduce dependencies.
     */
    private File parent;

    /**
     * The full path to the document. For example, {@code $:main:thisTxtDocument.txt}.
     */
    private String path;


    /**
     * Constructs a new document with its name, its type and its content.
     * @param name the name of the new document
     * @param type the type of the new document, according to the instruction document, this can be only {@code txt}, {@code java}, {@code html} or {@code css}.
     * @param content the content of the new document.
     * @param parent the parent of the document.
     * @throws CannotInitializeFileException if any errors are met.
     */
    public Document(String name, String type, String content, Directory parent) throws CannotInitializeFileException {
        if ((name == null) || !name.matches("[a-zA-Z0-9]+") || !validTypes.contains(type) || (parent == null)) {
            throw new CannotInitializeFileException("Invalid parameter(s) for initializing the document.");
        }
        
        this.name = name;
        this.type = type;
        this.content = content;
        this.size = EMPTY_DOCUMENT_SIZE + content.length() * 2L;
        this.parent = parent;
        this.path = parent.getPath() + ":" + getFullname();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFullname() {
        return name + "." + type;
    }

    @Override
    public long getSize() {
        return size;
    }

    /**
     * Get the type of the document.
     * @return type of the document.
     */
    public String getType() {
        return type;
    }

    /**
     * Get the content of the document.
     * @return the content of the document.
     */
    public String getContent() {
        return content;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    @ModelInternalUse
    public File __INTERNAL__getParent() {
        return parent;
    }

    @Override
    @ModelInternalUse
    public File __INTERNAL__getRoot() {
        return parent.__INTERNAL__getRoot();
    }

    @Override
    @ModelInternalUse
    public void __INTERNAL__setName(String newName) {
        name = newName;
    }

    /**
     * Set a new content to the document.
     * @param newContent new content to set.
     */
    @ModelInternalUse
    public void __INTERNAL__setContent(String newContent) {
        content = newContent;
        long oldSize = size;
        size = EMPTY_DOCUMENT_SIZE + content.length() * 2L;
        ((Directory)parent).__INTERNAL__updateSize(size - oldSize);
    }
}
