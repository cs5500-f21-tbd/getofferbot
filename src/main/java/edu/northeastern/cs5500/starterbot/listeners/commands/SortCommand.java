package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.model.Job;
import java.awt.Color;
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

    @Override
    public String getName() {
        return "sort";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {

        List<Job> jobList = new ArrayList<Job>(this.jobController.getJobRepository().getAll());
        String category = event.getOption("category").getAsString();
        jobList = sortJob(jobList, category);

        event.reply("Here are the jobs sorted based on " + category + "\n").queue();
        Job job = null;
        for (Job j : jobList) {
            job = j;
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(job.getJobTitle());
            eb.setColor(Color.CYAN);
            eb.addField("Company", job.getCompany(), false);
            eb.addField("Link to apply", job.getLinkToApply(), false);
            if (job.getJobType() != null) {
                eb.addField(
                        "Job type",
                        this.jobController
                                .getJobTypeController()
                                .getJobTypeRepository()
                                .get(job.getJobType())
                                .toString(),
                        false);
            }
            if (job.getExperience() != null) {
                eb.addField(
                        "Experience",
                        this.jobController
                                .getExperienceController()
                                .getExperienceRepository()
                                .get(job.getExperience())
                                .toString(),
                        false);
            }

            if (job.getLocation() != null) {
                eb.addField(
                        "Location",
                        this.jobController
                                .getLocationController()
                                .getLocationRepository()
                                .get(job.getLocation())
                                .toString(),
                        false);
            }

            if (job.getCreated() != null) {
                eb.addField("Date posted", job.getCreated().toString(), false);
            }

            if (job.getSponsorship()) {
                eb.addField("Visa sponsorship", "Yes", false);
            }

            if (job.getAnnualPay() != null) {
                eb.addField("Annual pay", job.getAnnualPay().toString() + "+", false);
            }

            if (job.getStarRating() != null) {
                eb.addField("Rating", job.getStarRating().toString(), false);
            }
            event.getChannel().sendMessage(eb.build()).queue();
        }
    }

    @Override
    public CommandData getCommandData() {
        OptionData categoryOptions =
                new OptionData(OptionType.STRING, "category", "What category do you want to sort?")
                        .setRequired(true);
        for (String choice : Arrays.asList("annual pay", "location", "post date", "rating")) {
            categoryOptions.addChoice(choice, choice);
        }

        return new CommandData("sort", "Sort jobs based on a category.")
                .addOptions(categoryOptions.setRequired(true));
    }

    @Override
    public void setJobController(JobController jobController) {
        this.jobController = jobController;
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
