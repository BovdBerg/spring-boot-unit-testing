package com.luv2code.tdd;

/**
 * Rules:
 * 1. If the number is divisible by 3, return "Fizz"
 * 2. If the number is divisible by 5, return "Buzz"
 * 3. If the number is divisible by both 3 and 5, return "FizzBuzz
 * 4. If the number is not divisible by either 3 or 5, return the number
 */
public class FizzBuzz {
    private FizzBuzz() {
    }

    public static String compute(int i) {
        StringBuilder result = new StringBuilder();

        if (i % 3 == 0) {
            result.append("Fizz");
        }
        if (i % 5 == 0) {
            result.append("Buzz");
        }

        return result.isEmpty() ? Integer.toString(i) : result.toString();
    }
}
