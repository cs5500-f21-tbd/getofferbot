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
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class SearchCommandTest {

    InMemoryRepository<JobType> jobTypeRepository;
    InMemoryRepository<Experience> experienceRepository;
    InMemoryRepository<Location> locationRepository;
    InMemoryRepository<Job> jobRepository;
    JobTypeController jobTypeController;
    ExperienceController experienceController;
    LocationController locationController;
    JobController jobController;
    List<Job> expectedJobList;
    SearchCommand searchCommand;
    List<Job> jobListBeforeSearch;
    String expectedName;
    CommandData expectedCommandData;

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

        expectedJobList.add(0, JobUtilities.generateJobCreated1(jobController));
        expectedJobList.add(1, JobUtilities.generateJobCreated2(jobController));
        expectedJobList.add(2, JobUtilities.generateJobCreated3(jobController));
        expectedJobList.add(3, JobUtilities.generateJobAnnualPay2(jobController));
        expectedJobList.add(4, JobUtilities.generateJobStarRating2(jobController));

        searchCommand = new SearchCommand(jobController);

        jobListBeforeSearch = new ArrayList<>(this.jobController.getJobRepository().getAll());

        expectedName = "search";
        String description = "Search for jobs";
        expectedCommandData = new CommandData(expectedName, description);
    }

    @Test
    void testSearchJobsInList() {
        List<Job> actualSearchList = searchCommand.searchJobsInList(jobListBeforeSearch);
        List<Job> actualTopFiveJobs = actualSearchList.subList(0, 5);
        assertThat(actualTopFiveJobs).isEqualTo(expectedJobList);
    }

    @Test
    void testGetName() {
        String actualName = searchCommand.getName();
        assertThat(actualName).isEqualTo(expectedName);
    }

    @Test
    void testGetCommandData() {
        CommandData actualCommandData = searchCommand.getCommandData();
        assertThat(actualCommandData).isEqualTo(expectedCommandData);
    }
}
