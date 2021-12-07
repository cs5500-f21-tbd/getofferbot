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
import edu.northeastern.cs5500.starterbot.repository.MongoDBRepository;
import edu.northeastern.cs5500.starterbot.service.MongoDBService;
import java.util.EnumSet;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
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
        MongoDBService mongoDBService = new MongoDBService();

        GenericRepository<Job> mongoJobRepository =
                new MongoDBRepository<>(Job.class, mongoDBService);
        GenericRepository<Location> mongoLocationRepository =
                new MongoDBRepository<>(Location.class, mongoDBService);
        GenericRepository<Experience> mongoExperienceRepository =
                new MongoDBRepository<>(Experience.class, mongoDBService);
        GenericRepository<JobType> mongoJobTypeRepository =
                new MongoDBRepository<>(JobType.class, mongoDBService);

        JobTypeController mongoJobTypeController = new JobTypeController(mongoJobTypeRepository);
        ExperienceController mongoExperienceController =
                new ExperienceController(mongoExperienceRepository);
        LocationController mongoLocationController =
                new LocationController(mongoLocationRepository);

        JobController mongoJobController =
                new JobController(
                        mongoJobRepository,
                        mongoJobTypeController,
                        mongoExperienceController,
                        mongoLocationController);
                        
        MessageListener messageListener = new MessageListener(mongoJobController);

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

        commands.queue();
    }
}
