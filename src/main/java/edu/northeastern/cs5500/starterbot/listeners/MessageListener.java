package edu.northeastern.cs5500.starterbot.listeners;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        switch (event.getName()) {
            case "say":
                event.reply(event.getOption("content").getAsString()).queue();
                break;

                // sort command handler
            case "sort":
                event.reply(
                                "Here are the jobs sorted based on "
                                        + event.getOption("category").getAsString())
                        .queue();
                // todo: change event.reply with sort function call
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
