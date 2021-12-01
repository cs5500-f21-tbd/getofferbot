package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.model.Job;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * TestMongoCommand represents a Discord slashCommand that is used to verify job data can be stored
 * and retrieved from MongoDB. When users use slashCommand /testMongo on getofferbot, it is expected
 * that the bot returns the default jobs stored in JobController.java.
 */
// TODO: delete this command when the official slash command are implemented
public class TestMongoCommand implements Command {

    private JobController jobController;

    @Override
    public String getName() {
        return "testmongo";
    }

    /**
     * /testmongo slashCommand event handler.
     *
     * @return bot replies 5 default jobs stored in mongoDB without filter or order. If there are
     *     less than 5 jobs in mongoDB, bot will replies all the jobs.
     */
    // TODO: refactor this method.
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        event.reply("Here are the jobs:").queue();
        // Job job = null;
        // StringBuilder sb = new StringBuilder();
        // for (Job j : this.jobRepository.getAll()) {
        //     job = j;
        //     System.out.print(job.getJobTitle());
        //     sb.append(job.getJobTitle());
        //     sb.append('\n');
        // }
        // event.reply(sb.toString()).queue();

        Job job = null;
        int size = this.jobController.getJobRepository().getAll().size();
        int length = 0;
        if (size < 5) {
            length = size;
        } else {
            length = 5;
        }
        Object[] jobArray = this.jobController.getJobRepository().getAll().toArray();
        for (int i = 0; i < length; i++) {
            job = (Job) jobArray[i];
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
        return new CommandData(
                "testmongo", "verify that Job can be stored and retrieved from MongoDB");
    }

    @Override
    public void setJobController(JobController jobController) {
        this.jobController = jobController;
    }
}
