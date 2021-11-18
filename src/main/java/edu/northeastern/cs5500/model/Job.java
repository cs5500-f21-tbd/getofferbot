// Placeholder for task 71
package edu.northeastern.cs5500.model;

import java.sql.Timestamp;
import lombok.Data;

enum Experience {
    intern,
    entry,
    mid,
    senior;
}

@Data
public class Job {
    private Integer JobId;
    private String JobTitle;
    private String JobType;
    private Experience Experience;
    private String Company;
    private Timestamp Created;
    private float AnnualPay;
    private float StarRating;
    private boolean Sponsorship;
    private String LinkToApply;
    private Location Location;
}
