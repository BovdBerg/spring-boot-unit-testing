package com.luv2code.tdd;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {
    // Rules:
    // If the number is divisible by 3, return "Fizz"
    // If the number is divisible by 5, return "Buzz"
    // If the number is divisible by both 3 and 5, return "FizzBuzz"
    // If the number is not divisible by either 3 or 5, return the number

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
}
