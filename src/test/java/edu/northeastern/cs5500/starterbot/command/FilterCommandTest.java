package edu.northeastern.cs5500.starterbot.command;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.controller.ExperienceController;
import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.controller.JobTypeController;
import edu.northeastern.cs5500.starterbot.controller.LocationController;
import edu.northeastern.cs5500.starterbot.listeners.commands.FilterCommand;
import edu.northeastern.cs5500.starterbot.model.Experience;
import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.model.JobType;
import edu.northeastern.cs5500.starterbot.model.Location;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import java.time.LocalDate;
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
    LocalDate presentDate;
    String text1;
    String text2;
    String keyword1;
    String keyword2;
    String keyword3;

    Job jobAnnualPay;
    Job jobStarRating;
    Job jobType;
    Job jobCreated;
    Job jobExperience;

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

        Job jobAnnualPay1 =
                new Job("University Grad Software Engineer (USA)", "Pinterest", "pinterest.com");
        jobAnnualPay1.setAnnualPay(140000f);
        jobAnnualPay1.setSponsorship(true);
        jobAnnualPay1.setLocation(
                jobController.getLocationController().getLocationByZipCode("98101").getId());

        Job jobStarRating1 =
                new Job("Backend Software Engineer-QikQok", "QikQok", "careers.qikqok.com");
        jobStarRating1.setJobType(
                jobController.getJobTypeController().getJobTypeByLabel("parttime").getId());
        jobStarRating1.setStarRating(4.8f);

        Job jobExperience1 =
                new Job(
                        "Database Engineer (Early Career)",
                        "Apple",
                        "https://jobs.apple.com/en-us/details/200303605/database-engineer-early-career?team=SFTWR");
        jobExperience1.setExperience(
                jobController.getExperienceController().getExperienceByLabel("entry").getId());

        Job jobType2 = new Job("Backend Software Engineer (US)", "Sence", "sence.com");
        jobType2.setJobType(
                jobController.getJobTypeController().getJobTypeByLabel("fulltime").getId());

        Job jobCreated1 =
                new Job(
                        "Software Engineer - Visualization Pipeline",
                        "Salesforce",
                        "salesforce.com");
        jobCreated1.setCreated(LocalDate.of(2021, 12, 10));
        jobCreated1.setAnnualPay(98900f);
        jobCreated1.setJobType(
                jobController.getJobTypeController().getJobTypeByLabel("fulltime").getId());

        rawJobList = new ArrayList<>();
        rawJobList.add(jobAnnualPay1);
        rawJobList.add(jobStarRating1);
        rawJobList.add(jobType2);
        rawJobList.add(jobCreated1);
        rawJobList.add(jobExperience1);
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
    void testFilterJobs() {
        filteredJobList = filterCommand.filterJobs(rawJobList, "annualpay", "110000");
        assertThat(rawJobList.contains(jobAnnualPay));
        filteredJobList = filterCommand.filterJobs(rawJobList, "rating", "4f");
        assertThat(rawJobList.contains(jobStarRating));
        filteredJobList = filterCommand.filterJobs(rawJobList, "jobtype", "parttime");
        assertThat(rawJobList.contains(jobType));
        filteredJobList = filterCommand.filterJobs(rawJobList, "experience", "entry");
        assertThat(rawJobList.contains(jobStarRating));
        filteredJobList = filterCommand.filterJobs(rawJobList, "date_posted", "1 month");
        assertThat(rawJobList.contains(jobCreated));
        filteredJobList = filterCommand.filterJobs(rawJobList, "title", "Software Engineer");
        assertThat(rawJobList.contains(jobType));
        filteredJobList = filterCommand.filterJobs(rawJobList, "company", "Apple");
        assertThat(rawJobList.contains(jobExperience));
    }
}
