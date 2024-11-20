package hk.edu.polyu.comp.comp2021.cvfs.model.entities.file;

import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.CannotInitializeFileException;
import org.junit.Test;

import static org.junit.Assert.*;

public class DirectoryTest {
    @Test
    public void testGetName() {
        try {
            Directory directory = new Directory("Main", new Directory(true));
            Document document = new Document("Main", "java", "public class Main { }", directory);
            directory.__INTERNAL__add(document);
            assertEquals("Main", directory.getName());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetFullName() {
        try {
            Directory directory = new Directory("Main", new Directory(true));
            Document document = new Document("Main", "java", "public class Main { }", directory);
            directory.__INTERNAL__add(document);
            assertEquals("Main", directory.getFullname());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetSize() {
        try {
            Directory directory = new Directory("Main", new Directory(true));
            Document document = new Document("Main", "java", "public class Main { }", directory);
            directory.__INTERNAL__add(document);
            assertEquals(40 + 40 + 21 * 2, directory.getSize());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetPath() {
        try {
            Directory directory = new Directory("Main", new Directory(true));
            Document document = new Document("Main", "java", "public class Main { }", directory);
            directory.__INTERNAL__add(document);
            assertEquals("$:Main", directory.getPath());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testParent() {
        try {
            Directory directory = new Directory("Main", new Directory(true));
            Document document = new Document("Main", "java", "public class Main { }", directory);
            directory.__INTERNAL__add(document);
            assertTrue(((Directory)(directory.__INTERNAL__getParent())).isRootDirectory());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testRoot() {
        try {
            Directory directory = new Directory("Main", new Directory(true));
            Document document = new Document("Main", "java", "public class Main { }", directory);
            directory.__INTERNAL__add(document);
            assertEquals("$", directory.__INTERNAL__getRoot().getName());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testSetName() {
        try {
            Directory directory = new Directory("Main", new Directory(true));
            Document document = new Document("Main", "java", "public class Main { }", directory);
            directory.__INTERNAL__add(document);
            directory.__INTERNAL__setName("Main2");
            assertEquals("Main2", directory.getName());
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testExistsName() {
        try {
            Directory directory = new Directory("Main", new Directory(true));
            Document document = new Document("Main", "java", "public class Main { }", directory);
            directory.__INTERNAL__add(document);
            assertTrue(directory.__INTERNAL__existsName("Main"));
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testFindFile() {
        try {
            Directory directory = new Directory("Main", new Directory(true));
            Document document = new Document("Main", "java", "public class Main { }", directory);
            directory.__INTERNAL__add(document);
            assertNotNull(directory.__INTERNAL__findFile("Main"));
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testDeleteFile() {
        try {
            Directory directory = new Directory("Main", new Directory(true));
            Document document = new Document("Main", "java", "public class Main { }", directory);
            directory.__INTERNAL__add(document);
            directory.__INTERNAL__delete(document);
            assertNull(directory.__INTERNAL__findFile("Main"));
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testInvalidDocument() {
        Directory directory = null;
        try {
            directory = new Directory("invalid-name", new Directory(true));
        } catch (CannotInitializeFileException e) {
            assertNull(directory);
        }
    }
}