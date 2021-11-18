package edu.northeastern.cs5500.starterbot.model;

import javax.annotation.Nonnull;
import lombok.Data;
import org.bson.types.ObjectId;

/** A location class that represents a single zip code, using Lombok to minimize boilerplate code */
@Data
public class Location implements Model {
    private ObjectId id;
    @Nonnull private String zipCode;
    @Nonnull private String city;
    @Nonnull private String state;
    @Nonnull private String country;
}
