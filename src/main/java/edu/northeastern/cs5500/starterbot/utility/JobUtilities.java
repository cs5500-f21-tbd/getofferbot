package edu.northeastern.cs5500.starterbot.utility;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.model.Job;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;

/**
 * JobUtilities class contains methods that genrate various jobs covering each job attribute. When a
 * class wants to use a default job, it can call appropriate method in JobUtilities to generate a
 * Job with or without a specific attribute.
 */
@UtilityClass
public class JobUtilities {

    public Job generateJobAnnualPay1(JobController jobController) {
        Job jobAnnualPay1 =
                new Job("University Grad Software Engineer (USA)", "Pinterest", "pinterest.com");
        jobAnnualPay1.setAnnualPay(140000f);
        jobAnnualPay1.setSponsorship(true);
        jobAnnualPay1.setLocation(
                jobController.getLocationController().getLocationByZipCode("98101").getId());
        return jobAnnualPay1;
    }

    public Job generateJobAnnualPay2(JobController jobController) {
        Job jobAnnualPay2 = new Job("Software Engineer", "Plunk", "Plunk.com");
        jobAnnualPay2.setCreated(LocalDate.of(2021, 11, 12));
        jobAnnualPay2.setAnnualPay(135000f);
        jobAnnualPay2.setLocation(
                jobController.getLocationController().getLocationByZipCode("10001").getId());
        return jobAnnualPay2;
    }

    public Job generateJobAnnualPay3(JobController jobController) {
        Job jobAnnualPay3 = new Job("Software Engineer", "Inneed", "inneed.com");
        jobAnnualPay3.setStarRating(3.5f);
        jobAnnualPay3.setAnnualPay(132000f);
        return jobAnnualPay3;
    }

    public Job generateJobAnnualPay4NoAnnualPay(JobController jobController) {
        Job jobAnnualPay4NoAnnualPay =
                new Job("Software Engineer II - New Growth Channels", "Weber", "weber.com");
        jobAnnualPay4NoAnnualPay.setStarRating(3.7f);
        jobAnnualPay4NoAnnualPay.setLocation(
                jobController.getLocationController().getLocationByZipCode("98101").getId());
        return jobAnnualPay4NoAnnualPay;
    }

    public Job generateJobStarRating1(JobController jobController) {
        Job jobStarRating1 =
                new Job("Backend Software Engineer-QikQok", "QikQok", "careers.qikqok.com");
        jobStarRating1.setJobType(
                jobController.getJobTypeController().getJobTypeByLabel("fulltime").getId());
        jobStarRating1.setStarRating(4.8f);
        return jobStarRating1;
    }

    public Job generateJobStarRating2(JobController jobController) {
        Job jobStarRating2 = new Job("Software Engineer (Early Career)", "Apple", "apple.com");
        jobStarRating2.setStarRating(4.5f);
        jobStarRating2.setCreated(LocalDate.of(2021, 11, 10));
        return jobStarRating2;
    }

    public Job generateJobStarRating3(JobController jobController) {
        Job jobStarRating3 =
                new Job("Software Engineer II - New Growth Channels", "Uber", "uber.com");
        jobStarRating3.setStarRating(3.7f);
        jobStarRating3.setLocation(
                jobController.getLocationController().getLocationByZipCode("98101").getId());
        return jobStarRating3;
    }

    public Job generateJobStarRating4NoStarRating(JobController jobController) {
        Job jobStarRating4NoStarRating = new Job("Software Engineer", "Partnercubed", "indeed.com");
        return jobStarRating4NoStarRating;
    }

    public Job generateJobCreated1(JobController jobController) {
        Job jobCreated1 =
                new Job(
                        "Software Engineer - Visualization Pipeline",
                        "Salesforce",
                        "salesforce.com");
        jobCreated1.setCreated(LocalDate.of(2021, 12, 10));
        jobCreated1.setAnnualPay(98900f);
        jobCreated1.setJobType(
                jobController.getJobTypeController().getJobTypeByLabel("fulltime").getId());
        return jobCreated1;
    }

    public Job generateJobCreated2(JobController jobController) {
        Job jobCreated2 = new Job("Software Engineer", "HP", "hp.com");
        jobCreated2.setCreated(LocalDate.of(2021, 12, 5));
        jobCreated2.setLocation(
                jobController.getLocationController().getLocationByZipCode("80528").getId());
        jobCreated2.setJobType(
                jobController.getJobTypeController().getJobTypeByLabel("fulltime").getId());
        return jobCreated2;
    }

    public Job generateJobCreated3(JobController jobController) {
        Job jobCreated3 = new Job("Software Engineer I/II", "Microsoft", "microsoft.com");
        jobCreated3.setCreated(LocalDate.of(2021, 12, 1));
        return jobCreated3;
    }

    public Job generateJobType1(JobController jobController) {
        Job jobJobType1 = new Job("Software Engineer, TikTok Backend", "TikTok", "tiktok.com");
        jobJobType1.setJobType(
                jobController.getJobTypeController().getJobTypeByLabel("fulltime").getId());
        return jobJobType1;
    }

