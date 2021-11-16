package edu.northeastern.cs5500.starterbot.model;

import javax.annotation.Nonnull;
import lombok.Data;
import org.bson.types.ObjectId;

/**
 * A location class that depicts a combination of zip code, city, state, and country, using Lombok
 * to minimize boilerplate code
 */
@Data // provides getters, setters, equals & hashcode
public class Location implements Model {
    private ObjectId id;
    @Nonnull private Integer zipCode;
    @Nonnull private String city;
    @Nonnull private String state;
    @Nonnull private String country;
}
