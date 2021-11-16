package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.JobType;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** This class is all but identical to ExperienceController.java */
public class JobTypeController {
    GenericRepository<JobType> jobTypeRepository;

    public JobTypeController(GenericRepository<JobType> JobTypeRepository) {
        this.jobTypeRepository = JobTypeRepository;

        createDefaults();
    }

    private void createDefaults() {
        if (jobTypeRepository.count() > 0) {
            // Only create default job type levels if none exist
            return;
        }
        jobTypeRepository.add(new JobType("fulltime", "Full-time"));
        jobTypeRepository.add(new JobType("parttime", "Part-time"));
    }

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
