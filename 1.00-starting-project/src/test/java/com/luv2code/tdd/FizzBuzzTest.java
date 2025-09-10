package com.luv2code.tdd;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {
    // If the number is divisible by 3, return "Fizz"
    @Test
    @DisplayName("Divisible by 3: Fizz")
    @Order(1)
    void divisibleBy3() {
        String expected = "Fizz";

        assertEquals(expected, FizzBuzz.compute(3), "3 should return Fizz");
    }

    // If the number is divisible by 5, return "Buzz"
    @Test
    @DisplayName("Divisible by 5: Buzz")
    @Order(2)
    void divisibleBy5() {
        String expected = "Buzz";

        assertEquals(expected, FizzBuzz.compute(5), "5 should return Buzz");
    }

    // If the number is divisible by both 3 and 5, return "FizzBuzz"
    @Test
    @DisplayName("Divisible by 3 and 5: FizzBuzz")
    @Order(3)
    void divisibleBy3And5() {
        String expected = "FizzBuzz";

        assertEquals(expected, FizzBuzz.compute(15), "15 should return FizzBuzz");
    }

    // If the number is not divisible by either 3 or 5, return the number
    @Test
    @DisplayName("Not divisible by 3 or 5: number itself")
    @Order(4)
    void notDivisibleBy3Or5() {
        int input = 1;
        String expected = Integer.toString(input);

        assertEquals(expected, FizzBuzz.compute(input), "7 should return 7");
    }

    @Test
    @DisplayName("Manually test multiple values")
    @Order(5)
    void testValuesFrom1To7() {
        String[] expectedValues = {"1", "2", "Fizz", "4", "Buzz", "Fizz", "7"};

        for (int i = 1; i <= 7; i++) {
            assertEquals(expectedValues[i - 1], FizzBuzz.compute(i), i + " should return " + expectedValues[i - 1]);
        }
    }

    @ParameterizedTest(name = "input={0} --> expected={1}")
    @CsvSource({
            "1, 1",
            "3, Fizz",
            "5, Buzz",
            "15, FizzBuzz"
    })
    @DisplayName("Parameterized test from CSV")
    @Order(6)
    void parameterizedTestFromCsv(int input, String expected) {
        assertEquals(expected, FizzBuzz.compute(input), input + " should return " + expected);
    }

    public static Stream<Arguments> fizzBuzzTestDataProvider() {
        return Stream.of(
                Arguments.of(1, "1"),
                Arguments.of(3, "Fizz"),
                Arguments.of(5, "Buzz"),
                Arguments.of(15, "FizzBuzz")
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/fizzbuzz-test-data.csv", numLinesToSkip = 1)
    @DisplayName("Parameterized test from CSV file")
    @Order(6)
    void parameterizedTestFromCsvFile(int input, String expected) {
        assertEquals(expected, FizzBuzz.compute(input), input + " should return " + expected);
    }

    //@EnumSource exists too but is not applicable here

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
    @DisplayName("Parameterized test from ValueSource")
    @Order(6)
    void parameterizedTestFromValueSource(int input) {
        StringBuilder expected = new StringBuilder();
        if (input % 3 == 0) {
            expected.append("Fizz");
        }
        if (input % 5 == 0) {
            expected.append("Buzz");
        }
        if (expected.isEmpty()) {
            expected.append(input);
        }

        assertEquals(expected.toString(), FizzBuzz.compute(input), input + " should return " + expected);
    }

    @ParameterizedTest
    @MethodSource("fizzBuzzTestDataProvider")
    @DisplayName("Parameterized test from MethodSource")
    @Order(6)
    void parameterizedTestFromMethodSource(int input, String expected) {
        assertEquals(expected, FizzBuzz.compute(input), input + " should return " + expected);
    }
}
