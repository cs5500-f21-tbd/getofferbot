package edu.northeastern.cs5500.starterbot.listeners;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.controller.ExperienceController;
import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.controller.JobTypeController;
import edu.northeastern.cs5500.starterbot.controller.LocationController;
import edu.northeastern.cs5500.starterbot.listeners.commands.Command;
import edu.northeastern.cs5500.starterbot.listeners.commands.FilterCommand;
import edu.northeastern.cs5500.starterbot.listeners.commands.HelpCommand;
import edu.northeastern.cs5500.starterbot.listeners.commands.SearchCommand;
import edu.northeastern.cs5500.starterbot.listeners.commands.SortCommand;
import edu.northeastern.cs5500.starterbot.model.Experience;
import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.model.JobType;
import edu.northeastern.cs5500.starterbot.model.Location;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageListenerTest {

    InMemoryRepository<Job> jobRepository;
    InMemoryRepository<JobType> jobTypeRepository;
    InMemoryRepository<Experience> experienceRepository;
    InMemoryRepository<Location> locationRepository;
    JobTypeController jobTypeController;
    ExperienceController experienceController;
    LocationController locationController;
    JobController jobController;
    MessageListener messageListener;

    Command searchCommand;
    Command sortCommand;
    Command filterCommand;
    Command helpCommand;

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

        messageListener = new MessageListener(jobController);

        sortCommand = new SortCommand(jobController);

        filterCommand = new FilterCommand(jobController);

        searchCommand = new SearchCommand(jobController);

        helpCommand = new HelpCommand();
    }

    @Test
    void testCommandsHasHelpCommand() {
        assertThat(messageListener.getCommands().containsKey("help"));
        assertThat(messageListener.getCommands().containsValue(helpCommand));
    }

    @Test
    void testCommandsHasSortCommand() {
        assertThat(messageListener.getCommands().containsKey("sort"));
        assertThat(messageListener.getCommands().containsValue(sortCommand));
    }

    @Test
    void testCommandsHasSearchCommand() {
        assertThat(messageListener.getCommands().containsKey("search"));
        assertThat(messageListener.getCommands().containsValue(searchCommand));
    }

    @Test
    void testCommandsHasFilterCommand() {
        assertThat(messageListener.getCommands().containsKey("filter"));
        assertThat(messageListener.getCommands().containsValue(filterCommand));
    }
}
