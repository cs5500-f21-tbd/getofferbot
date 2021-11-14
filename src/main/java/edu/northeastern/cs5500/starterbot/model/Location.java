package edu.northeastern.cs5500.starterbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zimeng (Parker) Xie, CS 5500, Fall 2021
 * A location class, using Lombok to minimize boilerplate code
 */
@Data // provides getters, setters, equals & hashcode
@NoArgsConstructor // provides a default/no-arguments constructor, and
@AllArgsConstructor // provides a fully-specified/all-arguments constructor
public class Location {
    private Integer zipCode;
    private String city;
    private String state;
    private String country;
}
