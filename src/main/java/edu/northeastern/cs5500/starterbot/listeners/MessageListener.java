package edu.northeastern.cs5500.starterbot.listeners;

import edu.northeastern.cs5500.starterbot.listeners.commands.*;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    Command defaultCommand = new DefaultCommand();

    @Getter Map<String, Command> commands;

    public MessageListener() {

        commands = new HashMap<>();

        Command sort = new SortCommand();
        commands.put(sort.getName(), sort);

        Command filter = new FilterCommand(jobController);
        commands.put(filter.getName(), filter);

        Command search = new SearchCommand();
        commands.put(search.getName(), search);

        Command testMongo = new TestMongoCommand();
        commands.put(testMongo.getName(), testMongo);

        Command help = new HelpCommand();
        commands.put(help.getName(), help);
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        commands.getOrDefault(event.getName(), defaultCommand).onSlashCommand(event);
    }
}
