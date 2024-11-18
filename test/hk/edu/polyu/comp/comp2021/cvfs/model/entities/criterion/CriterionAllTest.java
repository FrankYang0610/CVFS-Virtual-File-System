package hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion.IsDocument;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion.SizeCriterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.CannotInitializeFileException;
import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.InvalidCriterionParameterException;
import org.junit.Test;

import static org.junit.Assert.*;

public class CriterionAllTest {
    CriterionFactory criterionFactory = new CriterionFactory();

    @Test
    public void testGetNameCriterion() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("aa", "name", "contains", "\"Main\"");
            assertEquals("aa", criterion.getName());
            assertEquals("aa: name contains \"Main\"", criterion.toString());

            criterion.__INTERNAL__increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.__INTERNAL__decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertTrue(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    public void testInvalidNameCriterion1() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("aa", "name", "contains", "Main");
            assertEquals("aa: name contains \"Main\"", criterion.toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    public void testInvalidNameCriterion2() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("aa", "name", "contain", "Main");
            assertEquals("aa: name contains \"Main\"", criterion.toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    public void testGetTypeCriterion() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("bb", "type", "equals", "\"java\"");
            assertEquals("bb", criterion.getName());
            assertEquals("bb: type equals \"java\"", criterion.toString());

            criterion.__INTERNAL__increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.__INTERNAL__decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    public void testInvalidTypeCriterion1() {
        try {
            assertEquals("bb: type equals java", criterionFactory.createSimpleCriterion("bb", "type", "equals", "java").toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    public void testInvalidTypeCriterion2() {
        try {
            assertEquals("bb: type equals java", criterionFactory.createSimpleCriterion("bb", "type", "equal", "java").toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    public void testGetSizeCriterion1() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("cc", "size", ">", "60");
            assertEquals("cc", criterion.getName());
            assertEquals("cc: size > 60", criterion.toString());

            criterion.__INTERNAL__increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.__INTERNAL__decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetSizeCriterion2() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("cc", "size", "<", "60");
            assertEquals("cc", criterion.getName());
            assertEquals("cc: size < 60", criterion.toString());

            criterion.__INTERNAL__increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.__INTERNAL__decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertTrue(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetSizeCriterion3() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("cc", "size", ">=", "60");
            assertEquals("cc", criterion.getName());
            assertEquals("cc: size >= 60", criterion.toString());

            criterion.__INTERNAL__increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.__INTERNAL__decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetSizeCriterion4() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("cc", "size", "<=", "60");
            assertEquals("cc", criterion.getName());
            assertEquals("cc: size <= 60", criterion.toString());

            criterion.__INTERNAL__increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.__INTERNAL__decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertTrue(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetSizeCriterion5() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("cc", "size", "==", "60");
            assertEquals("cc", criterion.getName());
            assertEquals("cc: size == 60", criterion.toString());

            criterion.__INTERNAL__increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.__INTERNAL__decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetSizeCriterion6() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("cc", "size", "!=", "60");
            assertEquals("cc", criterion.getName());
            assertEquals("cc: size != 60", criterion.toString());

            criterion.__INTERNAL__increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.__INTERNAL__decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertTrue(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetSizeCriterion7() {
        try {
            Criterion criterion1 = new SizeCriterion("cc", "<", 60);
            Criterion criterion2 = new SizeCriterion("cc", ">", 60);
            Criterion criterion3 = new SizeCriterion("cc", "<=", 60);
            Criterion criterion4 = new SizeCriterion("cc", ">=", 60);
            Criterion criterion5 = new SizeCriterion("cc", "==", 60);
            Criterion criterion6 = new SizeCriterion("cc", "!=", 60);
            Criterion criterion7 = new SizeCriterion("cc", "?t?", 60);

            Directory directory = new Directory("Main", new Directory(true));
            assertTrue(criterion1.check(directory));
            assertFalse(criterion2.check(directory));
            assertTrue(criterion3.check(directory));
            assertFalse(criterion4.check(directory));
            assertFalse(criterion5.check(directory));
            assertTrue(criterion6.check(directory));
            assertFalse(criterion7.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    public void testInvalidSizeCriterion1() {
        try {
            assertNull(criterionFactory.createSimpleCriterion("cc", "size", "<>", "60").toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    public void testInvalidSizeCriterion2() {
        try {
            assertNull(criterionFactory.createSimpleCriterion("cc", "size", "<>", "not_a_number").toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    public void testInvalidCriterion() {
        try {
            assertNull(criterionFactory.createSimpleCriterion("cc", "invalidAttr", "<>", "not_a_number"));
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    public void IsDocumentTest() {
        Criterion criterion = new IsDocument();
        assertEquals("IsDocument", criterion.getName());
        assertEquals("IsDocument", criterion.toString());

        criterion.__INTERNAL__increaseReferenceCount();
        assertEquals(1, criterion.getReferenceCount());
        criterion.__INTERNAL__decreaseReferenceCount();
        assertEquals(0, criterion.getReferenceCount());
        assertEquals(0, criterion.getDependencies().length);

        try {
            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetNegationCriterion() {
        try {
            SizeCriterion sizeCriterion = new SizeCriterion("cc", ">", 60);
            Criterion criterion = criterionFactory.createNegationCriterion("dd", sizeCriterion);
            assertEquals("dd", criterion.getName());
            assertEquals("dd: !cc", criterion.toString());

            criterion.__INTERNAL__increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.__INTERNAL__decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(1, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertTrue(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    public void testInvalidNegationCriterion1() {
        try {
            SizeCriterion sizeCriterion = new SizeCriterion("cc", "<>", 60);
            Criterion criterion = criterionFactory.createNegationCriterion("dd", sizeCriterion);
            assertEquals("dd: !cc", criterion.toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    public void testInvalidNegationCriterion2() {
        try {
            SizeCriterion sizeCriterion = new SizeCriterion("cc", ">", 60);
            Criterion criterion = criterionFactory.createNegationCriterion("dd_", sizeCriterion);
            assertEquals("dd: !cc", criterion.toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    public void testGetBinaryAndCriterion() {
        try {
            SizeCriterion sizeCriterion1 = new SizeCriterion("cc", ">", 60);
            SizeCriterion sizeCriterion2 = new SizeCriterion("gg", ">", 70);
            Criterion criterion = criterionFactory.createBinaryCriterion("hh", sizeCriterion1, "&&", sizeCriterion2);
            assertEquals("hh", criterion.getName());
            assertEquals("hh: cc && gg", criterion.toString());

            criterion.__INTERNAL__increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.__INTERNAL__decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(2, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    public void testGetBinaryOrCriterion() {
        try {
            SizeCriterion sizeCriterion1 = new SizeCriterion("cc", ">", 60);
            SizeCriterion sizeCriterion2 = new SizeCriterion("gg", ">", 70);
            Criterion criterion = criterionFactory.createBinaryCriterion("kk", sizeCriterion1, "||", sizeCriterion2);
            assertEquals("kk", criterion.getName());
            assertEquals("kk: cc || gg", criterion.toString());

            criterion.__INTERNAL__increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.__INTERNAL__decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(2, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    public void testInvalidBinaryCriterion() {
        try {
            SizeCriterion sizeCriterion1 = new SizeCriterion("cc", ">", 60);
            SizeCriterion sizeCriterion2 = new SizeCriterion("gg", ">", 70);
            Criterion criterion = criterionFactory.createBinaryCriterion("hh", sizeCriterion1, "^", sizeCriterion2);
            assertEquals("hh", criterion.getName());
            assertEquals("hh: cc && gg", criterion.toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }
}
