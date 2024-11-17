package hk.edu.polyu.comp.comp2021.cvfs.model.entities.file;

import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.CannotInitializeFileException;
import org.junit.Test;

import static org.junit.Assert.*;

public class DocumentTest {
    Document document;
    {
        try {
            document = new Document("Main", "java", "public class Main { }", new Directory(true));
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetName() {
        assertEquals("Main", document.getName());
    }

    @Test
    public void testGetFullName() {
        assertEquals("Main.java", document.getFullname());
    }

    @Test
    public void testGetSize() {
        assertTrue(document.getSize() > 0);
    }

    @Test
    public void testGetType() {
        assertEquals("java", document.getType());
    }

    @Test
    public void testGetContent() {
        assertEquals("public class Main { }", document.getContent());
    }

    @Test
    public void testGetPath() {
        assertEquals("$:Main.java", document.getPath());
    }

    @Test
    public void testParent() {
        assertTrue(((Directory)(document.__INTERNAL__getParent())).isRootDirectory());
    }

    @Test
    public void testRoot() {
        assertEquals("$", document.__INTERNAL__getRoot().getName());
    }

    @Test
    public void testSetName() {
        document.__INTERNAL__setName("Main2");
        assertEquals("Main2", document.getName());
    }

    @Test
    public void testSetContent() {
        document.__INTERNAL__setContent("public class Main { private int a; }");
        assertEquals("public class Main { private int a; }", document.getContent());
    }

    @Test
    public void testInvalidDocument() {
        Document anotherDocument = null;
        try {
            anotherDocument = new Document("invalid-name", "java", "...", new Directory(true));
        } catch (CannotInitializeFileException e) {
            assertNull(anotherDocument);
        }
    }
}