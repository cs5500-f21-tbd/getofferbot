package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import java.time.LocalDate;
import java.util.Collection;

public class JobController {
    GenericRepository<Job> jobRepository;
    JobTypeController jobTypeController;
    ExperienceController experienceController;
    LocationController locationController;

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

    private void createDefaults() {
        if (jobRepository.count() > 0) {
            // Only create default jobs if none exist
            return;
        }

        Job job1 =
                new Job(
                        "University Grad Software Engineer (USA)",
                        jobTypeController.getJobTypeByLabel("fulltime").getId(),
                        experienceController.getExperienceByLabel("entry").getId(),
                        "Pinterest",
                        LocalDate.of(2021, 8, 20),
                        4.2f,
                        "pinterest.com",
                        locationController.getLocationByZipCode(98101).getId());
        job1.setAnnualPay(140000f);
        job1.setSponsorship(true);

        Job job2 =
                new Job(
                        "Software Engineer",
                        jobTypeController.getJobTypeByLabel("fulltime").getId(),
                        experienceController.getExperienceByLabel("entry").getId(),
                        "Splunk",
                        LocalDate.of(2021, 11, 12),
                        4.0f,
                        "splunk.com",
                        locationController.getLocationByZipCode(10001).getId());
        job2.setAnnualPay(80000f);

        Job job3 =
                new Job(
                        "Staff Software Engineer",
                        jobTypeController.getJobTypeByLabel("fulltime").getId(),
                        experienceController.getExperienceByLabel("senior").getId(),
                        "Visa",
                        LocalDate.of(2021, 11, 9),
                        4.1f,
                        "visa.com",
                        locationController.getLocationByZipCode(94404).getId());
        job3.setSponsorship(false);

        jobRepository.add(job1);
        jobRepository.add(job2);
        jobRepository.add(job3);
    }

    public Collection<Job> getAll() {
        return jobRepository.getAll();
    }
}
