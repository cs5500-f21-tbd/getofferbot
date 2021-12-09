package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.model.Job;
import java.awt.Color;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class FilterCommand implements Command {

    private JobController jobController;
    private List<String> commandList;

    public FilterCommand(JobController jobController) {
        this.jobController = jobController;
        commandList =
                Arrays.asList(
                        "title",
                        "jobtype",
                        "company",
                        "distance",
                        "time_posted",
                        "experience",
                        "rating",
                        "annualpay",
                        "visa");

    }

    @Override
    public String getName() {
        return "filter";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        // String option = event.getOption("category").getAsString();
        String category = getCategory(event);
        String optionInput = event.getOption(category).getAsString();
        List<Job> jobListFiltered = filterJobs(jobList, category, optionInput);
        event.reply(category + " Here are the jobs filtered based on " + optionInput).queue();

        // jobListFiltered = jobList.subList(2, 7);

        Logger logger = Logger.getLogger("FilterCommandTest");
        logger.info(String.valueOf(jobListFiltered.size()));

        for (Job job : jobListFiltered) {
            // job = (Job) castJob;
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

        // OptionData categoryOptions =
        //         new OptionData(
        //                         OptionType.STRING,
        //                         "category",
        //                         "What category do you want to filter?")
        //                 .setRequired(true);
        // for (String choice : commandList) {
        //     categoryOptions.addChoice(choice, choice);
        // }

        OptionData titleOptions =
                new OptionData(
                        OptionType.STRING, "title", "What job titles do you want to filter for?");
        for (String choice : Arrays.asList("Software Engineer", "Backend", "University")) {
            titleOptions.addChoice(choice, choice);
        }

        OptionData typeOptions =
                new OptionData(
                        OptionType.STRING,
                        "jobtype",
                        "What type of job do you want to filter for?");
        for (String choice : Arrays.asList("Full-time", "Part-time")) {
            typeOptions.addChoice(choice, choice);
        }

        OptionData companyOptions =
                new OptionData(
                        OptionType.STRING, "company", "What company do you want to filter for?");

        for (String choice : getCompanyNameList()) {

            companyOptions.addChoice(choice, choice);
        }

        OptionData distanceOptions =
                new OptionData(
                        OptionType.STRING,
                        "distance",
                        "What are the farthest jobs do you want to filter for?");
        for (String choice :
                Arrays.asList(
                        "10 miles/locally",
                        "50 miles/in the same metro",
                        "1500 miles/on the same coast")) {
            distanceOptions.addChoice(choice, choice);
        }

        OptionData postTimeOptions =
                new OptionData(
                        OptionType.STRING,
                        "time_posted",
                        "What are the earliest jobs posted do you'd like to filter for?");
        for (String choice : Arrays.asList("1 day", "3 days", "1 week", "1 month")) {
            postTimeOptions.addChoice(choice, choice);
        }

        OptionData experience =
                new OptionData(
                        OptionType.STRING,
                        "experience",
                        "What job experience level do you'd like to filter for?");
        for (String choice :
                Arrays.asList("internship", "entry-level", "mid-level", "senior-level")) {
            experience.addChoice(choice, choice);

        }

        OptionData ratingOptions =
                new OptionData(
                        OptionType.STRING,
                        "rating",
                        "What is the lowest star rating of jobs you want to filter for?");
        for (String choice : Arrays.asList("3.0", "4.0", "4.5")) {
            ratingOptions.addChoice(choice, choice);
        }

        OptionData annualPayOptions =
                new OptionData(
                        OptionType.STRING,
                        "annualpay",
                        "What is the min annual pay of jobs you want to filter for? (in USD)");
        for (String choice : Arrays.asList("50000", "100000", "150000")) {

            annualPayOptions.addChoice(choice, choice);
        }

        OptionData visaOptions =
                new OptionData(
                        OptionType.STRING,
                        "visa",
                        "Do you want to ignore jobs that does not sponsor work visa in the US?");
        for (String choice : Arrays.asList("Yes", "No")) {
            visaOptions.addChoice(choice, choice);
        }

        return new CommandData(this.getName(), "Filter for jobs.")
                .addOptions(
                        // categoryOptions.setRequired(true),
                        titleOptions,
                        typeOptions,
                        companyOptions,
                        distanceOptions,
                        experience,
                        postTimeOptions,
                        ratingOptions,
                        annualPayOptions,
                        visaOptions);
    }

    private List<Job> filterJobs(List<Job> jobList, String Category, String Option) {

        List<Job> filteredJobList = new ArrayList<>();
        // String x = "Software Engineer";

        // for (Job job : jobList) {
        //     System.out.println(job.getJobTitle());
        //     if (job.getJobTitle().equals(Option)) {
        //         filteredJobList.add(job);
        //     }
        // }

        switch (Category) {
            case "title":
                for (Job job : jobList) {
                    if (containsKeyword(job.getJobTitle(), Option)) {
                        // if (job.getJobTitle().equals(Option)) {
                        filteredJobList.add(job);
                    }
                }

            case "company":
                for (Job job : jobList) {
                    if (containsKeyword(job.getCompany(), Option)) {
                        filteredJobList.add(job);
                    }
                }

                // case "annualpay":
                //     for (Job job : jobList) {
                //         // if (Option)
                //         if (Float.compare(job.getAnnualPay(), 60000f) == 1) {
                //             filteredJobList.add(job);
                //         }
                //     }
            case "experience":
                for (Job job : jobList) {
                    if (job.getExperience()
                            .equals(
                                    jobController
                                            .getExperienceController()
                                            .getExperienceByLabel(Option)
                                            .getId())) {
                        filteredJobList.add(job);
                    }
                }
            default:
        }

        int size = 5;
        if (filteredJobList.size() < size) {
            size = filteredJobList.size();
        }

        // return filteredJobList.subList(0, size);
        return filteredJobList;
    }

    public ArrayList<String> getCompanyNameList() {
        ArrayList<String> companyList = new ArrayList<>();

        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        for (Job job : jobList) {
            if (companyList.contains(job.getCompany()) == false) {
                companyList.add(job.getCompany());
            }
        }
        return companyList;
    }

    public String getCategory(SlashCommandEvent event) {
        String option = null;
        for (String command : commandList) {
            if (event.getOption(command) != null) {
                option = command;
                return option;
            }
        }
        return option;
    }

    public Boolean containsKeyword(String title, String keyword) {
        if (title.indexOf(keyword) != -1) {
            return true;
        }
        return false;
    }
}
