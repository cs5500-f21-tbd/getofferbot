package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.utility.EmbedUtilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class SortCommand implements Command {

    private JobController jobController;

    public SortCommand(JobController jobController) {
        this.jobController = jobController;
    }

    @Override
    public String getName() {
        return "sort";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {

        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        String category = event.getOption("category").getAsString();
        jobList = sortJob(jobList, category);

        event.reply("Here are the jobs sorted based on " + category + "\n").queue();
        Job job = null;
        for (Job j : jobList) {
            job = j;
            EmbedBuilder embedBuilder = EmbedUtilities.generateJobEmbed(job, jobController);
            event.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }

    @Override
    public CommandData getCommandData() {
        OptionData categoryOptions =
                new OptionData(
                                OptionType.STRING,
                                "category",
                                "What category do you want to sort? (Try 'annual pay', 'location', 'post date', or 'rating')")
                        .setRequired(true);
        for (String choice : Arrays.asList("annual pay", "location", "post date", "rating")) {
            categoryOptions.addChoice(choice, choice);
        }

        return new CommandData("sort", "Sort jobs based on a category.")
                .addOptions(categoryOptions.setRequired(true));
    }

    // TODO: 1.add parameter to allow users change the size
    // TODO: 2. add parameter to allow users choose the order （asc/des)

    private List<Job> sortJob(List<Job> jobList, String category) {

        if (category.equals("annual pay")) {
            Collections.sort(
                    jobList,
                    Comparator.comparing(
                                    Job::getAnnualPay,
                                    Comparator.nullsFirst(Comparator.naturalOrder()))
                            .reversed());

        } else if (category.equals("rating")) {
            Collections.sort(
                    jobList,
                    Comparator.comparing(
                                    Job::getStarRating,
                                    Comparator.nullsFirst(Comparator.naturalOrder()))
                            .reversed());
        } else if (category.equals("post date")) {
            Collections.sort(
                    jobList,
                    Comparator.comparing(
                                    Job::getCreated,
                                    Comparator.nullsFirst(Comparator.naturalOrder()))
                            .reversed());
            // TODO: implement sort by location
        } else if (category.equals("location")) {
            return new ArrayList<>();
        }

        int size = 5;
        if (jobList.size() < size) {
            size = jobList.size();
        }
        return jobList.subList(0, size);
    }
}
