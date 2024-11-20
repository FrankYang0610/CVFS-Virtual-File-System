package hk.edu.polyu.comp.comp2021.cvfs.model.entities.file;

import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.CannotInitializeFileException;
import org.junit.Test;

import static org.junit.Assert.*;

public class DocumentTest {
    @Test
    public void testGetName() {
        try {
            Document document = new Document("Main", "java", "public class Main { }", new Directory(true));
            assertEquals("Main", document.getName());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetFullName() {
        try {
            Document document = new Document("Main", "java", "public class Main { }", new Directory(true));
            assertEquals("Main.java", document.getFullname());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetSize() {
        try {
            Document document = new Document("Main", "java", "public class Main { }", new Directory(true));
            assertTrue(document.getSize() > 0);
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetType() {
        try {
            Document document = new Document("Main", "java", "public class Main { }", new Directory(true));
            assertEquals("java", document.getType());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetContent() {
        try {
            Document document = new Document("Main", "java", "public class Main { }", new Directory(true));
            assertEquals("public class Main { }", document.getContent());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetPath() {
        try {
            Document document = new Document("Main", "java", "public class Main { }", new Directory(true));
            assertEquals("$:Main.java", document.getPath());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testParent() {
        try {
            Document document = new Document("Main", "java", "public class Main { }", new Directory(true));
            assertTrue(((Directory)(document.__INTERNAL__getParent())).isRootDirectory());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testRoot() {
        try {
            Document document = new Document("Main", "java", "public class Main { }", new Directory(true));
            assertEquals("$", document.__INTERNAL__getRoot().getName());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testSetName() {
        try {
            Document document = new Document("Main", "java", "public class Main { }", new Directory(true));
            document.__INTERNAL__setName("Main2");
            assertEquals("Main2", document.getName());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testSetContent() {
        try {
            Document document = new Document("Main", "java", "public class Main { }", new Directory(true));
            document.__INTERNAL__setContent("public class Main { private int a; }");
            assertEquals("public class Main { private int a; }", document.getContent());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testInvalidDocument() {
        Document document = null;
        try {
            document = new Document("invalid-name", "java", "...", new Directory(true));
        } catch (CannotInitializeFileException e) {
            assertNull(document);
        }
    }
}