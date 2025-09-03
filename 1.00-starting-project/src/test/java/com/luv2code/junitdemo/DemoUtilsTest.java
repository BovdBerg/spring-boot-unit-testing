package com.luv2code.junitdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeEach
    void setupBeforeEach() {
        demoUtils = new DemoUtils();
    }

    @Test
    @DisplayName("`add`: assertEquals and assertNotEquals")
    void testEqualsAndNotEquals() {
        assertEquals(6, demoUtils.add(2, 4), "2 + 4 should equal 6");
        assertNotEquals(8, demoUtils.add(2, 4), "2 + 4 should not equal 8");
        assertNotEquals(6, demoUtils.add(2, 3), "2 + 3 should not equal 6");
    }

    @Test
    @DisplayName("`checkNull`: assertNull and assertNotNull")
    void testNullAndNotNull() {
        String str2 = "luv2code";

        assertNull(demoUtils.checkNull(null), "Object should be null");
        assertNotNull(demoUtils.checkNull(str2), "Object should not be null");
    }
}
