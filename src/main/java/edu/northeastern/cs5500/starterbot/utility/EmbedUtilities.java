package edu.northeastern.cs5500.starterbot.utility;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.model.Job;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;

/** EmbedUtilities class contains the methods that generate embed messages. */
public final class EmbedUtilities {

    /**
     * Genrates an EmbedBuilder based on input Job and Jobcontroller.
     *
     * @param job - Job, input job
     * @param jobController - JobController, input jobController
     * @return EmbedBuilder
     */
    public static EmbedBuilder generateJobEmbed(Job job, JobController jobController) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(job.getJobTitle());
        embedBuilder.setColor(Color.CYAN);
        embedBuilder.addField("Company", job.getCompany(), false);
        embedBuilder.addField("Link to apply", job.getLinkToApply(), false);
        if (job.getJobType() != null) {
            embedBuilder.addField(
                    "Job type",
                    jobController
                            .getJobTypeController()
                            .getJobTypeRepository()
                            .get(job.getJobType())
                            .toString(),
                    false);
        }
        if (job.getExperience() != null) {
            embedBuilder.addField(
                    "Experience",
                    jobController
                            .getExperienceController()
                            .getExperienceRepository()
                            .get(job.getExperience())
                            .toString(),
                    false);
        }

        if (job.getLocation() != null) {
            embedBuilder.addField(
                    "Location",
                    jobController
                            .getLocationController()
                            .getLocationRepository()
                            .get(job.getLocation())
                            .toString(),
                    false);
        }

        if (job.getCreated() != null) {
            embedBuilder.addField("Date posted", job.getCreated().toString(), false);
        }

        if (job.getSponsorship()) {
            embedBuilder.addField("Visa sponsorship", "Yes", false);
        }

        if (job.getAnnualPay() != null) {
            embedBuilder.addField("Annual pay", job.getAnnualPay().toString() + "+", false);
        }

        if (job.getStarRating() != null) {
            embedBuilder.addField("Rating", job.getStarRating().toString(), false);
        }
        return embedBuilder;
    }

    /**
     * Generates an EmbedBuilder that contains help menu for GetOfferBot.
     *
     * @return EmbedBuilder
     */
    public static EmbedBuilder generateHelpEmbed() {
        // TODO: polish embed message such as adding emoji and using some fun fonts.
        // TODO: make sure commands and options in help menu matches the features supported by final
        // version
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("COMMANDS");
        embedBuilder.setDescription("There are the current commands for GETOFFERBOT");
        embedBuilder.setColor(Color.ORANGE);
        embedBuilder.addField("/help", "Get a menu explaining how to use GetOfferBot", false);
        embedBuilder.addField(
                "/search", "Look up 10 SDE jobs ordered by post date by default", false);
        // embedBuilder.addField("OPTIONS", "optional options for search", false);
        // embedBuilder.addField("level:\tthe level of the job\t/search --level-intern", "", false);
        // embedBuilder.addField("  ", "", true);
        embedBuilder.addField("/sort", "Sort the jobs based on input keywords", false);
        embedBuilder.addField("OPTIONS for sort", "required, only one option is allowed", false);
        // embedBuilder.addField("\t", "", true);
        embedBuilder.addField("annual pay", "/sort annual pay", true);
        // embedBuilder.addField("\t", "", true);
        // embedBuilder.addField("\t", "", false);
        embedBuilder.addField("rating", "/sort rating", true);
        embedBuilder.addField("post date", "/sort post date", true);
        embedBuilder.addField("location", "/sort location", true);

        embedBuilder.addField("/filter", "Filter the jobs base on one or more keywords", false);
        embedBuilder.addField(
                "OPTIONS for filter", "required, one or more options are allowed", false);
        embedBuilder.addField("experience", "/filter experience", true);
        embedBuilder.addField("level", "/filter level", true);
        return embedBuilder;
    }
}
