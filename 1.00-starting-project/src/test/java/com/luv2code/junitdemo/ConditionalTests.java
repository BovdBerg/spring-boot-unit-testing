package com.luv2code.junitdemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

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

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void testOnlyOnJava17() {
    }

    @Test
    @EnabledOnJre({JRE.JAVA_11, JRE.JAVA_17})
    void testOnJava11Or17() {
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_11)
    void testForJava11AndAbove() {
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_11, max = JRE.JAVA_17)
    void testForJava11To17() {
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "LUV2CODE_ENV", matches = "DEV")
    void testEnvironmentVariable() {
    }

    @Test
    @EnabledIfSystemProperty(named = "LUV2CODE_SYS_PROP", matches = "CI_CD_DEPLOY")
    void testSystemProperty() {
    }
}
