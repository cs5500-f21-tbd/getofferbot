package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * Note that this should not be added to the command chain.
 * @param event
 */
public class DefaultCommand implements Command {

    private GenericRepository<Job> jobRepository;

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        String content = String.format("Command %s is not supported, type /help for supported commands.",
                event.getName());

        event.reply(content).queue();
    }

    @Override
    //
    public CommandData getCommandData() {
        return null;
    }

    @Override
    public void setJobRepository(GenericRepository<Job> jobRepository) {
        this.jobRepository = jobRepository;
    }
}
