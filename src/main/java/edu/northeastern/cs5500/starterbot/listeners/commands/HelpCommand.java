package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.utility.EmbedUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class HelpCommand implements Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        EmbedBuilder embedBuilder = EmbedUtilities.generateHelpEmbed();
        event.replyEmbeds(embedBuilder.build()).queue();
    }

    @Override
    //
    public CommandData getCommandData() {
        return new CommandData("help", "Help menu of the bot");
    }
}
