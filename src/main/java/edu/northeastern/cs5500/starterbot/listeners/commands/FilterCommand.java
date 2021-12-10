package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.utility.EmbedUtilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * FilterCommand is created by messageListenenr to filter job from jobList. When slashCommand
 * /filter along with Option entered correctly on getofferbot enterd, the bot will return filtered
 * jobs based on the specified option.
 */
public class FilterCommand implements Command {

    private JobController jobController;
    private List<String> commandList;
    private List<String> experienceList;
    private List<String> ratingList;
    private List<String> payList;

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

        experienceList = Arrays.asList("intern", "entry", "mid", "senior");
    }

    @Override
    public String getName() {
        return "filter";
    }

    /**
     * This method retrieves all jobs from job repository in MongoDB, call filter funtion to start
     * filter returned job list then rendered in embed format and returned back to discord.
     *
     * @param event discord command event
     */
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        String category = getCategory(event);
        String optionInput = event.getOption(category).getAsString();
        List<Job> jobListFiltered = filterJobs(jobList, category, optionInput);

        int sizeToreturn = jobListFiltered.size();
        if (sizeToreturn > 6) {
            sizeToreturn = 6;
        }

        jobListFiltered = jobListFiltered.subList(0, sizeToreturn);

        if (sizeToreturn == 0) {
            event.reply("No result at this point, try another option or /help").queue();
        } else {
            event.reply("based on" + category + " Here are the jobs filtered by " + optionInput)
                    .queue();
        }
        for (Job job : jobListFiltered) {
            EmbedBuilder embedBuilder = EmbedUtilities.generateJobEmbed(job, jobController);
            event.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }

    @Override
    public CommandData getCommandData() {

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
        for (String choice : experienceList) {
            experience.addChoice(choice, choice);
        }

        OptionData ratingOptions =
                new OptionData(
                        OptionType.STRING,
                        "rating",
                        "What is the lowest star rating of jobs you want to filter for?");
        for (String choice : ratingList) {
            ratingOptions.addChoice(choice, choice);
        }

        OptionData annualPayOptions =
                new OptionData(
                        OptionType.STRING,
                        "annualpay",
                        "What is the min annual pay of jobs you want to filter for?");
        for (String choice : payList) {

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

    /**
     * Filter Job based on command input
     *
     * @param jobList List<Job>, all jobs from repository
     * @param category String, job field to filter \ * @param option String, subcommand for
     *     category, specifies the entered option by user
     * @return a list of filtered job
     */
    private List<Job> filterJobs(List<Job> jobList, String Category, String Option) {

        List<Job> filteredJobList = new ArrayList<>();

        switch (Category) {
            case "experience":
                if (experienceList.indexOf(Option) == -1) {
                    Option = "senior";
                }
                jobList = removeNullforexperience(jobList);
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

    /**
     * Helper function to remove if the job's annualpay attribute is null
     *
     * @param jobList List, joblist to be removed
     * @return jobList List, jobList after the removal
     */
    public List<Job> removeNullforAnnualpay(List<Job> jobList) {
        List<Job> newJobList = new ArrayList<>();
        for (Job job : jobList) {
            if (job.getAnnualPay() != null) {
                newJobList.add(job);
            }
        }
        return newJobList;
    }

    /**
     * Helper function to remove the job if the experience attribute is null
     *
     * @param jobList List, joblist to be removed
     * @return jobList List, jobList after the removal
     */
    public List<Job> removeNullforexperience(List<Job> jobList) {
        List<Job> newJobList = new ArrayList<>();
        for (Job job : jobList) {
            if (job.getExperience() != null) {
                newJobList.add(job);
            }
        }
        return newJobList;
    }
}
