package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.Location;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This is the controller class for Location, which converts location-related inputs into
 * concrete Location objects. Also creates default cases upon initialization with an empty repo.
 */
public class LocationController {
    GenericRepository<Location> locationRepository;

    /**
     * Constructor. Initializes the controller and create default cases if Location repo is empty.
     * @param locationRepository repo for Location objects
     */
    public LocationController(GenericRepository<Location> locationRepository) {
        this.locationRepository = locationRepository;

        createDefaults();
    }

    /**
     * Create default cases for location.
     */
    private void createDefaults() {
        if (locationRepository.count() > 0) {
            // Only create default locations if none exist
            return;
        }

        Location location1 = new Location("98101", "Seattle", "WA", "USA");

        Location location2 = new Location("10001", "New York", "NY", "USA");

        Location location3 = new Location("94404", "San Mateo", "CA", "USA");

        locationRepository.add(location1);
        locationRepository.add(location2);
        locationRepository.add(location3);
    }

    /**
     * Look up the Location object that matches the given zip code; returns null if no match
     * @param zipCode - a non-null string, such as "98101"
     * @return desired Location object
     */
    @Nullable
    public Location getLocationByZipCode(@Nonnull String zipCode) {
        for (Location location : locationRepository.getAll()) {
            if (zipCode.equals(location.getZipCode())) {
                return location;
            }
        }
        return null;
    }
}
