package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import java.util.Arrays;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class FilterCommand implements Command {

    private JobController jobController;

    public FilterCommand(JobController jobController) {
        this.jobController = jobController;
    }

    @Override
    public String getName() {
        return "filter";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        event.reply(
                        "Here are the jobs filtered based on "
                                + event.getOption("category").getAsString())
                .queue();
    }

    @Override
    public CommandData getCommandData() {
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
        for (String choice : Arrays.asList("Amazon", "Meta", "Google")) {
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
                        "posttime",
                        "What is the max posting time of jobs do you want to display?");
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
}
