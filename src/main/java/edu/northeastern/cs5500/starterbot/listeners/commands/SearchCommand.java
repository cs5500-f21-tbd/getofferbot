package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.utility.EmbedUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class SearchCommand implements Command {

    private final JobController jobController;

    private static final int RESULT_SIZE = 10;

    public SearchCommand(JobController jobController) {
        this.jobController = jobController;
    }

    @Override
    public String getName() {
        return "search";
    }
    /**
     * This method retrieves all jobs from job repository, sort them by creation date in descending order, and then
     * return the top X items (defined by global variable RESULT_SIZE) back to discord, rendered in embed format.
     *
     * @param event discord command event.
     */
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        event.reply("Here are the jobs:").queue();

        this.jobController.getJobRepository().getAll().stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .limit(RESULT_SIZE)
                .forEach(
                        job -> {
                            EmbedBuilder embedBuilder =
                                    EmbedUtilities.generateJobEmbed(job, jobController);
                            event.getChannel().sendMessage(embedBuilder.build()).queue();
                        });
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(this.getName(), "Search for jobs.");
    }
}