    public Job generateJobType2(JobController jobController) {
        Job jobJobType2 = new Job("Backend Software Engineer (US)", "Sence", "sence.com");
        jobJobType2.setJobType(
                jobController.getJobTypeController().getJobTypeByLabel("fulltime").getId());
        return jobJobType2;
    }

    public Job generateJobType3(JobController jobController) {
        Job jobJobType3 = new Job("Software Engineer II", "Microsoft", "microsoft.com");
        jobJobType3.setJobType(
                jobController.getJobTypeController().getJobTypeByLabel("parttime").getId());
        return jobJobType3;
    }

    public Job generateJobType4(JobController jobController) {
        Job jobJobType4 =
                new Job("CNC Machine Software Development", "Rogue Fabrication, LLC", "rogue.com");
        jobJobType4.setJobType(
                jobController.getJobTypeController().getJobTypeByLabel("parttime").getId());
        return jobJobType4;
    }

    public Job generateJobType5NoJobType(JobController jobController) {
        Job jobJobType5NoJobType =
                new Job("Software Development Engineer I", "Discovery,Inc.", "discovery.com");
        return jobJobType5NoJobType;
    }

    public Job generateJobLocation1(JobController jobController) {
        Job jobLocation1 =
                new Job(
                        "Backend Software Development Engineer (All Levels) - Marketing Cloud",
                        "Salesforce",
                        "salesforce.com");
        jobLocation1.setLocation(
                jobController.getLocationController().getLocationByZipCode("98004").getId());
        return jobLocation1;
    }

    public Job generateJobLocation2(JobController jobController) {
        Job jobLocation2 =
                new Job(
                        "Software Development Engineer",
                        "Verifone",
                        "https://www.verifone.com/en/careers/job-posting/4704060003?gh_jid=4704060003");
        jobLocation2.setLocation(
                jobController.getLocationController().getLocationByZipCode("60644").getId());
        return jobLocation2;
    }

    public Job generateJobLocation3(JobController jobController) {
        Job jobLocation3 = new Job("Software Engineer-Backend", "Intuit", "intuit.com");
        jobLocation3.setLocation(
                jobController.getLocationController().getLocationByZipCode("83616").getId());
        return jobLocation3;
    }

    public Job generateJobLocation4NoLocation(JobController jobController) {
        Job jobLocation4NoLocation =
                new Job(
                        "Software Development Engineer",
                        "Amazon.com",
                        "https://m5.apply.indeed.com/beta/indeedapply/form/contact-info");
        return jobLocation4NoLocation;
    }

    public Job generateJobExperience1(JobController jobController) {
        Job jobExperience1 =
                new Job(
                        "Database Engineer (Early Career)",
                        "Apple",
                        "https://jobs.apple.com/en-us/details/200303605/database-engineer-early-career?team=SFTWR");
        jobExperience1.setExperience(
                jobController.getExperienceController().getExperienceByLabel("entry").getId());
        return jobExperience1;
    }

    public Job generateJobExperience2(JobController jobController) {
        Job jobExperience2 =
                new Job(
                        "Resume Platform - Staff Software Development Engineer",
                        "Indeed",
                        "https://m5.apply.indeed.com/beta/indeedapply/form/contact-info");
        jobExperience2.setExperience(
                jobController.getExperienceController().getExperienceByLabel("mid").getId());
        return jobExperience2;
    }

    public Job generateJobExperience3(JobController jobController) {
        Job jobExperience3 =
                new Job(
                        "Senior Software Engineer, Backend",
                        "Duolingo",
                        "https://boards.greenhouse.io/duolingo/jobs/5632759002?gh_src=08460e3a2us");
        jobExperience3.setExperience(
                jobController.getExperienceController().getExperienceByLabel("senior").getId());
        return jobExperience3;
    }

    public Job generateJobExperience4(JobController jobController) {
        Job jobExperience4 =
                new Job("Software Engineer Intern", "Visual Concepts", "visualconcepts.com");
        jobExperience4.setExperience(
                jobController.getExperienceController().getExperienceByLabel("intern").getId());
        return jobExperience4;
    }

    public Job generateJobSponsorship1(JobController jobController) {
        Job jobSponsorship1 =
                new Job("Software Engineer, Backend", "Capital One", "capitalone.com");
        return jobSponsorship1;
    }

    public Job generateJobSponsorship2(JobController jobController) {
        Job jobSponsorship2 =
                new Job(
                        "Software Middleware Engineer II",
                        "Allegiant Airlines",
                        "https://jobs.lever.co/allegiantair/17019726-519b-4991-bbd5-95bdc976eb92");
        jobSponsorship2.setSponsorship(true);
        return jobSponsorship2;
    }

    public Job generateJobSponsorship3(JobController jobController) {
        Job jobSponsorship3 =
                new Job(
                        "Software Engineer",
                        "John Deere",
                        "https://www.indeed.com/jobs?q=software%20engineer%20Visa%20Sponsorship&l&vjk=0e09a88674d30d5c");
        jobSponsorship3.setSponsorship(true);
        return jobSponsorship3;
    }
}
