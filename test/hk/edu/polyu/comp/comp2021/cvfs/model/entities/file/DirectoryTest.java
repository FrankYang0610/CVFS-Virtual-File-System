package hk.edu.polyu.comp.comp2021.cvfs.model.entities.file;

import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.CannotInitializeFileException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryTest {
    Directory directory;
    {
        try {
            directory = new Directory("Main", new Directory(true));
        } catch (CannotInitializeFileException ignored) {}
    }

    Document document;
    {
        try {
            document = new Document("Main", "java", "public class Main { }", directory);
            directory.__INTERNAL__add(document);
        } catch (CannotInitializeFileException ignored) {}
    }


    @Test
    void testGetName() {
        assertEquals("Main", directory.getName());
    }

    @Test
    void testGetFullName() {
        assertEquals("Main", directory.getFullname());
    }

    @Test
    void testGetSize() {
        assertEquals(40 + 40 + 21 * 2, directory.getSize());
    }

    @Test
    void testGetPath() {
        assertEquals("$:Main", directory.getPath());
    }

    @Test
    void testParent() {
        assertTrue(((Directory)(directory.__INTERNAL__getParent())).isRootDirectory());
    }

    @Test
    void testRoot() {
        assertEquals("$", directory.__INTERNAL__getRoot().getName());
    }

    @Test
    void testSetName() {
        directory.__INTERNAL__setName("Main2");
        assertEquals("Main2", directory.getName());
    }

    @Test
    void testExistsName() {
        assertTrue(directory.__INTERNAL__existsName("Main"));
    }

    @Test
    void testFindFile() {
        assertNotNull(directory.__INTERNAL__findFile("Main"));
    }

    @Test
    void testDeleteFile() {
        directory.__INTERNAL__delete(document);
        assertNull(directory.__INTERNAL__findFile("Main"));
    }

    @Test
    void testInvalidDocument() {
        Directory anotherDirectory = null;
        try {
            anotherDirectory = new Directory("invalid-name", new Directory(true));
        } catch (CannotInitializeFileException e) {
            assertNull(anotherDirectory);
        }
    }
}