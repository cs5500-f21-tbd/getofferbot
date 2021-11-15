package edu.northeastern.cs5500.starterbot.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Zimeng (Parker) Xie, CS 5500, Fall 2021 A test utility class for future unit tests of Job and
 * Location classes. The content of this class will change pending progress of web scraping (for
 * now, it is manually generated)
 */
public class JobTestUtility {
    @Getter static List<Location> mockLocations = new ArrayList<>();
    @Getter static List<Job> mockJobs = new ArrayList<>();

    public static void generateLocations() {
        Location location1 = new Location(98101, "Seattle", "WA", "USA");
        Location location2 = new Location(10001, "New York", "NY", "USA");
        Location location3 = new Location(94404, "San Mateo", "CA", "USA");
        mockLocations.add(location1);
        mockLocations.add(location2);
        mockLocations.add(location3);
    }

    public static void generateJobs() {
        generateLocations();
        Job job1 =
                new Job(
                        1,
                        "University Grad Software Engineer (USA)",
                        "Full-time",
                        Experience.getExperienceByName("entry"),
                        "Pinterest",
                        LocalDate.of(2021, 8, 20),
                        80000f,
                        4.2f,
                        true,
                        "pinterest.com",
                        mockLocations.get(0));
        Job job2 =
                new Job(
                        2,
                        "Software Engineer",
                        "Full-time",
                        Experience.getExperienceByName("entry"),
                        "Splunk",
                        LocalDate.of(2021, 11, 12),
                        80000f,
                        4.0f,
                        true,
                        "splunk.com",
                        mockLocations.get(1));
        Job job3 =
                new Job(
                        3,
                        "Staff Software Engineer",
                        "Full-time",
                        Experience.getExperienceByName("senior"),
                        "Visa",
                        LocalDate.of(2021, 11, 9),
                        170000f,
                        4.1f,
                        true,
                        "visa.com",
                        mockLocations.get(2));
        mockJobs.add(job1);
        mockJobs.add(job2);
        mockJobs.add(job3);
    }
}
