package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import edu.northeastern.cs5500.starterbot.listeners.commands.FilterCommand;
import edu.northeastern.cs5500.starterbot.model.Experience;
import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.model.JobType;
import edu.northeastern.cs5500.starterbot.model.Location;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FilterCommandTest {

    InMemoryRepository<Job> jobRepository;
    InMemoryRepository<JobType> jobTypeRepository;
    InMemoryRepository<Experience> experienceRepository;
    InMemoryRepository<Location> locationRepository;
    FilterCommand filterCommand;


    @BeforeEach
    void setUpReposAndControllers() {
        jobRepository = new InMemoryRepository<>();
        jobTypeRepository = new InMemoryRepository<>();
        experienceRepository = new InMemoryRepository<>();
        locationRepository = new InMemoryRepository<>();
        filterCommand = new FilterCommand();
    }

    @Test
    void testDefaultJobsAreCreated() {
        assertThat(jobRepository.getAll()).isNotEmpty();
        assertThat(jobRepository.getAll().size()).isEqualTo(27);
    }

    // @Test
    // void testJobWithoutSalarySpecifiedExists() {
    //     ArrayList<Job> jobList = new ArrayList<>(jobController.getAll());
    //     for (Job job : jobList) {
    //         if (job.getAnnualPay() == null) {
    //             return;
    //         }
    //     }
    //     assertWithMessage("All jobs had salaries specified").that(false).isTrue();
    // }

    @Test
    void testgetCompanyName() {
        ArrayList<String> companyList = filterCommand.getCompanyName();
        if (companyList.size() > 10) {
            assertWithMessage("All companies have been added").that(false).isTrue();
        }
    }
    
}

