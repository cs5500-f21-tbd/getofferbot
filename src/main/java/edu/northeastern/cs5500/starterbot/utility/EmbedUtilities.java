package edu.northeastern.cs5500.starterbot.utility;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.model.Job;
import java.awt.Color;
import lombok.Generated;
import lombok.experimental.UtilityClass;
import net.dv8tion.jda.api.EmbedBuilder;

/** EmbedUtilities class contains the methods that generate embed messages. */
@UtilityClass
@Generated
public class EmbedUtilities {

    /**
     * Genrates an EmbedBuilder based on input Job and Jobcontroller.
     *
     * @param job - Job, input job
     * @param jobController - JobController, input jobController
     * @return EmbedBuilder
     */
    public EmbedBuilder generateJobEmbed(Job job, JobController jobController) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(job.getJobTitle());
        embedBuilder.setColor(Color.CYAN);
        embedBuilder.addField("\uD83C\uDFE2 Company", job.getCompany(), false);
        embedBuilder.addField("\uD83D\uDD17 Link to apply", job.getLinkToApply(), false);
        if (job.getJobType() != null) {
            embedBuilder.addField(
                    "\uD83D\uDCBC Job type",
                    jobController
                            .getJobTypeController()
                            .getJobTypeRepository()
                            .get(job.getJobType())
                            .getDisplayName(),
                    false);
        }
        if (job.getExperience() != null) {
            embedBuilder.addField(
                    "\uD83D\uDCBB Experience",
                    jobController
                            .getExperienceController()
                            .getExperienceRepository()
                            .get(job.getExperience())
                            .getDisplayName(),
                    false);
        }

        if (job.getLocation() != null) {
            embedBuilder.addField(
                    "\uD83D\uDDFALocation",
                    EmbedUtilities.generateLocation(job, jobController),
                    false);
        }

        if (job.getCreated() != null) {
            embedBuilder.addField("\uD83D\uDCC5 Date posted", job.getCreated().toString(), false);
        }

        if (job.getSponsorship()) {
            embedBuilder.addField("\uD83D\uDCD1 Visa sponsorship", "Yes", false);
        }

        if (job.getAnnualPay() != null) {
            embedBuilder.addField(
                    "\uD83D\uDCB5 Annual pay", job.getAnnualPay().toString() + "+", false);
        }

        if (job.getStarRating() != null) {
            embedBuilder.addField("\uD83C\uDF1F Rating", job.getStarRating().toString(), false);
        }
        return embedBuilder;
    }

    /**
     * Create a location String based on an input job.
     *
     * @param job - Job, input job
     * @param jobController - JobController, input jobController associated with the job.
     * @return String - a string representing location
     */
    public String generateLocation(Job job, JobController jobController) {
        if (job.getLocation() == null) {
            return "";
        }
        String city =
                jobController
                        .getLocationController()
                        .getLocationRepository()
                        .get(job.getLocation())
                        .getCity();
        String state =
                jobController
                        .getLocationController()
                        .getLocationRepository()
                        .get(job.getLocation())
                        .getState();
        String zipCode =
                jobController
                        .getLocationController()
                        .getLocationRepository()
                        .get(job.getLocation())
                        .getZipCode();
        String country =
                jobController
                        .getLocationController()
                        .getLocationRepository()
                        .get(job.getLocation())
                        .getCountry();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(city)
                .append(", ")
                .append(state)
                .append(" ")
                .append(zipCode)
                .append(", ")
                .append(country);
        return stringBuilder.toString();
    }

    /**
     * Generates an EmbedBuilder that contains help menu for GetOfferBot.
     *
     * @return EmbedBuilder
     */
    public EmbedBuilder generateHelpEmbed() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("\uD83D\uDEE0 COMMANDS \uD83D\uDEE0");
        embedBuilder.setDescription("These are the current commands for GETOFFERBOT");
        embedBuilder.setColor(Color.ORANGE);
        embedBuilder.addField("/help", "Get a menu explaining how to use GetOfferBot", false);
        embedBuilder.addField(
                "/search", "Look up 10 SDE jobs ordered by post date by default", false);
        embedBuilder.addField("/sort", "Sort the jobs based on input keywords", false);
        embedBuilder.addField("OPTIONS for sort", "Required, only one option is allowed", false);
        embedBuilder.addField("annual pay", "/sort annual pay", true);
        embedBuilder.addField("rating", "/sort rating", true);
        embedBuilder.addField("post date", "/sort post date", true);
        embedBuilder.addField("location", "/sort location", true);
        embedBuilder.addField("/filter", "Filter the jobs base on one or more keywords", false);
        embedBuilder.addField(
                "OPTIONS for filter", "Required, one or more options are allowed", false);
        embedBuilder.addField("title", "/filter title", true);
        embedBuilder.addField("jobtype", "/filter jobtype", true);
        embedBuilder.addField("compnay", "/filter company", true);
        embedBuilder.addField("time_posted", "/filter time_posted", true);
        embedBuilder.addField("experience", "/filter experience", true);
        embedBuilder.addField("rating", "/filter rating", true);
        embedBuilder.addField("annualpay", "/filter annualpay", true);
        return embedBuilder;
    }
}
