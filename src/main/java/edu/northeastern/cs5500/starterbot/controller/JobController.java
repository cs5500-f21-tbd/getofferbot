package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.utility.JobUtilities;
import java.util.Collection;
import lombok.Data;

/**
 * This is the controller class for Job, which, in conjunction with ExperienceController,
 * JobTypeController, and LocationController, converts job-related inputs into concrete Job objects.
 * Also creates default cases upon initialization with an empty repo.
 */
@Data
public class JobController {
    private GenericRepository<Job> jobRepository;
    private JobTypeController jobTypeController;
    private ExperienceController experienceController;
    private LocationController locationController;

    public JobController(
            GenericRepository<Job> jobRepository,
            JobTypeController jobTypeController,
            ExperienceController experienceController,
            LocationController locationController) {
        this.jobRepository = jobRepository;
        this.jobTypeController = jobTypeController;
        this.experienceController = experienceController;
        this.locationController = locationController;

        createDefaults();
    }

    /** Create default cases for jobs. */
    private void createDefaults() {
        if (jobRepository.count() > 0) {
            // Only create default jobs if none exist
            return;
        }

        jobRepository.add(JobUtilities.generateJobAnnualPay1());
        jobRepository.add(JobUtilities.generateJobAnnualPay2());
        jobRepository.add(JobUtilities.generateJobAnnualPay3());
        jobRepository.add(JobUtilities.generateJobAnnualPay4NoAnnualPay());
        jobRepository.add(JobUtilities.generateJobStarRating1());
        jobRepository.add(JobUtilities.generateJobStarRating2());
        jobRepository.add(JobUtilities.generateJobStarRating3());
        jobRepository.add(JobUtilities.generateJobStarRating4NoStarRating());
        jobRepository.add(JobUtilities.generateJobCreated1());
        jobRepository.add(JobUtilities.generateJobCreated2());
        jobRepository.add(JobUtilities.generateJobCreated3());
        jobRepository.add(JobUtilities.generateJobType1());
        jobRepository.add(JobUtilities.generateJobType2());
        jobRepository.add(JobUtilities.generateJobType3());
        jobRepository.add(JobUtilities.generateJobType4());
        jobRepository.add(JobUtilities.generateJobType5NoJobType());
        jobRepository.add(JobUtilities.generateJobLocation1());
        jobRepository.add(JobUtilities.generateJobLocation2());
        jobRepository.add(JobUtilities.generateJobLocation3());
        jobRepository.add(JobUtilities.generateJobLocation4NoLocation());
        jobRepository.add(JobUtilities.generateJobExperience1());
        jobRepository.add(JobUtilities.generateJobExperience2());
        jobRepository.add(JobUtilities.generateJobExperience3());
        jobRepository.add(JobUtilities.generateJobExperience4());
        jobRepository.add(JobUtilities.generateJobSponsorship1());
        jobRepository.add(JobUtilities.generateJobSponsorship2());
        jobRepository.add(JobUtilities.generateJobSponsorship3());
    }

    /**
     * Collect all instances of Jobs in the repo into a Collection
     *
     * @return a Collection of Jobs
     */
    public Collection<Job> getAll() {
        return jobRepository.getAll();
    }
}
