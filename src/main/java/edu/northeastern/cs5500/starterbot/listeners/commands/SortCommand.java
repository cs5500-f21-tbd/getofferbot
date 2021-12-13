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

/**
 * SortCommand sort job data for users. When users use slashCommand /sort on getofferbot, it is
 * expected that the bot returns jobs sorted by user defined order.
 */
public class SortCommand implements Command {

    private JobController jobController;
    private static final int DEFAULT_SIZE = 5;

    public SortCommand(JobController jobController) {
        this.jobController = jobController;
    }

    @Override
    public String getName() {
        return "sort";
    }

    /**
     * This method retrieves all jobs from job repository, sort jobs by user input and then return
     * them back to discord, rendered in embed format
     *
     * @param event discord command event
     */
    @Override
    public void onSlashCommand(SlashCommandEvent event) {

        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        String category = event.getOption("category").getAsString();
        String order = event.getOption("order").getAsString();
        String inputSize = event.getOption("size").getAsString();

        jobList = sortJob(jobList, category, order, inputSize);

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
                new OptionData(OptionType.STRING, "category", "What category do you want to sort?");
        for (String choice : Arrays.asList("annual pay", "post date", "rating")) {
            categoryOptions.addChoice(choice, choice);
        }
        OptionData orderOptions =
                new OptionData(OptionType.STRING, "order", "What order do you want to sort?");
        for (String choice : Arrays.asList("des", "asc")) {
            orderOptions.addChoice(choice, choice);
        }
        OptionData sizeOptions =
                new OptionData(OptionType.STRING, "size", "how many jobs do you want to sort?");

        return new CommandData(this.getName(), "Sort jobs based on a category.")
                .addOptions(
                        categoryOptions.setRequired(true),
                        orderOptions.setRequired(true),
                        sizeOptions.setRequired(true));
    }

    /**
     * Sort Job according to different user input
     *
     * @param jobList List<Job>, all jobs from repository
     * @param category String, user defined category, decide which field of job we need to sort
     * @param order String, user defined order, asc for ascending order and des for descending order
     * @param inputSize String, user defined size, will be parse into an integer
     * @return a list of sorted job in user defined order
     */
    public List<Job> sortJob(List<Job> jobList, String category, String order, String inputSize) {

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
        }
        if (order.equals("asc")) {
            Collections.reverse(jobList);
        }
        Integer repoSize = jobList.size();
        Integer returnSize = sizeHandler(inputSize, repoSize);
        return jobList.subList(0, returnSize);
    }

    /**
     * Helper function to validate the input size.
     *
     * @param inputSize String, user defined size
     * @return true if the input size can be parsed to an integer, false otherwise.
     */
    public Boolean checkNumeric(String inputSize) {
        try {
            Double.parseDouble(inputSize);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Helper function to handle the input size, set a default return size as 5, only update to user
     * defined size when input is valid
     *
     * @param inputSize String, user defined size
     * @param repoSize Integer, the total number of jobs in repository
     * @return returnSize, a valid integer represent the size of returning jobs
     */
    public Integer sizeHandler(String inputSize, Integer repoSize) {
        Integer returnSize = DEFAULT_SIZE;
        if (checkNumeric(inputSize)) {
            returnSize = Integer.parseInt(inputSize);
        }
        if (returnSize > repoSize) {
            returnSize = repoSize;
        }
        return returnSize;
    }
}
