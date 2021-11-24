package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import java.util.Arrays;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.data.DataObject;

public class SearchCommand implements Command {

    private GenericRepository<Job> jobRepository;

    @Override
    public String getName() {
        return "search";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        // TODO: replace this with actual DB query
        // TODO: use embeds to show messages
        String content =
                "JobId     Company     Level   Location\n"
                        + "0         Amazon      Intern  Seattle\n"
                        + "2         Google      Intern  Seattle\n"
                        + "3         Amazon      NewGrad Huston\n"
                        + "4         Meta        NewGrad Seattle\n"
                        + "5         Meta        Senior  Kirland\n";
        event.reply(content).queue();
    }

    @Override
    public CommandData getCommandData() {
        // TODO: either make them global static, or dynamically refresh such choices based on data
        // from DB.
        OptionData companyOptions =
                new OptionData(
                        OptionType.STRING, "company", "What companies do you want to search for?");
        for (String choice : Arrays.asList("Amazon", "Google", "Meta")) {
            companyOptions.addChoice(choice, choice);
        }

        OptionData levelOptions =
                new OptionData(OptionType.STRING, "level", "What levels do you want to search?");
        for (String choice : Arrays.asList("Intern", "New Grad", "Senior")) {
            levelOptions.addChoice(choice, choice);
        }

        OptionData locationOptions =
                new OptionData(
                        OptionType.STRING, "location", "What locations do you want to search?");
        for (String choice : Arrays.asList("Seattle", "Silicon Valley", "New York")) {
            locationOptions.addChoice(choice, choice);
        }

        OptionData wfhOptions =
                new OptionData(OptionType.STRING, "wfh", "What wfh options do you want to search?");
        for (String choice : Arrays.asList("Yes", "No")) {
            wfhOptions.addChoice(choice, choice);
        }

        OptionData sponsorshipOptions =
                new OptionData(
                        OptionType.STRING,
                        "sponsorship",
                        "What sponsorship options do you want to search?");
        for (String choice : Arrays.asList("Yes", "No")) {
            sponsorshipOptions.addChoice(choice, choice);
        }

        OptionData sortOptions =
                new OptionData(OptionType.STRING, "sort", "How would you like to sort the result?");
        for (String choice : Arrays.asList("Company", "Level", "Location", "Annual Pay")) {
            sortOptions.addChoice(choice, choice);
        }

        return new CommandData(this.getName(), "Search for jobs.")
                .addOptions(
                        companyOptions,
                        levelOptions,
                        locationOptions,
                        wfhOptions,
                        sponsorshipOptions,
                        sortOptions);
    }

    @Override
    public void setJobRepository(GenericRepository<Job> jobRepository) {
        this.jobRepository = jobRepository;
    }
}
