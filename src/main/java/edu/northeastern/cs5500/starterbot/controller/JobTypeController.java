package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.JobType;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;

/**
 * This is the controller class for JobType, which converts job type input (a String) into concrete
 * JobType objects. Also creates default cases upon initialization with an empty repo.
 */
@Data
public class JobTypeController {
    private GenericRepository<JobType> jobTypeRepository;

    /**
     * Constructor. Initializes the controller and create default cases if JobType repo is empty.
     *
     * @param jobTypeRepository repo for JobType objects
     */
    public JobTypeController(GenericRepository<JobType> jobTypeRepository) {
        this.jobTypeRepository = jobTypeRepository;

        createDefaults();
    }

    /** Create default cases for job type. */
    private void createDefaults() {
        if (jobTypeRepository.count() > 0) {
            // Only create default job type levels if none exist
            return;
        }
        jobTypeRepository.add(new JobType("fulltime", "Full-time"));
        jobTypeRepository.add(new JobType("parttime", "Part-time"));
    }

    /**
     * Look up the JobType object that matches the given label; returns null if no match
     *
     * @param label - a non-null string, such as "parttime"
     * @return desired JobType object
     */
    @Nullable
    public JobType getJobTypeByLabel(@Nonnull String label) {
        for (JobType jobType : jobTypeRepository.getAll()) {
            if (label.equals(jobType.getLabel())) {
                return jobType;
            }
        }
        return null;
    }
}
