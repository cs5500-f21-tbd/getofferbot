package edu.northeastern.cs5500.starterbot.controller;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.Experience;
import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.model.JobType;
import edu.northeastern.cs5500.starterbot.model.Location;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class JobControllerTest {
    @Test
    void testDefaultJobs() {
        InMemoryRepository<Job> jobRepository = new InMemoryRepository<>();
        InMemoryRepository<JobType> jobTypeRepository = new InMemoryRepository<>();
        InMemoryRepository<Experience> experienceRepository = new InMemoryRepository<>();
        InMemoryRepository<Location> locationRepository = new InMemoryRepository<>();
        JobTypeController jobTypeController = new JobTypeController(jobTypeRepository);
        ExperienceController experienceController = new ExperienceController(experienceRepository);
        LocationController locationController = new LocationController(locationRepository);
        JobController jobController = new JobController(jobRepository, jobTypeController,
                experienceController, locationController);

        assertThat(jobRepository.getAll()).isNotEmpty();
        assertThat(jobRepository.getAll().size()).isEqualTo(3);
    }

    @Test
    void testJobWithoutSalarySpecifiedExists() {
        InMemoryRepository<Job> jobRepository = new InMemoryRepository<>();
        InMemoryRepository<JobType> jobTypeRepository = new InMemoryRepository<>();
        InMemoryRepository<Experience> experienceRepository = new InMemoryRepository<>();
        InMemoryRepository<Location> locationRepository = new InMemoryRepository<>();
        JobTypeController jobTypeController = new JobTypeController(jobTypeRepository);
        ExperienceController experienceController = new ExperienceController(experienceRepository);
        LocationController locationController = new LocationController(locationRepository);
        JobController jobController = new JobController(jobRepository, jobTypeController,
                experienceController, locationController);

        ArrayList<Job> jobList = new ArrayList<>(jobController.getAll());
        boolean salarySpecified = true;
        for (Job job: jobList) {
            if (job.getAnnualPay() == null) {
                salarySpecified = false;
                break;
            }
        }
        assertThat(salarySpecified).isFalse();
    }
}
