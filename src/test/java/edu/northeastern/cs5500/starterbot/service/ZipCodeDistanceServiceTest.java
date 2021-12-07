package edu.northeastern.cs5500.starterbot.service;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ZipCodeDistanceServiceTest {
    static ZipCodeDistanceService zipcodeDistanceService;

    @BeforeEach
    static void setUp() {
        zipcodeDistanceService = new ZipCodeDistanceService();
    }

    @Test
    void testTwoSeattleAreaZipCodesAreClose() {
        assertThat(zipcodeDistanceService.getDistanceInMiles("98005", "98103")).isAtMost(25d);
    }

    @Test
    void testASeattleZipCodeAndABostonZipCodeAreFar() {
        assertThat(zipcodeDistanceService.getDistanceInMiles("98101", "02114")).isAtLeast(2000d);
    }
}
