package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class SortCommand implements Command {

    private JobController jobController;

    @Override
    public String getName() {
        return "sort";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        event.reply(
                        "Here are the jobs sorted based on "
                                + event.getOption("category").getAsString())
                .queue();
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData("sort", "Sort jobs based on a category.")
                .addOptions(
                        new OptionData(
                                        OptionType.STRING,
                                        "category",
                                        "What category do you want to sort?")
                                .setRequired(true));
    }

    @Override
    public void setJobController(JobController jobController) {
        this.jobController = jobController;
    }
}
