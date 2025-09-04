package com.luv2code.junitdemo;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeEach
    void beforeEach() {
        demoUtils = new DemoUtils();
    }

    @Test
    @Order(-1)
    @DisplayName("`add` method: assertEquals & assertNotEquals")
    void Equals_and_not_Equals() {
        assertEquals(6, demoUtils.add(2, 4), "2 + 4 should equal 6");
        assertNotEquals(8, demoUtils.add(2, 4), "2 + 4 should not equal 8");
        assertNotEquals(6, demoUtils.add(2, 3), "2 + 3 should not equal 6");
    }

    @Test
    void Multiply() {
        assertAll(
                () -> assertEquals(8, demoUtils.multiply(2, 4), "2 * 4 should equal 8"),
                () -> assertEquals(0, demoUtils.multiply(2, 0), "2 * 0 should equal 0"),
                () -> assertEquals(-2, demoUtils.multiply(2, -1), "2 * -1 should equal -2")
        );
    }

    @Test
    void Null_and_not_Null() {
        String str2 = "luv2code";

        assertNull(demoUtils.checkNull(null), "Object should be null");
        assertNotNull(demoUtils.checkNull(str2), "Object should not be null");
    }

    @Test
    @Order(2)
    void Same_and_not_Same() {
        String str = "luv2code";

        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(), "Objects should be the same");
        assertNotSame(str, demoUtils.getAcademy(), "Objects should not be the same");
    }

    @Test
    void True_and_False() {
        int gradeOne = 10;
        int gradeTwo = 5;

        assertTrue(demoUtils.isGreater(gradeOne, gradeTwo), "Should return true");
        assertFalse(demoUtils.isGreater(gradeTwo, gradeOne), "Should return false");
    }

    @Test
    @Order(1)
    void Array_Equals() {
        String[] stringArray = {"A", "B", "C"};

        assertArrayEquals(stringArray, demoUtils.getFirstThreeLettersOfAlphabet(), "Arrays should be equal");
    }

    @Test
    @Order(1)
    void Iterable_Equals() {
        Iterable<String> stringList = List.of("luv", "2", "code");

        assertIterableEquals(stringList, demoUtils.getAcademyInList(), "Iterables should be equal");
    }

    @Test
    @Order(1)
    void Line_Match() {
        List<String> stringList = List.of("luv", "2", "code");

        assertLinesMatch(stringList, demoUtils.getAcademyInList(), "Lines should match");
    }

    @Test
    void Throws_or_not() {
        assertThrows(Exception.class, () -> demoUtils.throwException(-1), "Should throw exception");
        assertDoesNotThrow(() -> demoUtils.throwException(5), "Should not throw exception");
    }

    @Test
    void Timeout() {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> demoUtils.checkTimeout(),
                "Method should execute within 1 seconds");
    }
}
