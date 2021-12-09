package edu.northeastern.cs5500.starterbot.utility;

import edu.northeastern.cs5500.starterbot.controller.ExperienceController;
import edu.northeastern.cs5500.starterbot.controller.JobTypeController;
import edu.northeastern.cs5500.starterbot.controller.LocationController;
import edu.northeastern.cs5500.starterbot.model.Experience;
import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.model.JobType;
import edu.northeastern.cs5500.starterbot.model.Location;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;

/**
 * JobUtilities class contains methods that genrate various jobs covering each job attribute. When a
 * class wants to use a default job, it can call appropriate method in JobUtilities to generate a
 * Job with or without a specific attribute.
 */
@UtilityClass
public class JobUtilities {

    GenericRepository<Location> locationRepository = new InMemoryRepository<>();
    GenericRepository<Experience> experienceRepository = new InMemoryRepository<>();
    GenericRepository<JobType> jobTypeRepository = new InMemoryRepository<>();

    JobTypeController jobTypeController = new JobTypeController(jobTypeRepository);
    ExperienceController experienceController = new ExperienceController(experienceRepository);
    LocationController locationController = new LocationController(locationRepository);

    public Job generateJobAnnualPay1() {
        Job jobAnnualPay1 =
                new Job("University Grad Software Engineer (USA)", "Pinterest", "pinterest.com");
        jobAnnualPay1.setAnnualPay(140000f);
        jobAnnualPay1.setSponsorship(true);
        jobAnnualPay1.setLocation(locationController.getLocationByZipCode("98101").getId());
        return jobAnnualPay1;
    }

    public Job generateJobAnnualPay2() {
        Job jobAnnualPay2 = new Job("Software Engineer", "Splunk", "splunk.com");
        jobAnnualPay2.setCreated(LocalDate.of(2021, 11, 12));
        jobAnnualPay2.setAnnualPay(80000f);
        jobAnnualPay2.setLocation(locationController.getLocationByZipCode("10001").getId());
        return jobAnnualPay2;
    }

    public Job generateJobAnnualPay3() {
        Job jobAnnualPay3 = new Job("Software Engineer", "Indeed", "indeed.com");
        jobAnnualPay3.setJobType(jobTypeController.getJobTypeByLabel("fulltime").getId());
        jobAnnualPay3.setStarRating(4.3f);
        jobAnnualPay3.setAnnualPay(132000f);
        return jobAnnualPay3;
    }

    public Job generateJobAnnualPay4NoAnnualPay() {
        Job jobAnnualPay4NoAnnualPay =
                new Job("Software Engineer II - New Growth Channels", "Uber", "uber.com");
        jobAnnualPay4NoAnnualPay.setJobType(
                jobTypeController.getJobTypeByLabel("fulltime").getId());
        jobAnnualPay4NoAnnualPay.setStarRating(3.7f);
        jobAnnualPay4NoAnnualPay.setLocation(
                locationController.getLocationByZipCode("98101").getId());
        return jobAnnualPay4NoAnnualPay;
    }

    public Job generateJobStarRating1() {
        Job jobStarRating1 =
                new Job("Backend Software Engineer-TikTok", "TikTok", "careers.tiktok.com");
        jobStarRating1.setJobType(jobTypeController.getJobTypeByLabel("fulltime").getId());
        jobStarRating1.setStarRating(3.6f);
        return jobStarRating1;
    }

    public Job generateJobStarRating2() {
        Job jobStarRating2 = new Job("Software Engineer (Early Career)", "Apple", "apple.com");
        jobStarRating2.setStarRating(4.2f);
        jobStarRating2.setCreated(LocalDate.of(2021, 11, 10));
        return jobStarRating2;
    }

    public Job generateJobStarRating3() {
        Job jobStarRating3 =
                new Job("Software Engineer II - New Growth Channels", "Uber", "uber.com");
        jobStarRating3.setStarRating(3.7f);
        jobStarRating3.setLocation(locationController.getLocationByZipCode("98101").getId());
        return jobStarRating3;
    }

    public Job generateJobStarRating4NoStarRating() {
        Job jobStarRating4NoStarRating = new Job("Software Engineer", "Partnercubed", "indeed.com");
        return jobStarRating4NoStarRating;
    }

    public Job generateJobCreated1() {
        Job jobCreated1 =
                new Job(
                        "Software Engineer - Visualization Pipeline",
                        "Salesforce",
                        "salesforce.com");
        jobCreated1.setCreated(LocalDate.of(2021, 11, 29));
        jobCreated1.setAnnualPay(98900f);
        jobCreated1.setJobType(jobTypeController.getJobTypeByLabel("fulltime").getId());
        return jobCreated1;
    }

    public Job generateJobCreated2() {
        Job jobCreated2 = new Job("Software Engineer", "HP", "hp.com");
        jobCreated2.setCreated(LocalDate.of(2021, 11, 26));
        jobCreated2.setLocation(locationController.getLocationByZipCode("80528").getId());
        jobCreated2.setJobType(jobTypeController.getJobTypeByLabel("fulltime").getId());
        return jobCreated2;
    }

