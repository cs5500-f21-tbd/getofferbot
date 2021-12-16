package edu.northeastern.cs5500.starterbot.listeners.commands;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.controller.ExperienceController;
import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.controller.JobTypeController;
import edu.northeastern.cs5500.starterbot.controller.LocationController;
import edu.northeastern.cs5500.starterbot.model.Experience;
import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.model.JobType;
import edu.northeastern.cs5500.starterbot.model.Location;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FilterCommandTest {

    InMemoryRepository<Job> jobRepository;
    InMemoryRepository<JobType> jobTypeRepository;
    InMemoryRepository<Experience> experienceRepository;
    InMemoryRepository<Location> locationRepository;
    JobTypeController jobTypeController;
    ExperienceController experienceController;
    LocationController locationController;
    JobController jobController;
    FilterCommand filterCommand;

    @BeforeEach
    void setUpReposAndControllers() {
        jobRepository = new InMemoryRepository<>();
        jobTypeRepository = new InMemoryRepository<>();
        experienceRepository = new InMemoryRepository<>();
        locationRepository = new InMemoryRepository<>();
        jobTypeController = new JobTypeController(jobTypeRepository);
        experienceController = new ExperienceController(experienceRepository);
        locationController = new LocationController(locationRepository);

        jobController =
                new JobController(
                        jobRepository, jobTypeController, experienceController, locationController);

        filterCommand = new FilterCommand(jobController);
    }

    @Test
    void testDefaultJobsAreCreated() {
        assertThat(jobRepository.getAll()).isNotEmpty();
        assertThat(jobRepository.getAll().size()).isEqualTo(27);
    }

    @Test
    void testgetCompanyName() {
        ArrayList<String> companyList = filterCommand.getCompanyNameList();
        Logger logger = Logger.getLogger("FilterCommandTest");
        logger.info(String.valueOf(companyList.size()));
        System.out.println("company" + String.valueOf(companyList.size()));
        assertThat(companyList.size()).isAtLeast(20);
    }

    @Test
    void testremoveNullforAnnualpay() {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        System.out.println("company before " + String.valueOf(jobList.size()));
        List<Job> newjobList = filterCommand.removeNullForAnnualPay(jobList);
        System.out.println("company" + String.valueOf(jobList.size()));
        assertThat(jobList.size() > newjobList.size());
    }
}
