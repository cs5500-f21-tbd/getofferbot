// TODO : continue sort command test

// package edu.northeastern.cs5500.starterbot.listeners.commands;

// import static com.google.common.truth.Truth.assertThat;

// import edu.northeastern.cs5500.starterbot.controller.ExperienceController;
// import edu.northeastern.cs5500.starterbot.controller.JobController;
// import edu.northeastern.cs5500.starterbot.controller.JobTypeController;
// import edu.northeastern.cs5500.starterbot.controller.LocationController;
// import edu.northeastern.cs5500.starterbot.model.Experience;
// import edu.northeastern.cs5500.starterbot.model.Job;
// import edu.northeastern.cs5500.starterbot.model.JobType;
// import edu.northeastern.cs5500.starterbot.model.Location;
// import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
// import edu.northeastern.cs5500.starterbot.utility.JobUtilities;
// import java.util.ArrayList;
// import java.util.List;
// import net.dv8tion.jda.api.JDA;
// import org.junit.Before;
// import org.junit.jupiter.api.Test;

// public class SortCommandTest {

//     InMemoryRepository<Job> jobRepoForSortRatingTest;
//     InMemoryRepository<JobType> jobTypeRepository;
//     InMemoryRepository<Experience> experienceRepository;
//     InMemoryRepository<Location> locationRepository;
//     JobTypeController jobTypeController;
//     ExperienceController experienceController;
//     LocationController locationController;

//     JDA jda;

//     @Before
//     void setUpReposAndControllers() {
//         jobTypeRepository = new InMemoryRepository<>();
//         experienceRepository = new InMemoryRepository<>();
//         locationRepository = new InMemoryRepository<>();
//         jobTypeController = new JobTypeController(jobTypeRepository);
//         experienceController = new ExperienceController(experienceRepository);
//         locationController = new LocationController(locationRepository);
//     }

//     @Test
//     void testSortJobByRating() {
//         jobRepoForSortRatingTest = new InMemoryRepository<>();
//         jobRepoForSortRatingTest.add(JobUtilities.generateJobStarRating1());
//         jobRepoForSortRatingTest.add(JobUtilities.generateJobStarRating2());
//         jobRepoForSortRatingTest.add(JobUtilities.generateJobStarRating3());

//         JobController jobControllerForSortRatingTest =
//                 new JobController(
//                         jobRepoForSortRatingTest,
//                         jobTypeController,
//                         experienceController,
//                         locationController);
//         List<Job> actualJobList = new ArrayList<>(jobRepoForSortRatingTest.getAll());
//         List<Job> expectedJobList = new ArrayList<>();
//         expectedJobList.add(0, JobUtilities.generateJobStarRating2());
//         expectedJobList.add(1, JobUtilities.generateJobStarRating3());
//         expectedJobList.add(2, JobUtilities.generateJobStarRating1());

//         SortCommand sortCommand = new SortCommand(jobControllerForSortRatingTest);
//         String testCategory = "rating";

//         List<Job> actualSortedJobList =
//                 sortCommand.sortJob(actualJobList, testCategory, "dec", "3");
//         assertThat(actualSortedJobList).isEqualTo(expectedJobList);
//     }
// }
