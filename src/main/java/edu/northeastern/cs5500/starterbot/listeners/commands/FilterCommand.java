package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.utility.EmbedUtilities;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Generated;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * FilterCommand is created by messageListenenr to filter job from jobList. When slashCommand
 * /filter along with Option entered correctly on getofferbot enterd, the bot will return filtered
 * jobs based on the specified option and prompt user to change their input when there is no
 * matching jobs to show.
 */
public class FilterCommand implements Command {

    private JobController jobController;
    private List<String> commandList;
    private List<String> experienceList;
    private List<String> ratingList;
    private List<String> payList;
    private List<String> jobTypeList;
    private List<String> periodList;
    private List<String> companyList;
    private int sizeToreturn = 8;

    public FilterCommand(JobController jobController) {
        this.jobController = jobController;
        commandList =
                Arrays.asList(
                        "title",
                        "jobtype",
                        "company",
                        "date_posted",
                        "experience",
                        "rating",
                        "annualpay");

        experienceList = Arrays.asList("intern", "entry", "mid", "senior");
        ratingList = Arrays.asList("2", "3", "4", "5");
        payList = Arrays.asList("50000", "80000", "110000", "130000");
        jobTypeList = Arrays.asList("fulltime", "parttime");
        periodList = Arrays.asList("1 day", "3 days", "1 week", "1 month");
        companyList = getCompanyNameList();
    }

    @Override
    public String getName() {
        return "filter";
    }

    /**
     * This method retrieves all jobs from job repository in MongoDB, call filter funtion to start
     * filter job list, jobs will be rendered in embed format and returned back to discord in card
     * format.
     *
     * @param event discord command event
     */
    @Override
    @Generated
    public void onSlashCommand(SlashCommandEvent event) {

        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        String category = getCategory(event);
        String optionInput = event.getOption(category).getAsString();

        List<Job> jobListFiltered = filterJobs(jobList, category, optionInput);

        int sizeAfterFilter = jobListFiltered.size();
        if (sizeAfterFilter > sizeToreturn) {
            sizeAfterFilter = sizeToreturn;
        }

        jobListFiltered = jobListFiltered.subList(0, sizeAfterFilter);

        if (sizeAfterFilter == 0) {
            event.reply("No result at this point, try another option or use /help").queue();
        } else {
            event.reply(
                            "Filtering on "
                                    + category
                                    + ", Here are the jobs by "
                                    + optionInput)
                    .queue();
        }
        for (Job job : jobListFiltered) {
            EmbedBuilder embedBuilder = EmbedUtilities.generateJobEmbed(job, jobController);
            event.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }

    @Override
    @Generated
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
        for (String choice : jobTypeList) {
            typeOptions.addChoice(choice, choice);
        }

        OptionData companyOptions =
                new OptionData(
                        OptionType.STRING, "company", "What company do you want to filter for?");

        for (String choice : companyList) {

            companyOptions.addChoice(choice, choice);
        }

        OptionData postTimeOptions =
                new OptionData(
                        OptionType.STRING,
                        "date_posted",
                        "What are the earliest jobs posted do you'd like to filter for?");
        for (String choice : periodList) {
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

        return new CommandData(this.getName(), "Filter for jobs.")
                .addOptions(
                        titleOptions,
                        typeOptions,
                        companyOptions,
                        experience,
                        postTimeOptions,
                        ratingOptions,
                        annualPayOptions);
    }

    /**
     * Function that uses switch cases to carry out filtering operations accordingly
     *
     * @param Category indicates what the filter type
     * @param Option option entered for that filter
     * @return List jobList, return a new jobList filtered from original jobList
     */
    private List<Job> filterJobs(List<Job> jobList, String Category, String Option) {

        List<Job> filteredJobList = new ArrayList<>();

        switch (Category) {
            case "title":
                for (Job job : jobList) {
                    if (containsKeyword(job.getJobTitle(), Option)) {
                        filteredJobList.add(job);
                    }
                }
                break;

            case "jobtype":

                if (jobTypeList.indexOf(Option) == -1) {
                    Option = "parttime";
                }
                for (Job job : jobList) {
                    if (job.getJobType() != null) {
                        if (job.getJobType()
                                .equals(
                                        jobController
                                                .getJobTypeController()
                                                .getJobTypeByLabel(Option)
                                                .getId())) {
                            filteredJobList.add(job);
                        }
                    }
                }
                break;

            case "date_posted":

                for (Job job : jobList) {
                    if (job.getCreated() != null) {
                        if (job.getCreated().isAfter(parsingDate(Option))) {
                            filteredJobList.add(job);
                        }
                    }
                }
                break;

            case "company":

                for (Job job : jobList) {
                    if (containsKeyword(job.getCompany(), Option)) {
                        filteredJobList.add(job);
                    }
                }
                break;

            case "rating":

                if (ratingList.indexOf(Option) == -1) {
                    Option = "3";
                }
                for (Job job : jobList) {
                    if (job.getStarRating() != null) {
                        if (job.getStarRating() > Float.valueOf(Option)) {
                            filteredJobList.add(job);
                        }
                    }
                }
                break;

            case "experience":

                if (experienceList.indexOf(Option) == -1) {
                    Option = "entry";
                }
                for (Job job : jobList) {
                    if (job.getExperience() != null) {

                        if (job.getExperience()
                                .equals(
                                        jobController
                                                .getExperienceController()
                                                .getExperienceByLabel(Option)
                                                .getId())) {
                            filteredJobList.add(job);
                        }
                    }
                }
                break;

            case "annualpay":
            
                if (payList.indexOf(Option) == -1) {
                    Option = "50000";
                }

                for (Job job : jobList) {
                    if (job.getAnnualPay() != null) {

                        if (job.getAnnualPay().floatValue() > Float.valueOf(Option)) {
                            filteredJobList.add(job);
                        }
                    }
                }
                break;
        }

        return filteredJobList;
    }

    /**
     * Helper function to update company list from jobController
     *
     * @return companyList extracted from jobs that have company attribute
     */
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

    /**
     * Helper function to see which filter category our user is using
     *
     * @param event, JDA event passed in for matching
     * @return String option, option being used
     */
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
     * Helper function to check if a string contains a certain substring
     *
     * @param String text, input string
     * @param String keyword, keyword we are looking for
     * @return Boolean, true when the text contains keyword
     */
    public Boolean containsKeyword(String text, String keyword) {
        if (text.indexOf(keyword) != -1) {
            return true;
        }
        return false;
    }

    /**
     * Helper function that create and return a LocalDate object that subtract current date by
     * speified time
     *
     * @param String time, date back to...
     * @return LocalDate, parsed date.
     */
    public LocalDate parsingDate(String time) {

        LocalDate date = LocalDate.now();
        LocalDate returnvalue = date;

        if (time.equals("1 day")) {
            returnvalue = date.minusDays(1);
        } else if (time.equals("1 week")) {
            returnvalue = date.minusWeeks(1);
        } else if (time.equals("3 days")) {
            returnvalue = date.minusDays(3);
        } else {
            returnvalue = date.minusMonths(1);
        }
        return returnvalue;
    }
}
