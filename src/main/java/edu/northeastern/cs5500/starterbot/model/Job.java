package edu.northeastern.cs5500.starterbot.model;

import java.time.LocalDate;
import javax.annotation.Nonnull;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Job implements Model {
    private ObjectId id;
    @Nonnull private String jobTitle;
    private ObjectId jobType;
    private ObjectId experience;
    @Nonnull private String company;
    private LocalDate created;
    private Float annualPay;
    private Float starRating;
    @Nonnull private Boolean sponsorship = false;
    @Nonnull private String linkToApply; // Will be refactored into URI class next sprint
    @Nonnull private ObjectId location;
}
