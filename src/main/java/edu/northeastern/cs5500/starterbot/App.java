package edu.northeastern.cs5500.starterbot;

import static spark.Spark.*;

import edu.northeastern.cs5500.starterbot.controller.ExperienceController;
import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.controller.JobTypeController;
import edu.northeastern.cs5500.starterbot.controller.LocationController;
import edu.northeastern.cs5500.starterbot.listeners.MessageListener;
import edu.northeastern.cs5500.starterbot.listeners.commands.Command;
import edu.northeastern.cs5500.starterbot.model.Experience;
import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.model.JobType;
import edu.northeastern.cs5500.starterbot.model.Location;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import edu.northeastern.cs5500.starterbot.repository.MongoDBRepository;
import edu.northeastern.cs5500.starterbot.service.MongoDBService;
import java.util.EnumSet;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public class App {

    static String getBotToken() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        String token = processBuilder.environment().get("BOT_TOKEN");
        return token;
    }

    public static void main(String[] arg) throws LoginException {
        String token = getBotToken();
        if (token == null) {
            throw new IllegalArgumentException(
                    "The BOT_TOKEN environment variable is not defined.");
        }
        MessageListener messageListener = new MessageListener();
        GenericRepository<Job> jobRepository = new InMemoryRepository<>();
        GenericRepository<Location> locationRepository = new InMemoryRepository<>();
        GenericRepository<Experience> experienceRepository = new InMemoryRepository<>();
        GenericRepository<JobType> jobTypeRepository = new InMemoryRepository<>();

        JobTypeController jobTypeController = new JobTypeController(jobTypeRepository);
        ExperienceController experienceController = new ExperienceController(experienceRepository);
        LocationController locationController = new LocationController(locationRepository);
        JobController jobController =
                new JobController(
                        jobRepository, jobTypeController, experienceController, locationController);

        MongoDBService mongoDBService = new MongoDBService();
        GenericRepository<Job> mongoJobRepository =
                new MongoDBRepository<Job>(Job.class, mongoDBService);

        if (mongoJobRepository.count() == 0) {
            mongoJobRepository.addMany(jobController.getAll());
        }

        messageListener.getCommands().get("testmongo").setJobRepository(mongoJobRepository);

        JDA jda =
                JDABuilder.createLight(token, EnumSet.noneOf(GatewayIntent.class))
                        .addEventListeners(messageListener)
                        .build();

        updateCommandList(jda, messageListener);

        port(8080);

        get(
                "/",
                (request, response) -> {
                    return "{\"status\": \"OK\"}";
                });
    }

    private static void updateCommandList(JDA jda, MessageListener messageListener) {
        CommandListUpdateAction commands = jda.updateCommands();

        for (Command command : messageListener.getCommands().values()) {
            commands.addCommands(command.getCommandData());
        }

        commands.addCommands(
                new CommandData("say", "Makes the bot say what you told it to say")
                        .addOptions(
                                new OptionData(
                                                OptionType.STRING,
                                                "content",
                                                "What the bot should say")
                                        .setRequired(true)));

        // filter command
        commands.addCommands(
                new CommandData("filter", "Filter jobs based on one or more categories.")
                        .addOptions(
                                new OptionData(
                                                OptionType.STRING,
                                                "category",
                                                "What category do you want to filter?")
                                        .setRequired(true)));

        // help command
        commands.addCommands(new CommandData("help", "Get Help"));

        // search command
        commands.addCommands(new CommandData("search", "Search for your dream job"));

        commands.queue();
    }
}
