package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.Location;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class LocationController {
    GenericRepository<Location> locationRepository;

    public LocationController(GenericRepository<Location> locationRepository) {
        this.locationRepository = locationRepository;

        createDefaults();
    }

    private void createDefaults() {
        if (locationRepository.count() > 0) {
            // Only create default locations if none exist
            return;
        }

        Location location1 = new Location(
                98101,
                "Seattle",
                "WA",
                "USA");

        Location location2 = new Location(
                10001,
                "New York",
                "NY",
                "USA");

        Location location3 = new Location(
                94404,
                "San Mateo",
                "CA",
                "USA");

        locationRepository.add(location1);
        locationRepository.add(location2);
        locationRepository.add(location3);

    }

    @Nullable
    public Location getLocationByZipCode(@Nonnull Integer zipCode) {
        for (Location location : locationRepository.getAll()) {
            if (zipCode.equals(location.getZipCode())) {
                return location;
            }
        }
        return null;
    }
}
