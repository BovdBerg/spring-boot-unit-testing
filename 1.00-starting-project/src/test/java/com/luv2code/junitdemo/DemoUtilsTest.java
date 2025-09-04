package com.luv2code.junitdemo;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeEach
    void setupBeforeEach() {
        demoUtils = new DemoUtils();
    }

    @Test
    @DisplayName("Testing `add` method: assertEquals & assertNotEquals")
    void test_Equals_And_Not_Equals() {
        assertEquals(6, demoUtils.add(2, 4), "2 + 4 should equal 6");
        assertNotEquals(8, demoUtils.add(2, 4), "2 + 4 should not equal 8");
        assertNotEquals(6, demoUtils.add(2, 3), "2 + 3 should not equal 6");
    }

    @Test
    void test_Null_And_Not_Null() {
        String str2 = "luv2code";

        assertNull(demoUtils.checkNull(null), "Object should be null");
        assertNotNull(demoUtils.checkNull(str2), "Object should not be null");
    }

    @Test
    void test_Same_And_Not_Same() {
        String str = "luv2code";

        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(), "Objects should be the same");
        assertNotSame(str, demoUtils.getAcademy(), "Objects should not be the same");
    }

    @Test
    void test_True_And_False() {
        int gradeOne = 10;
        int gradeTwo = 5;

        assertTrue(demoUtils.isGreater(gradeOne, gradeTwo), "Should return true");
        assertFalse(demoUtils.isGreater(gradeTwo, gradeOne), "Should return false");
    }

    @Test
    void test_Array_Equals() {
        String[] stringArray = {"A", "B", "C"};

        assertArrayEquals(stringArray, demoUtils.getFirstThreeLettersOfAlphabet(), "Arrays should be equal");
    }

    @Test
    void test_Iterable_Equals() {
        Iterable<String> stringList = List.of("luv", "2", "code");

        assertIterableEquals(stringList, demoUtils.getAcademyInList(), "Iterables should be equal");
    }

    @Test
    void test_Line_Match() {
        List<String> stringList = List.of("luv", "2", "code");

        assertLinesMatch(stringList, demoUtils.getAcademyInList(), "Lines should match");
    }
}
