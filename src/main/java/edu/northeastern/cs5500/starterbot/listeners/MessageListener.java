package edu.northeastern.cs5500.starterbot.listeners;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.listeners.commands.*;
import java.util.HashMap;
import java.util.Map;

import lombok.Generated;
import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @Getter Map<String, Command> commands;

    public MessageListener(JobController jobController) {

        commands = new HashMap<>();

        Command sort = new SortCommand(jobController);
        commands.put(sort.getName(), sort);

        Command filter = new FilterCommand(jobController);
        commands.put(filter.getName(), filter);

        Command search = new SearchCommand(jobController);
        commands.put(search.getName(), search);

        Command help = new HelpCommand();
        commands.put(help.getName(), help);
    }

    @Override
    @Generated
    public void onSlashCommand(SlashCommandEvent event) {
        commands.get(event.getName()).onSlashCommand(event);
    }
}
