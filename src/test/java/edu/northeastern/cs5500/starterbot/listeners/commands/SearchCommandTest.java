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
import edu.northeastern.cs5500.starterbot.utility.JobUtilities;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SearchCommandTest {

    InMemoryRepository<Job> jobRepository;
    InMemoryRepository<JobType> jobTypeRepository;
    InMemoryRepository<Experience> experienceRepository;
    InMemoryRepository<Location> locationRepository;
    JobTypeController jobTypeController;
    ExperienceController experienceController;
    LocationController locationController;
    JobController jobController;
    SearchCommand searchCommand;

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

        searchCommand = new SearchCommand(jobController);
    }

    @Test
    void testSearchJobsInList() {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        List<Job> actualSearchList = searchCommand.searchJobsInList(jobList);
        List<Job> actualTopFiveJobs = actualSearchList.subList(0, 5);
        List<Job> expectedList = new ArrayList<>();
        expectedList.add(0, JobUtilities.generateJobCreated1(jobController));
        expectedList.add(1, JobUtilities.generateJobCreated2(jobController));
        expectedList.add(2, JobUtilities.generateJobCreated3(jobController));
        expectedList.add(3, JobUtilities.generateJobAnnualPay2(jobController));
        expectedList.add(4, JobUtilities.generateJobStarRating2(jobController));
        assertThat(actualTopFiveJobs).isEqualTo(expectedList);
    }

    @Test
    void testSearchHandleJobWithoutPostDate() {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        jobList = searchCommand.searchJobsInList(jobList);
        assertThat(jobList.get(5).getCreated()).isEqualTo(null);
    }

    @Test
    public void testGetName() {
        String actualName = searchCommand.getName();
        assertThat(actualName).isEqualTo("search");
    }
}
