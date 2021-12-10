package edu.northeastern.cs5500.starterbot.model;

import javax.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

/** A location class that represents a single zip code, using Lombok to minimize boilerplate code */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Location implements Model {
    private ObjectId id;
    @Nonnull private String zipCode;
    @Nonnull private String city;
    @Nonnull private String state;
    @Nonnull private String country;

    @Override
    public String toString() {
        return (city + ", " + state + zipCode + ", " + country);
    }
}
