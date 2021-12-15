package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.utility.EmbedUtilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.Generated;
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
     * This method retrieves all jobs from job repository, sort them by creation date in descending
     * order, and then return the top X items (defined by global variable RESULT_SIZE) back to
     * discord, rendered in embed format.
     *
     * @param event discord command event.
     */
    @Override
    @Generated
    public void onSlashCommand(SlashCommandEvent event) {

        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());

        jobList = searchJobsInList(jobList);

        event.reply("Here are the jobs:").queue();
        Job job = null;
        for (Job j : jobList) {
            job = j;
            EmbedBuilder eb = EmbedUtilities.generateJobEmbed(job, jobController);
            event.getChannel().sendMessage(eb.build()).queue();
        }
    }

    @Override
    @Generated
    public CommandData getCommandData() {
        return new CommandData(this.getName(), "Search for jobs.");
    }

    public List<Job> searchJobsInList(List<Job> jobList) {

        Collections.sort(
                jobList,
                Comparator.comparing(
                                Job::getCreated, Comparator.nullsFirst(Comparator.naturalOrder()))
                        .reversed());
        return jobList.subList(0, RESULT_SIZE);
    }
}
