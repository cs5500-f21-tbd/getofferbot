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
    private int numToReturn;

    public FilterCommand(JobController jobController) {
        this.jobController = jobController;
        commandList =
                Arrays.asList(
                        "title",
                        "jobtype",
                        "company",
                        "time_posted",
                        "experience",
                        "rating",
                        "annualpay");

        experienceList = Arrays.asList("intern", "entry", "mid", "senior");
        ratingList = Arrays.asList("2", "3", "4", "5");
        payList = Arrays.asList("50000", "80000", "110000", "130000");
        jobTypeList = Arrays.asList("fulltime", "parttime");
        periodList = Arrays.asList("1 day", "3 days", "1 week", "1 month");
        companyList = getCompanyNameList();
        numToReturn = 6;
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

        int sizeToreturn = jobListFiltered.size();
        if (sizeToreturn > numToReturn) {
            sizeToreturn = numToReturn;
        }

        jobListFiltered = jobListFiltered.subList(0, sizeToreturn);

        if (sizeToreturn == 0) {
            event.reply("No result at this point, try another option or /help").queue();
        } else {
            event.reply(
                            "Filtering on "
                                    + category
                                    + ", Here are the jobs filtered by "
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
                        "time_posted",
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
     * @param Category filter function to determine the filtering category and then filter jobs for
     *     if the option input is valid
     * @param Option user option input
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
                jobList = removeNullForType(jobList);

                if (jobTypeList.indexOf(Option) == -1) {
                    Option = "parttime";
                }
                for (Job job : jobList) {
                    if (job.getJobType()
                            .equals(
                                    jobController
                                            .getJobTypeController()
                                            .getJobTypeByLabel(Option)
                                            .getId())) {
                        filteredJobList.add(job);
                    }
                }
                break;

            case "time_posted":
                jobList = removeNullForCreated(jobList);
                for (Job job : jobList) {
                    if (job.getCreated() != null & job.getCreated().isAfter(parsingDate(Option))) {
                        filteredJobList.add(job);
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
                jobList = removeNullForRating(jobList);

                if (ratingList.indexOf(Option) == -1) {
                    Option = "3";
                }
                for (Job job : jobList) {
                    if (job.getStarRating() > Float.valueOf(Option)) {
                        filteredJobList.add(job);
                    }
                }
                break;

            case "experience":
                if (experienceList.indexOf(Option) == -1) {
                    Option = "entry";
                }
                jobList = removeNullForExperience(jobList);
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
                break;

            case "annualpay":
                if (payList.indexOf(Option) == -1) {
                    Option = "50000";
                }
                jobList = removeNullForAnnualPay(jobList);
                for (Job job : jobList) {
                    if (job.getAnnualPay().floatValue() > Float.valueOf(Option)) {
                        filteredJobList.add(job);
                    }
                }
                break;

                // default:
        }

        return filteredJobList;
    }

    /**
     * Helper function to update company list from jobController
     *
     * @return companyList extracted from job with company attribute
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
     * <<<<<<< HEAD Helper functions to return a new list of job with non_null attribute
     *
     * @param jobList List, original joblist to be filtered
     * @return jobList List, a new jobList containing jobs with valid attribute ======= Helper
     *     function to remove if the job's annual pay attribute is null
     * @param jobList List, jobList to be removed
     * @return jobList List, jobList after the removal >>>>>>> main
     */
    public List<Job> removeNullForAnnualPay(List<Job> jobList) {
        List<Job> newJobList = new ArrayList<>();
        for (Job job : jobList) {
            if (job.getAnnualPay() != null) {
                newJobList.add(job);
            }
        }
        return newJobList;
    }

    /**
     * Helper function to return a new list of job with experience attribute that is not null
     *
     * @param jobList List, original joblist to be filtered on experience
     * @return jobList List, a new jobList without experience
     */
    public List<Job> removeNullForExperience(List<Job> jobList) {
        List<Job> newJobList = new ArrayList<>();
        for (Job job : jobList) {
            if (job.getExperience() != null) {
                newJobList.add(job);
            }
        }
        return newJobList;
    }

    public List<Job> removeNullForRating(List<Job> jobList) {
        List<Job> newJobList = new ArrayList<>();
        for (Job job : jobList) {
            if (job.getStarRating() != null) {
                newJobList.add(job);
            }
        }
        return newJobList;
    }

    public List<Job> removeNullForType(List<Job> jobList) {
        List<Job> newJobList = new ArrayList<>();
        for (Job job : jobList) {
            if (job.getJobType() != null) {
                newJobList.add(job);
            }
        }
        return newJobList;
    }

    public List<Job> removeNullForCreated(List<Job> jobList) {
        List<Job> newJobList = new ArrayList<>();
        for (Job job : jobList) {
            if (job.getCreated() != null) {
                newJobList.add(job);
            }
        }
        return newJobList;
    }

    /**
     * Helper function to check if a string contains a certain substring
     *
     * @param String title, input string
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
     * Helper function to check if a string contains a certain substring
     *
     * @param String title, input string
     * @param String keyword, keyword we are looking for
     * @return Boolean, true when the text contains keyword
     */
    public LocalDate parsingDate(String period) {
        LocalDate date = LocalDate.now();
        LocalDate returnvalue = date;
        // LocalDate returnvalue1 = LocalDate.of(2021, 12, 1);

        if (period.equals("1 day")) {
            returnvalue = date.minusDays(1);
        } else if (period.equals("1 week")) {
            returnvalue = date.minusWeeks(1);
        } else if (period.equals("3 days")) {
            returnvalue = date.minusDays(3);
        } else {
            returnvalue = date.minusMonths(1);
        }
        return returnvalue;
    }
}
