package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.model.Job;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class FilterCommand implements Command {

    private JobController jobController;

    @Override
    public String getName() {
        return "filter";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());

        String category = event.getOption("category").getAsString();

        String title = event.getOption("title").getAsString();
        String type = event.getOption("type").getAsString();
        String company = event.getOption("company").getAsString();
        String distance = event.getOption("distance").getAsString();
        String timeposted = event.getOption("timeposted").getAsString();
        String rating = event.getOption("rating").getAsString();
        String annaulpay = event.getOption("annaulpay").getAsString();
        String visa = event.getOption("visa").getAsString();

        // String[] options = option.split(" ");
        // String optionType = options[0];
        // String optionVal = options[1];

        jobList = filterJobs(jobList, rating);
        event.reply("Here are the jobs filtered based on " + "val").queue();

        Job job = null;
        int size = this.jobController.getJobRepository().getAll().size();
        int length = 10;
        if (size < 10) {
            length = size;
        }

        // Object[] jobArray = this.jobController.getAll().toArray();
        // for (int i = 0; i < length; i++) {
        for (Object castJob : jobController.getAll().toArray()) {
            // job = (Job) jobArray[i];
            job = (Job) castJob;
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
            // event.getChannel().sendMessage(eb.build()).queue();
        }
    }

    @Override
    public CommandData getCommandData() {
        // TODO: This will be connected to the DB in Sprint 4

        OptionData categoryOptions =
                new OptionData(
                                OptionType.STRING,
                                "category",
                                "What category do you want to filter?")
                        .setRequired(true);
        for (String choice :
                Arrays.asList(
                        "title",
                        "type",
                        "company",
                        "distance",
                        "time_posted",
                        "rating",
                        "annualpay",
                        "visa")) {
            categoryOptions.addChoice(choice, choice);
        }

        OptionData titleOptions =
                new OptionData(
                        OptionType.STRING, "title", "What job titles do you want to display?");
        for (String choice : Arrays.asList("Software Engineer", "Backend", "University")) {
            titleOptions.addChoice(choice, choice);
        }

        OptionData typeOptions =
                new OptionData(OptionType.STRING, "type", "What job types do you want to display?");
        for (String choice : Arrays.asList("Full-time", "Part-time")) {
            typeOptions.addChoice(choice, choice);
        }

        OptionData companyOptions =
                new OptionData(
                        OptionType.STRING, "company", "What companies do you want to display?");
        for (String choice :
                Arrays.asList(
                        "Amazon",
                        "Meta",
                        "Google",
                        "HP",
                        "Microsoft",
                        "TikTok",
                        "Rogue Fabrication, LLC",
                        "Discovery,Inc.")) {
            companyOptions.addChoice(choice, choice);
        }

        OptionData distanceOptions =
                new OptionData(
                        OptionType.STRING,
                        "distance",
                        "What is the max distance of jobs do you want to display?");
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
                        "What is the earliest job do you'd like to see?");
        for (String choice : Arrays.asList("1 day", "3 days", "1 week", "1 month")) {
            postTimeOptions.addChoice(choice, choice);
        }

        OptionData ratingOptions =
                new OptionData(
                        OptionType.STRING,
                        "rating",
                        "What is the min rating of jobs do you want to display?");
        for (String choice : Arrays.asList("3.0", "4.0", "4.5")) {
            ratingOptions.addChoice(choice, choice);
        }

        OptionData annualPayOptions =
                new OptionData(
                        OptionType.STRING,
                        "annualpay",
                        "What is the min annual pay of jobs do you want to display?");
        for (String choice : Arrays.asList("50000 USD", "100000 USD", "150000 USD")) {
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
                        titleOptions,
                        typeOptions,
                        companyOptions,
                        distanceOptions,
                        postTimeOptions,
                        ratingOptions,
                        annualPayOptions,
                        visaOptions);
    }

    @Override
    public void setJobController(JobController jobController) {
        this.jobController = jobController;
    }

    private List<Job> filterJobs(List<Job> jobList, String option) {
        List<Job> newJobList = new ArrayList<>();

        switch (option) {
            case "title":
                for (Job job : jobList) {
                    if (job.getJobTitle() == option) {
                        newJobList.add(job);
                    }
                }
                // filter command handler
            case "type":
                for (Job job : jobList) {
                    // if ((JobType)job.getJobType() == optionVal) {
                    //     newJobList.add(job);
                    // }
                }
                break;

            case "company":
                // event.reply("The help menu will be added soon...").queue();
                // // todo: change event.reply with help function call
                // break;

            case "timeposted":
                //

            case "location":

            case "visa":

            default:
        }

        int size = 5;
        if (jobList.size() < size) {
            size = jobList.size();
        }
        return jobList.subList(0, size);
        // return new ArrayList<>();
    }

    public ArrayList<String> getCompanyName() {
        ArrayList<String> companyList = new ArrayList<>();

        List<Job> jobList = new ArrayList<>(this.jobController.getJobRepository().getAll());
        for (Job job : jobList) {
            if (companyList.contains(job.getCompany()) == false) {
                companyList.add(job.getCompany());
            }
        }
        return companyList;
    }
}
