package edu.northeastern.cs5500.starterbot.service;

import static com.google.common.truth.Truth.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ZipCodeDistanceServiceTest {
    static ZipCodeDistanceService zipcodeDistanceService;

    @BeforeAll
    static void setUp() {
        zipcodeDistanceService = new ZipCodeDistanceService();
    }

    @Test
    void testTwoSeattleAreaZipCodesAreClose() throws URISyntaxException, IOException {
        assertThat(zipcodeDistanceService.getDistanceInMiles("98005", "98103")).isAtMost(25d);
    }

    @Test
    void testASeattleZipCodeAndABostonZipCodeAreFar() throws URISyntaxException, IOException {
        assertThat(zipcodeDistanceService.getDistanceInMiles("98101", "02114")).isAtLeast(2000d);
    }
}
