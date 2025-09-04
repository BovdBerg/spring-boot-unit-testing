package com.luv2code.junitdemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class ConditionalTests {

    @Test
    @Disabled("Disabled until JIRA #123 is fixed")
    void basicTest() {
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testOnlyOnWindows() {
    }

    @Test
    @EnabledOnOs(OS.MAC)
    void testOnlyOnMac() {
    }

    @Test
    @EnabledOnOs({OS.WINDOWS, OS.LINUX})
    void testOnWindowsOrLinux() {
    }
}
