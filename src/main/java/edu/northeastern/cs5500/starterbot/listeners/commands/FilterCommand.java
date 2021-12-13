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
 * jobs based on the specified option and prompt user to change their input when there is no
 * matching jobs to show.
 */
public class FilterCommand implements Command {

    private JobController jobController;
    private List<String> commandList;
    private List<String> experienceList;
    private List<String> ratingList;
    private List<String> payList;
    private int numToRetuen;

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
        ratingList = Arrays.asList("2", "3", "4", "5");
        payList = Arrays.asList("50000", "80000", "110000", "130000");

        numToRetuen = 6;
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
    public void onSlashCommand(SlashCommandEvent event) {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        String category = getCategory(event);
        String optionInput = event.getOption(category).getAsString();
        System.out.println("------------++++++++++++");

        // List<Job> jobListFiltered = new ArrayList<>();
        // String Option = "rating";
        // jobList = removeNullforexperience(jobList, Option);
        // System.out.println(jobList.size());
        // for (Job job : jobList) {
        //     System.out.println("+++++++++++++++");
        //     System.out.println(job.getStarRating());
        //     if (job.getStarRating() > Float.valueOf(Option)) {
        //         jobListFiltered.add(job);
        //         System.out.println(job);
        //     }
        // }

        List<Job> jobListFiltered = filterJobs(jobList, category, optionInput);

        int sizeToreturn = jobListFiltered.size();
        if (sizeToreturn > numToRetuen) {
            sizeToreturn = numToRetuen;
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
        for (String choice : Arrays.asList("fulltime", "parttime")) {
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
                new OptionData(OptionType.STRING, "visa", "Do you require a job visa in the US?");
        for (String choice : Arrays.asList("true", "false")) {
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
     * @param String Category, filter function to determine the filtering category and then filter
     *     jobs for if the option input is valid
     * @param String Option, user option input
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

            case "jobtype":
                for (Job job : jobList) {
                    if (containsKeyword(job.getJobTitle(), Option)) {
                        filteredJobList.add(job);
                    }
                }

            case "visa":
                for (Job job : jobList) {
                    if (job.getSponsorship() == Boolean.valueOf(Option)) {
                        // if (job.getJobTitle().equals(Option)) {
                        filteredJobList.add(job);
                    }
                }

            case "rating":
                jobList = removeNullforRating(jobList);

                if (ratingList.indexOf(Option) == -1) {
                    Option = "3";
                }
                for (Job job : jobList) {
                    System.out.println("option " + Float.valueOf(Option));
                    System.out.println("job rating " + job.getStarRating());
                    System.out.println(job.getStarRating() > Float.valueOf(Option));
                    if (job.getStarRating() > Float.valueOf(Option)) {
                        filteredJobList.add(job);
                    }
                }

            case "experience":
                jobList = removeJobswithNullValue(jobList);

                if (experienceList.indexOf(Option) == -1) {
                    Option = "entry";
                }
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

            case "annualpay":
                if (payList.indexOf(Option) == -1) {
                    Option = "50000";
                }
                jobList = removeNullforAnnualpay(jobList);
                for (Job job : jobList) {
                    if (job.getAnnualPay().floatValue() > Float.valueOf(Option)) {
                        filteredJobList.add(job);
                    }
                }

            default:
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
     * @param SlashCommandEvent event, JDA event passed in for matching
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
     * Helper function to return a new list of job with experience attribute that is not null
     *
     * @param jobList List, original joblist to be filtered on experience
     * @return jobList List, a new jobList without experience
     */
    public List<Job> removeJobswithNullValue(List<Job> jobList) {
        List<Job> newJobList = new ArrayList<>();
        for (Job job : jobList) {
            if (job.getExperience() != null) {
                newJobList.add(job);
            }
        }
        return newJobList;
    }

    public List<Job> removeNullforRating(List<Job> jobList) {
        List<Job> newJobList = new ArrayList<>();
        for (Job job : jobList) {
            if (job.getStarRating() != null) {
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
}
