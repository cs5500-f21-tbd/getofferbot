// TODO : continue sort command test

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

public class SortCommandTest {

    InMemoryRepository<Job> jobRepository;
    InMemoryRepository<JobType> jobTypeRepository;
    InMemoryRepository<Experience> experienceRepository;
    InMemoryRepository<Location> locationRepository;
    JobTypeController jobTypeController;
    ExperienceController experienceController;
    LocationController locationController;
    JobController jobController;
    SortCommand sortCommand;

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

        sortCommand = new SortCommand(jobController);
    }

    @Test
    void testDefaultJobsAreCreated() {
        assertThat(jobRepository.getAll()).isNotEmpty();
        assertThat(jobRepository.getAll().size()).isEqualTo(27);
    }

    @Test
    public void testGetName() {
        String actualName = sortCommand.getName();
        assertThat(actualName).isEqualTo("sort");
    }

    @Test
    public void testCheckNumeric() {
        assertThat(sortCommand.checkNumeric("notnumber")).isEqualTo(false);
        assertThat(sortCommand.checkNumeric("123")).isEqualTo(true);
    }

    @Test
    void testSizeHandler() {

        assertThat(sortCommand.sizeHandler("123", 3)).isEqualTo(3);
        assertThat(sortCommand.sizeHandler("1", 3)).isEqualTo(1);
        assertThat(sortCommand.sizeHandler("abc", 3)).isEqualTo(3);
    }

    @Test
    void testSortByRatingsDES() {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        jobList = sortCommand.sortJob(jobList, "rating", "des", "2");
        List<Job> expectedList = new ArrayList<>();
        expectedList.add(JobUtilities.generateJobStarRating1(jobController));
        expectedList.add(JobUtilities.generateJobStarRating2(jobController));
        assertThat(jobList).isEqualTo(expectedList);
    }

    @Test
    void testSortByPostDateDEC() {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        jobList = sortCommand.sortJob(jobList, "post date", "des", "2");
        List<Job> expectedList = new ArrayList<>();
        expectedList.add(JobUtilities.generateJobCreated1(jobController));
        expectedList.add(JobUtilities.generateJobCreated2(jobController));
        assertThat(jobList).isEqualTo(expectedList);
    }

    @Test
    void testSortByAnnualPayDEC() {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        jobList = sortCommand.sortJob(jobList, "annual pay", "des", "2");
        List<Job> expectedList = new ArrayList<>();
        expectedList.add(JobUtilities.generateJobAnnualPay1(jobController));
        expectedList.add(JobUtilities.generateJobAnnualPay2(jobController));
        assertThat(jobList).isEqualTo(expectedList);
    }

    @Test
    void testSortByAnnualPayASC() {
        List<Job> jobList = new ArrayList<>();
        jobList.add(JobUtilities.generateJobAnnualPay1(jobController));
        jobList.add(JobUtilities.generateJobAnnualPay2(jobController));
        jobList = sortCommand.sortJob(jobList, "annual pay", "asc", "2");
        List<Job> expectedList = new ArrayList<>();
        expectedList.add(JobUtilities.generateJobAnnualPay2(jobController));
        expectedList.add(JobUtilities.generateJobAnnualPay1(jobController));
        assertThat(jobList).isEqualTo(expectedList);
    }
}
