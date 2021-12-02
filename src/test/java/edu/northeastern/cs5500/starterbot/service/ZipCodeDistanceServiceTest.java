package edu.northeastern.cs5500.starterbot.service;

import static com.google.common.truth.Truth.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;

class ZipCodeDistanceServiceTest {
    @Test
    void testTwoSeattleAreaZipCodesAreClose() throws URISyntaxException, IOException {
        assertThat(new ZipCodeDistanceService("98005", "98103").getMiles()).isAtMost(25d);
    }

    @Test
    void testASeattleZipCodeAndABostonZipCodeAreFar() throws URISyntaxException, IOException {
        assertThat(new ZipCodeDistanceService("98101", "02114").getMiles()).isAtLeast(2000d);
    }
}