    public Job generateJobCreated3() {
        Job jobCreated3 = new Job("Software Engineer I/II", "Microsoft", "microsoft.com");
        jobCreated3.setCreated(LocalDate.of(2021, 11, 19));
        return jobCreated3;
    }

    public Job generateJobType1() {
        Job jobJobType1 = new Job("Software Engineer, TikTok Backend", "TikTok", "tiktok.com");
        jobJobType1.setJobType(jobTypeController.getJobTypeByLabel("fulltime").getId());
        return jobJobType1;
    }

    public Job generateJobType2() {
        Job jobJobType2 = new Job("Backend Software Engineer (US)", "Sence", "sence.com");
        jobJobType2.setJobType(jobTypeController.getJobTypeByLabel("fulltime").getId());
        return jobJobType2;
    }

    public Job generateJobType3() {
        Job jobJobType3 = new Job("Software Engineer II", "Microsoft", "microsoft.com");
        jobJobType3.setJobType(jobTypeController.getJobTypeByLabel("parttime").getId());
        return jobJobType3;
    }

    public Job generateJobType4() {
        Job jobJobType4 =
                new Job("CNC Machine Software Development", "Rogue Fabrication, LLC", "rogue.com");
        jobJobType4.setJobType(jobTypeController.getJobTypeByLabel("parttime").getId());
        return jobJobType4;
    }

    public Job generateJobType5NoJobType() {
        Job jobJobType5NoJobType =
                new Job("Software Development Engineer I", "Discovery,Inc.", "discovery.com");
        return jobJobType5NoJobType;
    }

    public Job generateJobLocation1() {
        Job jobLocation1 =
                new Job(
                        "Backend Software Development Engineer (All Levels) - Marketing Cloud",
                        "Salesforce",
                        "salesforce.com");
        jobLocation1.setLocation(locationController.getLocationByZipCode("98004").getId());
        return jobLocation1;
    }

    public Job generateJobLocation2() {
        Job jobLocation2 =
                new Job(
                        "Software Development Engineer",
                        "Verifone",
                        "https://www.verifone.com/en/careers/job-posting/4704060003?gh_jid=4704060003");
        jobLocation2.setLocation(locationController.getLocationByZipCode("60644").getId());
        return jobLocation2;
    }

    public Job generateJobLocation3() {
        Job jobLocation3 = new Job("Software Engineer-Backend", "Intuit", "intuit.com");
        jobLocation3.setLocation(locationController.getLocationByZipCode("83616").getId());
        return jobLocation3;
    }

    public Job generateJobLocation4NoLocation() {
        Job jobLocation4NoLocation =
                new Job(
                        "Software Development Engineer",
                        "Amazon.com",
                        "https://m5.apply.indeed.com/beta/indeedapply/form/contact-info");
        return jobLocation4NoLocation;
    }

    public Job generateJobExperience1() {
        Job jobExperience1 =
                new Job(
                        "Database Engineer (Early Career)",
                        "Apple",
                        "https://jobs.apple.com/en-us/details/200303605/database-engineer-early-career?team=SFTWR");
        jobExperience1.setExperience(experienceController.getExperienceByLabel("entry").getId());
        return jobExperience1;
    }

    public Job generateJobExperience2() {
        Job jobExperience2 =
                new Job(
                        "Resume Platform – Staff Software Development Engineer",
                        "Indeed",
                        "https://m5.apply.indeed.com/beta/indeedapply/form/contact-info");
        jobExperience2.setExperience(experienceController.getExperienceByLabel("mid").getId());
        return jobExperience2;
    }

    public Job generateJobExperience3() {
        Job jobExperience3 =
                new Job(
                        "Senior Software Engineer, Backend",
                        "Duolingo",
                        "https://boards.greenhouse.io/duolingo/jobs/5632759002?gh_src=08460e3a2us");
        jobExperience3.setExperience(experienceController.getExperienceByLabel("senior").getId());
        return jobExperience3;
    }

    public Job generateJobExperience4() {
        Job jobExperience4 =
                new Job("Software Engineer Intern", "Visual Concepts", "visualconcepts.com");
        jobExperience4.setExperience(experienceController.getExperienceByLabel("intern").getId());
        return jobExperience4;
    }

    public Job generateJobSponsorship1() {
        Job jobSponsorship1 =
                new Job("Software Engineer, Backend", "Capital One", "capitalone.com");
        return jobSponsorship1;
    }

    public Job generateJobSponsorship2() {
        Job jobSponsorship2 =
                new Job(
                        "Software Middleware Engineer II",
                        "Allegiant Airlines",
                        "https://jobs.lever.co/allegiantair/17019726-519b-4991-bbd5-95bdc976eb92");
        jobSponsorship2.setSponsorship(true);
        return jobSponsorship2;
    }

    public Job generateJobSponsorship3() {
        Job jobSponsorship3 =
                new Job(
                        "Software Engineer",
                        "John Deere",
                        "https://www.indeed.com/jobs?q=software%20engineer%20Visa%20Sponsorship&l&vjk=0e09a88674d30d5c");
        jobSponsorship3.setSponsorship(true);
        return jobSponsorship3;
    }
}
