package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.JobController;
import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.utility.EmbedUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * TestMongoCommand represents a Discord slashCommand that is used to verify job data can be stored
 * and retrieved from MongoDB. When users use slashCommand /testMongo on getofferbot, it is expected
 * that the bot returns the default jobs stored in JobController.java.
 */
// TODO: delete this command when the official slash command are implemented
public class TestMongoCommand implements Command {

    private JobController jobController;

    public TestMongoCommand(JobController jobController) {
        this.jobController = jobController;
    }

    @Override
    public String getName() {
        return "testmongo";
    }

    /**
     * /testmongo slashCommand event handler.
     *
     * @return bot replies 5 default jobs stored in mongoDB without filter or order. If there are
     *     less than 5 jobs in mongoDB, bot will replies all the jobs.
     */
    // TODO: use scrapt to search for jobs and store in MongoDB
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        event.reply("Here are the jobs:").queue();
        Job job = null;
        int size = this.jobController.getJobRepository().getAll().size();
        int length = 0;
        if (size < 5) {
            length = size;
        } else {
            length = 5;
        }
        Object[] jobArray = this.jobController.getJobRepository().getAll().toArray();
        for (int i = 0; i < length; i++) {
            job = (Job) jobArray[i];
            EmbedBuilder embedBuilder = EmbedUtilities.generateJobEmbed(job, jobController);
            event.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(
                "testmongo", "verify that Job can be stored and retrieved from MongoDB");
    }
}
