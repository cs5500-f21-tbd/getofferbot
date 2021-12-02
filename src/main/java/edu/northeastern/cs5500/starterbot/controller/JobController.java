package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import java.time.LocalDate;
import java.util.Collection;
import lombok.Data;

/**
 * This is the controller class for Job, which, in conjunction with ExperienceController,
 * JobTypeController, and LocationController, converts job-related inputs into concrete Job objects.
 * Also creates default cases upon initialization with an empty repo.
 */
@Data
public class JobController {
    GenericRepository<Job> jobRepository;
    JobTypeController jobTypeController;
    ExperienceController experienceController;
    LocationController locationController;

    public JobController(
            GenericRepository<Job> jobRepository,
            JobTypeController jobTypeController,
            ExperienceController experienceController,
            LocationController locationController) {
        this.jobRepository = jobRepository;
        this.jobTypeController = jobTypeController;
        this.experienceController = experienceController;
        this.locationController = locationController;

        createDefaults();
    }

    /** Create default cases for jobs. */
    private void createDefaults() {
        if (jobRepository.count() > 0) {
            // Only create default jobs if none exist
            return;
        }

        Job jobAnnualPay1 =
                new Job("University Grad Software Engineer (USA)", "Pinterest", "pinterest.com");
        jobAnnualPay1.setAnnualPay(140000f);
        jobAnnualPay1.setSponsorship(true);
        jobAnnualPay1.setLocation(locationController.getLocationByZipCode("98101").getId());

        Job jobAnnualPay2 = new Job("Software Engineer", "Splunk", "splunk.com");
        jobAnnualPay2.setCreated(LocalDate.of(2021, 11, 12));
        jobAnnualPay2.setAnnualPay(80000f);
        jobAnnualPay2.setLocation(locationController.getLocationByZipCode("10001").getId());

        Job jobAnnualPay3 = new Job("Software Engineer", "Indeed", "indeed.com");
        jobAnnualPay3.setJobType(jobTypeController.getJobTypeByLabel("fulltime").getId());
        jobAnnualPay3.setStarRating(4.3f);
        jobAnnualPay3.setAnnualPay(132000f);

        Job jobAnnualPay4NoAnnualPay =
                new Job("Software Engineer II - New Growth Channels", "Uber", "uber.com");
        jobAnnualPay4NoAnnualPay.setJobType(
                jobTypeController.getJobTypeByLabel("fulltime").getId());
        jobAnnualPay4NoAnnualPay.setStarRating(3.7f);
        jobAnnualPay4NoAnnualPay.setLocation(
                locationController.getLocationByZipCode("98101").getId());

        Job jobStarRating1 =
                new Job("Backend Software Engineer-TikTok", "TikTok", "careers.tiktok.com");
        jobStarRating1.setJobType(jobTypeController.getJobTypeByLabel("fulltime").getId());
        jobStarRating1.setStarRating(3.6f);

        Job jobStarRating2 = new Job("Software Engineer (Early Career)", "Apple", "apple.com");
        jobStarRating2.setStarRating(4.2f);
        jobStarRating2.setCreated(LocalDate.of(2021, 11, 10));

        Job jobStarRating3 =
                new Job("Software Engineer II - New Growth Channels", "Uber", "uber.com");
        jobStarRating3.setStarRating(3.7f);
        jobStarRating3.setLocation(locationController.getLocationByZipCode("98101").getId());

        Job jobStarRating4NoStarRating = new Job("Software Engineer", "Partnercubed", "indeed.com");

        Job jobCreated1 =
                new Job(
                        "Software Engineer - Visualization Pipeline",
                        "Salesforce",
                        "salesforce.com");
        jobCreated1.setCreated(LocalDate.of(2021, 11, 29));
        jobCreated1.setAnnualPay(98900f);
        jobCreated1.setJobType(jobTypeController.getJobTypeByLabel("fulltime").getId());

        Job jobCreated2 = new Job("Software Engineer", "HP", "hp.com");
        jobCreated2.setCreated(LocalDate.of(2021, 11, 26));
        jobCreated2.setLocation(locationController.getLocationByZipCode("80528").getId());
        jobCreated2.setJobType(jobTypeController.getJobTypeByLabel("fulltime").getId());

        Job jobCreated3 = new Job("Software Engineer I/II", "Microsoft", "microsoft.com");
        jobCreated3.setCreated(LocalDate.of(2021, 11, 19));

        Job jobJobType1 = new Job("Software Engineer, TikTok Backend", "TikTok", "tiktok.com");
        jobJobType1.setJobType(jobTypeController.getJobTypeByLabel("fulltime").getId());

        Job jobJobType2 = new Job("Backend Software Engineer (US)", "Sence", "sence.com");
        jobJobType2.setJobType(jobTypeController.getJobTypeByLabel("fulltime").getId());

        Job jobJobType3 = new Job("Software Engineer II", "Microsoft", "microsoft.com");
        jobJobType3.setJobType(jobTypeController.getJobTypeByLabel("parttime").getId());

        Job jobJobType4 =
                new Job("CNC Machine Software Development", "Rogue Fabrication, LLC", "rogue.com");
        jobJobType4.setJobType(jobTypeController.getJobTypeByLabel("parttime").getId());

        Job jobJobType5NoJobType =
                new Job("Software Development Engineer I", "Discovery,Inc.", "discovery.com");

        Job jobLocation1 =
                new Job(
                        "Backend Software Development Engineer (All Levels) - Marketing Cloud",
                        "Salesforce",
                        "salesforce.com");
        jobLocation1.setLocation(locationController.getLocationByZipCode("98004").getId());

        Job jobLocation2 =
                new Job(
                        "Software Development Engineer",
                        "Verifone",
                        "https://www.verifone.com/en/careers/job-posting/4704060003?gh_jid=4704060003");
        jobLocation2.setLocation(locationController.getLocationByZipCode("60644").getId());

        Job jobLocation3 = new Job("Software Engineer-Backend", "Intuit", "intuit.com");
        jobLocation3.setLocation(locationController.getLocationByZipCode("83616").getId());

        Job jobLocation4NoLocation =
                new Job(
                        "Software Development Engineer",
                        "Amazon.com",
                        "https://m5.apply.indeed.com/beta/indeedapply/form/contact-info");

        Job jobExperience1 =
                new Job(
                        "Database Engineer (Early Career)",
                        "Apple",
                        "https://jobs.apple.com/en-us/details/200303605/database-engineer-early-career?team=SFTWR");
        jobExperience1.setExperience(experienceController.getExperienceByLabel("entry").getId());

        Job jobExperience2 =
                new Job(
                        "Resume Platform – Staff Software Development Engineer",
                        "Indeed",
                        "https://m5.apply.indeed.com/beta/indeedapply/form/contact-info");
        jobExperience2.setExperience(experienceController.getExperienceByLabel("mid").getId());

        Job jobExperience3 =
                new Job(
                        "Senior Software Engineer, Backend",
                        "Duolingo",
                        "https://boards.greenhouse.io/duolingo/jobs/5632759002?gh_src=08460e3a2us");
        jobExperience2.setExperience(experienceController.getExperienceByLabel("senior").getId());

        Job jobExperience4 =
                new Job("Software Engineer Intern", "Visual Concepts", "visualconcepts.com");
        jobExperience2.setExperience(experienceController.getExperienceByLabel("intern").getId());

        Job jobSponsorship1 =
                new Job("Software Engineer, Backend", "Capital One", "capitalone.com");

        Job jobSponsorship2 =
                new Job(
                        "Software Middleware Engineer II",
                        "Allegiant Airlines",
                        "https://jobs.lever.co/allegiantair/17019726-519b-4991-bbd5-95bdc976eb92");
        jobSponsorship2.setSponsorship(true);

        Job jobSponsorship3 =
                new Job(
                        "Software Engineer",
                        "John Deere",
                        "https://www.indeed.com/jobs?q=software%20engineer%20Visa%20Sponsorship&l&vjk=0e09a88674d30d5c");
        jobSponsorship3.setSponsorship(true);

        jobRepository.add(jobAnnualPay1);
        jobRepository.add(jobAnnualPay2);
        jobRepository.add(jobAnnualPay3);
        jobRepository.add(jobAnnualPay4NoAnnualPay);
        jobRepository.add(jobStarRating1);
        jobRepository.add(jobStarRating2);
        jobRepository.add(jobStarRating3);
        jobRepository.add(jobStarRating4NoStarRating);
        jobRepository.add(jobCreated1);
        jobRepository.add(jobCreated2);
        jobRepository.add(jobCreated3);
        jobRepository.add(jobJobType1);
        jobRepository.add(jobJobType2);
        jobRepository.add(jobJobType3);
        jobRepository.add(jobJobType4);
        jobRepository.add(jobJobType5NoJobType);
        jobRepository.add(jobLocation1);
        jobRepository.add(jobLocation2);
        jobRepository.add(jobLocation3);
        jobRepository.add(jobLocation4NoLocation);
        jobRepository.add(jobExperience1);
        jobRepository.add(jobExperience2);
        jobRepository.add(jobExperience3);
        jobRepository.add(jobExperience4);
        jobRepository.add(jobSponsorship1);
        jobRepository.add(jobSponsorship2);
        jobRepository.add(jobSponsorship3);
    }

    /**
     * Collect all instances of Jobs in the repo into a Collection
     *
     * @return a Collection of Jobs
     */
    public Collection<Job> getAll() {
        return jobRepository.getAll();
    }
}
