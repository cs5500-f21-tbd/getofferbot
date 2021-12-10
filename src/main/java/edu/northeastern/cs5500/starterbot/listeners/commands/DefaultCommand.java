package edu.northeastern.cs5500.starterbot.listeners.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * Note that this should not be added to the command chain.
 *
 * @param event
 */
public class DefaultCommand implements Command {

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        String content =
                String.format(
                        "Command %s is not supported, type /help for supported commands.",
                        event.getName());

        event.reply(content).queue();
    }

    @Override
    //
    public CommandData getCommandData() {
        return null;
    }
}
