package hk.edu.polyu.comp.comp2021.cvfs.model.entities.file;

import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.CannotInitializeFileException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {
    Document document;
    {
        try {
            document = new Document("Main", "java", "public class Main { }", new Directory(true));
        } catch (CannotInitializeFileException ignored) {}
    }


    @Test
    void testGetName() {
        assertEquals("Main", document.getName());
    }

    @Test
    void testGetFullName() {
        assertEquals("Main.java", document.getFullname());
    }

    @Test
    void testGetSize() {
        assertTrue(document.getSize() > 0);
    }

    @Test
    void testGetType() {
        assertEquals("java", document.getType());
    }

    @Test
    void testGetContent() {
        assertEquals("public class Main { }", document.getContent());
    }

    @Test
    void testGetPath() {
        assertEquals("$:Main.java", document.getPath());
    }

    @Test
    void testParent() {
        assertTrue(((Directory)(document.__INTERNAL__getParent())).isRootDirectory());
    }

    @Test
    void testRoot() {
        assertEquals("$", document.__INTERNAL__getRoot().getName());
    }

    @Test
    void testSetName() {
        document.__INTERNAL__setName("Main2");
        assertEquals("Main2", document.getName());
    }

    @Test
    void testSetContent() {
        document.__INTERNAL__setContent("public class Main { private int a; }");
        assertEquals("public class Main { private int a; }", document.getContent());
    }

    @Test
    void testInvalidDocument() {
        Document anotherDocument = null;
        try {
            anotherDocument = new Document("invalid-name", "java", "...", new Directory(true));
        } catch (CannotInitializeFileException e) {
            assertNull(anotherDocument);
        }
    }
}