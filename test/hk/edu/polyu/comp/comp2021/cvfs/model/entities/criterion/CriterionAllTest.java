package hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion;

import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion.IsDocument;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.criterion.simplecriterion.SizeCriterion;
import hk.edu.polyu.comp.comp2021.cvfs.model.entities.file.Directory;
import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.CannotInitializeFileException;
import hk.edu.polyu.comp.comp2021.cvfs.model.internalexceptions.InvalidCriterionParameterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CriterionAllTest {
    CriterionFactory criterionFactory = new CriterionFactory();

    @Test
    void testGetNameCriterion() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("aa", "name", "contains", "\"Main\"");
            assertEquals("aa", criterion.getName());
            assertEquals("aa: name contains \"Main\"", criterion.toString());

            criterion.increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertTrue(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    void testInvalidNameCriterion1() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("aa", "name", "contains", "Main");
            assertEquals("aa: name contains \"Main\"", criterion.toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    void testInvalidNameCriterion2() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("aa", "name", "contain", "Main");
            assertEquals("aa: name contains \"Main\"", criterion.toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    void testGetTypeCriterion() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("bb", "type", "equals", "\"java\"");
            assertEquals("bb", criterion.getName());
            assertEquals("bb: type equals \"java\"", criterion.toString());

            criterion.increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    void testInvalidTypeCriterion1() {
        try {
            assertEquals("bb: type equals java", criterionFactory.createSimpleCriterion("bb", "type", "equals", "java").toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    void testInvalidTypeCriterion2() {
        try {
            assertEquals("bb: type equals java", criterionFactory.createSimpleCriterion("bb", "type", "equal", "java").toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    void testGetSizeCriterion1() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("cc", "size", ">", "60");
            assertEquals("cc", criterion.getName());
            assertEquals("cc: size > 60", criterion.toString());

            criterion.increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    void testGetSizeCriterion2() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("cc", "size", "<", "60");
            assertEquals("cc", criterion.getName());
            assertEquals("cc: size < 60", criterion.toString());

            criterion.increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertTrue(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    void testGetSizeCriterion3() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("cc", "size", ">=", "60");
            assertEquals("cc", criterion.getName());
            assertEquals("cc: size >= 60", criterion.toString());

            criterion.increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    void testGetSizeCriterion4() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("cc", "size", "<=", "60");
            assertEquals("cc", criterion.getName());
            assertEquals("cc: size <= 60", criterion.toString());

            criterion.increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertTrue(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    void testGetSizeCriterion5() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("cc", "size", "==", "60");
            assertEquals("cc", criterion.getName());
            assertEquals("cc: size == 60", criterion.toString());

            criterion.increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    void testGetSizeCriterion6() {
        try {
            Criterion criterion = criterionFactory.createSimpleCriterion("cc", "size", "!=", "60");
            assertEquals("cc", criterion.getName());
            assertEquals("cc: size != 60", criterion.toString());

            criterion.increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(0, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertTrue(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    void testInvalidSizeCriterion1() {
        try {
            assertEquals("cc: size > 60", criterionFactory.createSimpleCriterion("cc", "size", "<>", "60").toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    void testInvalidSizeCriterion2() {
        try {
            assertEquals("cc: size > 60", criterionFactory.createSimpleCriterion("cc", "size", "<>", "not_a_number").toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    void IsDocumentTest() {
        Criterion criterion = new IsDocument();
        assertEquals("IsDocument", criterion.getName());
        assertEquals("IsDocument", criterion.toString());

        criterion.increaseReferenceCount();
        assertEquals(1, criterion.getReferenceCount());
        criterion.decreaseReferenceCount();
        assertEquals(0, criterion.getReferenceCount());
        assertEquals(0, criterion.getDependencies().length);

        try {
            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (CannotInitializeFileException ignored) {}
    }

    @Test
    void testGetNegationCriterion() {
        try {
            SizeCriterion sizeCriterion = new SizeCriterion("cc", ">", 60);
            Criterion criterion = criterionFactory.createNegationCriterion("dd", sizeCriterion);
            assertEquals("dd", criterion.getName());
            assertEquals("dd: !cc", criterion.toString());

            criterion.increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(1, criterion.getDependencies().length);
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    void testInvalidNegationCriterion1() {
        try {
            SizeCriterion sizeCriterion = new SizeCriterion("cc", "<>", 60);
            Criterion criterion = criterionFactory.createNegationCriterion("dd", sizeCriterion);
            assertEquals("dd: !cc", criterion.toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    void testInvalidNegationCriterion2() {
        try {
            SizeCriterion sizeCriterion = new SizeCriterion("cc", ">", 60);
            Criterion criterion = criterionFactory.createNegationCriterion("dd_", sizeCriterion);
            assertEquals("dd: !cc", criterion.toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }

    @Test
    void testGetBinaryAndCriterion() {
        try {
            SizeCriterion sizeCriterion1 = new SizeCriterion("cc", ">", 60);
            SizeCriterion sizeCriterion2 = new SizeCriterion("gg", ">", 70);
            Criterion criterion = criterionFactory.createBinaryCriterion("hh", sizeCriterion1, "&&", sizeCriterion2);
            assertEquals("hh", criterion.getName());
            assertEquals("hh: cc && gg", criterion.toString());

            criterion.increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(2, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    void testGetBinaryOrCriterion() {
        try {
            SizeCriterion sizeCriterion1 = new SizeCriterion("cc", ">", 60);
            SizeCriterion sizeCriterion2 = new SizeCriterion("gg", ">", 70);
            Criterion criterion = criterionFactory.createBinaryCriterion("kk", sizeCriterion1, "||", sizeCriterion2);
            assertEquals("kk", criterion.getName());
            assertEquals("kk: cc || gg", criterion.toString());

            criterion.increaseReferenceCount();
            assertEquals(1, criterion.getReferenceCount());
            criterion.decreaseReferenceCount();
            assertEquals(0, criterion.getReferenceCount());
            assertEquals(2, criterion.getDependencies().length);

            Directory directory = new Directory("Main", new Directory(true));
            assertFalse(criterion.check(directory));
        } catch (InvalidCriterionParameterException | CannotInitializeFileException ignored) {}
    }

    @Test
    void testInvalidBinaryCriterion() {
        try {
            SizeCriterion sizeCriterion1 = new SizeCriterion("cc", ">", 60);
            SizeCriterion sizeCriterion2 = new SizeCriterion("gg", ">", 70);
            Criterion criterion = criterionFactory.createBinaryCriterion("hh", sizeCriterion1, "^", sizeCriterion2);
            assertEquals("hh", criterion.getName());
            assertEquals("hh: cc && gg", criterion.toString());
        } catch (InvalidCriterionParameterException ignored) {}
    }
}
