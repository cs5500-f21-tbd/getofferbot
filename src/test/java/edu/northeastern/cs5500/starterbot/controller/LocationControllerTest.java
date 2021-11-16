package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.Location;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class LocationControllerTest {
    @Test
    void testDefaultLocation() {
        InMemoryRepository<Location> locationRepository = new InMemoryRepository<>();
        LocationController locationController = new LocationController(locationRepository);

        assertThat(locationRepository.getAll()).isNotEmpty();
        assertThat(locationRepository.getAll().size()).isEqualTo(3);
    }

    @Test
    void testDefaultLocationHas10001() {
        InMemoryRepository<Location> locationRepository = new InMemoryRepository<>();
        LocationController locationController = new LocationController(locationRepository);

        assertThat(locationController.getLocationByZipCode(10001)).isNotNull();
    }
}
