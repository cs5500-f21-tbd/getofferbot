package edu.northeastern.cs5500.starterbot.model;

import java.time.LocalDate;
import javax.annotation.Nonnull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Job implements Model {
    @EqualsAndHashCode.Exclude private ObjectId id;
    @Nonnull private String jobTitle;
    private ObjectId jobType;
    private ObjectId experience;
    @Nonnull private String company;
    private LocalDate created;
    private Float annualPay;
    private Float starRating;
    @Nonnull private Boolean sponsorship = false;
    @Nonnull private String linkToApply; // Will be refactored into URI class next sprint
    private ObjectId location;
}
