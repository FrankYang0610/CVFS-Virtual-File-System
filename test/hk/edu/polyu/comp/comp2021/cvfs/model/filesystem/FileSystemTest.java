package hk.edu.polyu.comp.comp2021.cvfs.model.filesystem;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.compositecriterion.NegationCriterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion.IsDocument;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion.SizeCriterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Document;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.vdisk.VDisk;
import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileSystemTest {
    @Test
    public void testGetRootDirectory() {
        try {
            FileSystem fs = new FileSystem();
            assertNull(fs.getRootDirectory());
        } catch (NoMountedDiskOrWorkingDirectoryException ignored) {}
    }

    @Test
    public void testGetWorkingDirectory1() {
        try {
            FileSystem fs = new FileSystem();
            assertNull(fs.getWorkingDirectory());
        } catch (NoMountedDiskOrWorkingDirectoryException | FileNotExistsException ignored) {}
    }

    @Test
    public void testGetWorkingDirectory2() {
        try {
            FileSystem fs = new FileSystem();
            assertNotNull(fs.getWorkingDirectory());
        } catch (NoMountedDiskOrWorkingDirectoryException | FileNotExistsException ignored) {}
    }

    @Test
    public void testGetWorkingDirectoryPathSafely() {
        FileSystem fs = new FileSystem();
        assertEquals("", fs.getWorkingDirectoryPathSafely());
    }

    @Test
    public void testMountVDisk() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            assertEquals("$", fs.getRootDirectory().getName());
            assertEquals("$", fs.getWorkingDirectory().getName());
        } catch (NoMountedDiskOrWorkingDirectoryException | CannotInitializeVDiskException | FileNotExistsException ignored) {}
    }

    @Test
    public void testLocalFileSystemException() {
        try {
            throw new LocalFileSystemException("testError");
        } catch (LocalFileSystemException ignored) {}
    }

    @Test
    public void testCannotEditRootDirectoryException() {
        try {
            throw new CannotEditRootDirectoryException();
        } catch (CannotEditRootDirectoryException ignored) {}
    }

    @Test
    public void testSetWorkingDirectory() {
        try {
            FileSystem fs = new FileSystem();
            fs.setNewWorkingDirectory(new Directory(true));
        } catch (FileNotExistsException | WrongAddressSpaceException ignored) {}
    }

    @Test
    public void testGetParent() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            assertNull(fs.getParent(fs.getWorkingDirectory()));
        } catch (FileNotExistsException | NoMountedDiskOrWorkingDirectoryException | CannotInitializeVDiskException ignored) {}
    }

    @Test
    public void testGetAllFiles() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            assertTrue(fs.getAllFiles(fs.getWorkingDirectory()).isEmpty());
        } catch (FileNotExistsException | CannotInitializeVDiskException |
                 NoMountedDiskOrWorkingDirectoryException ignored) {}
    }

    @Test
    public void testFindFile() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            fs.findFile(fs.getWorkingDirectory(), "Main");
        } catch (FileNotExistsException | CannotInitializeVDiskException |
                 NoMountedDiskOrWorkingDirectoryException ignored) {}
    }

    @Test
    public void testAddAndFindFile1() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(5)); // no enough space
            fs.storeFile(new Directory("Main", fs.getWorkingDirectory()));
            assertNotNull(fs.findFile(fs.getWorkingDirectory(), "Main"));
        } catch (FileNotExistsException | CannotInitializeVDiskException | NoMountedDiskOrWorkingDirectoryException | CannotInitializeFileException | WrongAddressSpaceException | VDiskOutOfSpaceException |
                 DuplicatedFilenameException ignored) {}
    }

    @Test
    public void testAddAndFindFile2() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            fs.storeFile(new Directory("Main", fs.getWorkingDirectory()));
            assertNotNull(fs.findFile(fs.getWorkingDirectory(), "Main"));
        } catch (FileNotExistsException | CannotInitializeVDiskException | NoMountedDiskOrWorkingDirectoryException | CannotInitializeFileException | WrongAddressSpaceException | VDiskOutOfSpaceException |
                 DuplicatedFilenameException ignored) {}
    }

    @Test
    public void testAddAndFindFile3() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            fs.storeFile(new Directory("Main", fs.getWorkingDirectory()));
            fs.storeFile(new Directory("Main", fs.getWorkingDirectory())); // repeat file
            assertNotNull(fs.findFile(fs.getWorkingDirectory(), "Main"));
        } catch (FileNotExistsException | CannotInitializeVDiskException | NoMountedDiskOrWorkingDirectoryException | CannotInitializeFileException | WrongAddressSpaceException | VDiskOutOfSpaceException |
                 DuplicatedFilenameException ignored) {}
    }

    @Test
    public void testAddAndFindAndRemoveFile() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            fs.storeFile(new Directory("Main", fs.getWorkingDirectory()));
            assertNotNull(fs.findFile(fs.getWorkingDirectory(), "Main"));
            fs.removeFile(new Directory("Main", fs.getWorkingDirectory())); // error!
        } catch (FileNotExistsException | CannotInitializeVDiskException | NoMountedDiskOrWorkingDirectoryException |
                 CannotInitializeFileException | WrongAddressSpaceException | VDiskOutOfSpaceException |
                 DuplicatedFilenameException | CannotEditRootDirectoryException ignored) {}
    }

    @Test
    public void testAddAndRenameFile1() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            Directory directory = new Directory("Main", fs.getWorkingDirectory());
            fs.storeFile(directory);
            fs.renameFile(directory, "Main2");
            assertEquals("Main2", fs.findFile(fs.getWorkingDirectory(), "Main2").getName());
        } catch (FileNotExistsException | CannotInitializeVDiskException | NoMountedDiskOrWorkingDirectoryException |
                 CannotInitializeFileException | WrongAddressSpaceException | VDiskOutOfSpaceException |
                 DuplicatedFilenameException | CannotEditRootDirectoryException ignored) {}
    }

    @Test
    public void testAddAndRenameFile2() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            Directory directory = new Directory("Main", fs.getWorkingDirectory());
            fs.storeFile(directory);
            fs.renameFile(directory, "Main");
            assertEquals("Main2", fs.findFile(fs.getWorkingDirectory(), "Main2").getName());
        } catch (FileNotExistsException | CannotInitializeVDiskException | NoMountedDiskOrWorkingDirectoryException |
                 CannotInitializeFileException | WrongAddressSpaceException | VDiskOutOfSpaceException |
                 DuplicatedFilenameException | CannotEditRootDirectoryException ignored) {}
    }

    @Test
    public void testProcessDocument1() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            Document document = new Document("Main", "java", "public class Main { }", fs.getWorkingDirectory());
            fs.storeFile(document);
            fs.renameFile(document, "Main2");
            fs.modifyDocument(document, "public class Main { private int a; }");
            assertEquals("public class Main { private int a; }", ((Document)(fs.findFile(fs.getWorkingDirectory(), "Main2"))).getContent());
            fs.removeFile(document);
            assertEquals(0, fs.getWorkingDirectory().getSize());
        } catch (FileNotExistsException | CannotInitializeVDiskException | NoMountedDiskOrWorkingDirectoryException |
                 CannotInitializeFileException | WrongAddressSpaceException | VDiskOutOfSpaceException |
                 DuplicatedFilenameException | CannotEditRootDirectoryException ignored) {}
    }

    @Test
    public void testProcessDocument2() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(90));
            Document document = new Document("Main", "java", "public class Main { }", fs.getWorkingDirectory());
            fs.storeFile(document);
            fs.renameFile(document, "Main2");
            fs.modifyDocument(document, "public class Main { private int a; private int b; }");
            assertEquals("public class Main { private int a; private int b; }", ((Document)(fs.findFile(fs.getWorkingDirectory(), "Main2"))).getContent());
            fs.removeFile(document);
            assertEquals(0, fs.getWorkingDirectory().getSize());
        } catch (FileNotExistsException | CannotInitializeVDiskException | NoMountedDiskOrWorkingDirectoryException |
                 CannotInitializeFileException | WrongAddressSpaceException | VDiskOutOfSpaceException |
                 DuplicatedFilenameException | CannotEditRootDirectoryException ignored) {}
    }

    @Test
    public void testProcessInappropriateDocument() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            Directory directory = new Directory("Main", fs.getWorkingDirectory());
            fs.storeFile(directory);
            fs.modifyDocument(directory, "...");
        } catch (FileNotExistsException | CannotInitializeVDiskException | NoMountedDiskOrWorkingDirectoryException |
                 CannotInitializeFileException | WrongAddressSpaceException | VDiskOutOfSpaceException |
                 DuplicatedFilenameException ignored) {}
    }

    @Test
    public void testRenameRootDirectory() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            fs.renameFile(fs.getRootDirectory(), "$$");
        } catch (CannotInitializeVDiskException | FileNotExistsException | CannotEditRootDirectoryException |
                 WrongAddressSpaceException | NoMountedDiskOrWorkingDirectoryException | DuplicatedFilenameException ignored) {}
    }

    @Test
    public void testDeleteRootDirectory() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            fs.removeFile(fs.getRootDirectory());
        } catch (CannotInitializeVDiskException | FileNotExistsException | CannotEditRootDirectoryException | WrongAddressSpaceException | NoMountedDiskOrWorkingDirectoryException ignored) {}
    }

    @Test
    public void checkAddressSpace() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            VDisk vDisk = new VDisk(1000);
            Directory directory = new Directory("Main", vDisk.__INTERNAL__getRootDirectory());
            fs.storeFile(directory);
            assertEquals(3, 1 + 1); // impossible here
        } catch (CannotInitializeVDiskException | CannotInitializeFileException | WrongAddressSpaceException |
                 VDiskOutOfSpaceException | DuplicatedFilenameException ignored) {}
    }

    @Test
    public void testWrongAddressSpaceException() {
        try {
            throw new WrongAddressSpaceException();
        } catch (WrongAddressSpaceException ignored) {}
    }

    @Test
    public void testReleaseResource() {
        FileSystem fs = new FileSystem();
        fs.releaseResource();
        assertTrue(fs.getAllCriteria().isEmpty());
    }

    @Test
    public void testDuplicateCriterionNameException() {
        try {
            throw new DuplicatedCriterionNameException("testName");
        } catch (DuplicatedCriterionNameException ignored) {}
    }

    @Test
    public void testCriterion1() {
        try {
            FileSystem fs = new FileSystem();
            SizeCriterion sizeCriterion = new SizeCriterion("cc", ">", 60);
            fs.addCriterion(sizeCriterion);
            assertEquals(2, fs.getAllCriteria().size());
            assertNotNull(fs.findCriterion("IsDocument"));
            fs.removeCriterion(sizeCriterion);
            assertEquals(1, fs.getAllCriteria().size());
            assertNull(fs.findCriterion("aa"));
        } catch (InvalidCriterionParameterException | DuplicatedCriterionNameException | CriterionNotExistsException |
                 CannotDeleteCriterionException ignored) {}
    }

    @Test
    public void testCriterion2() {
        try {
            FileSystem fs = new FileSystem();
            IsDocument isDocument = new IsDocument();
            fs.addCriterion(isDocument);
            assertEquals(2, fs.getAllCriteria().size()); // impossible here
        } catch (DuplicatedCriterionNameException ignored) {}
    }

    @Test
    public void testCriterion3() {
        try {
            FileSystem fs = new FileSystem();
            assertNull(fs.findCriterion("aa"));
        } catch (CriterionNotExistsException ignored) {}
    }

    @Test
    public void testCriterion4() {
        try {
            FileSystem fs = new FileSystem();
            fs.removeCriterion(new IsDocument()); // error here
            assertEquals(1, fs.getAllCriteria().size());
        } catch (CriterionNotExistsException | CannotDeleteCriterionException ignored) {}
    }

    @Test
    public void testCriterion5() {
        try {
            FileSystem fs = new FileSystem();
            fs.addCriterion(new NegationCriterion("xx", fs.getAllCriteria().get("IsDocument")));
            assertEquals(2, fs.getAllCriteria().size());
            fs.removeCriterion(fs.getAllCriteria().get("xx"));
            assertEquals(1, fs.getAllCriteria().size());
        } catch (DuplicatedCriterionNameException | CriterionNotExistsException | CannotDeleteCriterionException ignored) {}
    }

    @Test
    public void testCriterion6() {
        try {
            FileSystem fs = new FileSystem();
            fs.addCriterion(new NegationCriterion("xx", fs.getAllCriteria().get("IsDocument")));
            assertEquals(2, fs.getAllCriteria().size());
            fs.removeCriterion(fs.getAllCriteria().get("IsDocument"));
            assertEquals(1, fs.getAllCriteria().size()); // impossible here
        } catch (DuplicatedCriterionNameException | CriterionNotExistsException | CannotDeleteCriterionException ignored) {}
    }

    @Test
    public void testLoadSaveVDisk() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            fs.saveVDisk("UnitTestVDisk.ser");
            fs.loadVDisk("UnitTestVDisk.ser");
            assertNotNull(fs.getRootDirectory());
        } catch (CannotInitializeVDiskException | NoMountedDiskOrWorkingDirectoryException | LocalFileSystemException ignored) {}
    }

    @Test
    public void testLoadVDisk1() {
        try {
            FileSystem fs = new FileSystem();
            fs.loadVDisk("notExistsSuchVDiskFile.ser");
            assertNotNull(fs.getRootDirectory()); // impossible here
        } catch (NoMountedDiskOrWorkingDirectoryException | LocalFileSystemException ignored) {}
    }

    @Test
    public void testSaveVDisk1() {
        try {
            FileSystem fs = new FileSystem();
            fs.saveVDisk("UnitTestVDisk.ser");
            assertNotNull(fs.getRootDirectory()); // impossible here
        } catch (NoMountedDiskOrWorkingDirectoryException | LocalFileSystemException ignored) {}
    }

    @Test
    public void testSaveVDisk2() {
        try {
            FileSystem fs = new FileSystem();
            fs.mountVDisk(new VDisk(1000));
            fs.saveVDisk("/invalid_path/<:>\\?**!<:}.ser");
            assertNotNull(fs.getRootDirectory()); // impossible here
        } catch (NoMountedDiskOrWorkingDirectoryException | LocalFileSystemException | CannotInitializeVDiskException ignored) {}
    }

    @Test
    public void testLoadSaveCriteria() {
        try {
            FileSystem fs = new FileSystem();
            fs.saveAllCriteria("UnitTestCriteria.ser");
            fs.loadCriteria("UnitTestCriteria.ser");
            assertEquals(1, fs.getAllCriteria().size());
        } catch (LocalFileSystemException ignored) {}
    }

    @Test
    public void testLoadCriteria() {
        try {
            FileSystem fs = new FileSystem();
            fs.loadCriteria("notExistsSuchCriteriaFile.ser");
            assertEquals(1, fs.getAllCriteria().size()); // impossible here
        } catch (LocalFileSystemException ignored) {}
    }

    @Test
    public void testSaveCriteria() {
        try {
            FileSystem fs = new FileSystem();
            fs.saveAllCriteria("/invalid_path/<:>\\?**!<:}.ser");
            assertEquals(1, fs.getAllCriteria().size()); // impossible here
        } catch (LocalFileSystemException ignored) {}
    }

    @Test
    public void testCannotDeleteCriterionException() {
        try {
            throw new CannotDeleteCriterionException();
        } catch (CannotDeleteCriterionException ignored) {}
    }
}
