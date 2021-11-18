package edu.northeastern.cs5500.starterbot.controller;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.Location;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocationControllerTest {

    InMemoryRepository<Location> locationRepository;
    LocationController locationController;

    @BeforeEach
    void setUpRepoAndController() {
        locationRepository = new InMemoryRepository<>();
        locationController = new LocationController(locationRepository);
    }

    @Test
    void testDefaultLocationsAreCreated() {
        assertThat(locationRepository.getAll()).isNotEmpty();
        assertThat(locationRepository.getAll().size()).isEqualTo(3);
    }

    @Test
    void testDefaultLocationHas10001() {
        assertThat(locationController.getLocationByZipCode("10001")).isNotNull();
    }
}
