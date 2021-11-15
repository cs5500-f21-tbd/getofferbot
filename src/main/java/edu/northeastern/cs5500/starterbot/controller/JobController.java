package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.model.Location;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import java.time.LocalDate;
import java.util.Collection;

public class JobController {
    GenericRepository<Job> jobRepository;
    ExperienceController experienceController;

    public JobController(
            GenericRepository<Job> jobRepository, ExperienceController experienceController) {
        this.jobRepository = jobRepository;
        this.experienceController = experienceController;

        createDefaults();
    }

    private void createDefaults() {
        if (jobRepository.count() > 0) {
            // Only create default jobs if none exist
            return;
        }
        jobRepository.add(
                new Job(
                        1,
                        "University Grad Software Engineer (USA)",
                        "Full-time", // TODO(pxie2016): this feels like it could be an object
                        // reference like experience
                        experienceController.getExperienceByLabel("entry").getId(),
                        "Pinterest",
                        LocalDate.of(
                                2021, 8, 20), // TODO(pxie2016): consider other ways to store this
                        80000f,
                        4.2f,
                        true,
                        "pinterest.com",
                        new Location(98101, "Seattle", "WA", "USA")));

        // TODO(pxie2016): Consider adding more default jobs
    }

    public Collection<Job> getAll() {
        return jobRepository.getAll();
    }
}
