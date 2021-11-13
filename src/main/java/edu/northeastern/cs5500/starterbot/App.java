package edu.northeastern.cs5500.starterbot;

import static spark.Spark.*;

import edu.northeastern.cs5500.starterbot.listeners.MessageListener;
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

        JDA jda =
                JDABuilder.createLight(token, EnumSet.noneOf(GatewayIntent.class))
                        .addEventListeners(new MessageListener())
                        .build();

        CommandListUpdateAction commands = jda.updateCommands();

        commands.addCommands(
                new CommandData("say", "Makes the bot say what you told it to say")
                        .addOptions(
                                new OptionData(
                                                OptionType.STRING,
                                                "content",
                                                "What the bot should say")
                                        .setRequired(true)));

        // sort command
        commands.addCommands(
                new CommandData("sort", "Sort jobs based on a category.")
                        .addOptions(
                                new OptionData(
                                                OptionType.STRING,
                                                "category",
                                                "What category do you want to sort?")
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

        port(8080);

        get(
                "/",
                (request, response) -> {
                    return "{\"status\": \"OK\"}";
                });
    }
}
