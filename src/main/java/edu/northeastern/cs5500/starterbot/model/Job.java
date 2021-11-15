package edu.northeastern.cs5500.starterbot.model;

import java.time.LocalDate;
import javax.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Job implements Model {
    private ObjectId id;
    // TODO(pxie2016): remove nonnull if they can/may be null
    @Nonnull private Integer jobId;
    @Nonnull private String jobTitle;
    @Nonnull private String jobType;
    @Nonnull private ObjectId experience;
    @Nonnull private String company;
    @Nonnull private LocalDate created;
    @Nonnull private Float annualPay;
    @Nonnull private Float starRating;
    @Nonnull private Boolean sponsorship;
    @Nonnull private String linkToApply; // TODO: consider URI class
    @Nonnull private Location location;
}
