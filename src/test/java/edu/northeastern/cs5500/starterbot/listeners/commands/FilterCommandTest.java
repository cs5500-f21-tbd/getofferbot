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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    LocalDate presentDate;
    String text1;
    String text2;
    String keyword1;
    String keyword2;
    String keyword3;

    List<Job> rawJobList;
    List<Job> filteredJobList;

    @BeforeEach
    void setUpReposAndControllers() {
        jobRepository = new InMemoryRepository<>();
        jobTypeRepository = new InMemoryRepository<>();
        experienceRepository = new InMemoryRepository<>();
        locationRepository = new InMemoryRepository<>();
        jobTypeController = new JobTypeController(jobTypeRepository);
        experienceController = new ExperienceController(experienceRepository);
        locationController = new LocationController(locationRepository);

        text1 = "Get Offer Bot";
        text2 = "TeamTBD";

        keyword1 = "bot";
        keyword2 = "Get Offer";
        keyword3 = "TBD";

        presentDate = LocalDate.now();

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
        assertThat(companyList.size()).isAtLeast(20);
    }

    @Test
    void testContainsKeyword() {
        assertThat(filterCommand.containsKeyword(text1, keyword2));
        assertThat(filterCommand.containsKeyword(text1, keyword1));
        assertThat(filterCommand.containsKeyword(text1, keyword2));
        assertThat(filterCommand.containsKeyword(text2, keyword3));
        assertThat(filterCommand.containsKeyword(text2, keyword1).equals(false));
    }

    @Test
    void testParsingDate() {
        LocalDate returnvalue1 = presentDate.minusDays(1);
        LocalDate returnvalue2 = presentDate.minusDays(3);
        LocalDate returnvalue3 = presentDate.minusWeeks(1);
        LocalDate returnvalue4 = presentDate.minusMonths(1);

        assertThat(filterCommand.parsingDate("1 day").equals(returnvalue1));
        assertThat(filterCommand.parsingDate("3 days").equals(returnvalue2));
        assertThat(filterCommand.parsingDate("1 week").equals(returnvalue3));
        assertThat(filterCommand.parsingDate("1 month").equals(returnvalue4));
    }

    @Test
    void testFilterByExperience() {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        jobList = filterCommand.filterJobs(jobList, "experience", "intern");
        assertThat(jobList).contains(JobUtilities.generateJobExperience4(jobController));
    }

    @Test
    void testFilterByPay() {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        jobList = filterCommand.filterJobs(jobList, "annualpay", "130000");
        assertThat(jobList).contains(JobUtilities.generateJobAnnualPay1(jobController));
        assertThat(jobList).contains(JobUtilities.generateJobAnnualPay2(jobController));
        assertThat(jobList).contains(JobUtilities.generateJobAnnualPay2(jobController));
    }

    @Test
    void testFilterByRating() {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        jobList = filterCommand.filterJobs(jobList, "rating", "4");
        assertThat(jobList).contains(JobUtilities.generateJobStarRating1(jobController));
        assertThat(jobList).contains(JobUtilities.generateJobStarRating2(jobController));
    }

    @Test
    void testFilterByCompany() {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        jobList = filterCommand.filterJobs(jobList, "company", "Apple");
        assertThat(jobList).contains(JobUtilities.generateJobExperience1(jobController));
        assertThat(jobList).contains(JobUtilities.generateJobStarRating2(jobController));
    }

    @Test
    void testFilterPayByCreated() {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        jobList = filterCommand.filterJobs(jobList, "date_posted", "1 month");
        assertThat(jobList).contains(JobUtilities.generateJobCreated1(jobController));
        assertThat(jobList).contains(JobUtilities.generateJobCreated2(jobController));
        assertThat(jobList).contains(JobUtilities.generateJobCreated3(jobController));
    }

    @Test
    void testFilterPayByJobtype() {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        jobList = filterCommand.filterJobs(jobList, "jobtype", "parttime");
        assertThat(jobList).contains(JobUtilities.generateJobType3(jobController));
        assertThat(jobList).contains(JobUtilities.generateJobType4(jobController));
    }
}
