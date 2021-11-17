package edu.northeastern.cs5500.starterbot.listeners;

import edu.northeastern.cs5500.starterbot.listeners.commands.Command;
import edu.northeastern.cs5500.starterbot.listeners.commands.SearchCommand;
import edu.northeastern.cs5500.starterbot.listeners.commands.SortCommand;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
    @Getter Map<String, Command> commands;

    public MessageListener() {
        commands = new HashMap<>();
        Command sort = new SortCommand();
        commands.put(sort.getName(), sort);
        Command search = new SearchCommand();
        commands.put(search.getName(), search);
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {

        if (commands.containsKey(event.getName())) {
            Command command = commands.get(event.getName());
            command.onSlashCommand(event);
            return;
        }

        switch (event.getName()) {
            case "say":
                String content = event.getOption("content").getAsString();
                event.reply(content).queue();
                break;

                // filter command handler
            case "filter":
                event.reply(
                                "Here are the jobs filtered based on "
                                        + event.getOption("category").getAsString())
                        .queue();
                // todo: change event.reply with filter function call
                break;

            case "help":
                event.reply("The help menu will be added soon...").queue();
                // todo: change event.reply with help function call
                break;

            case "search":
                event.reply("The stuff you are searching for will be added soon...").queue();
                // todo: change event.reply with search function call
                break;

            default:
                event.reply("I am busy thinking important stuff now...").setEphemeral(true).queue();
        }
    }
}
