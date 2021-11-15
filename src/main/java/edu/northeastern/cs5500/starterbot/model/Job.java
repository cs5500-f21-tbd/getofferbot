package edu.northeastern.cs5500.starterbot.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Zimeng (Parker) Xie, CS 5500, Fall 2021 A job posting class, using Lombok to minimize boilerplate
 * code
 */
@Data // provides getters, setters, equals & hashcode
@NoArgsConstructor // provides a default/no-arguments constructor, and
@AllArgsConstructor // provides a fully-specified/all-arguments constructor
public class Job {
    private Integer jobId;
    private String jobTitle;
    private String jobType;
    private Experience
            experience; // An experience class has been used in lieu of its enum counterpart.
    private String company;
    private LocalDate created;
    private float annualPay;
    private float starRating;
    private boolean sponsorship;
    private String
            linkToApply; // This might eventually be implemented using the URI class from java
    private Location location;
}
