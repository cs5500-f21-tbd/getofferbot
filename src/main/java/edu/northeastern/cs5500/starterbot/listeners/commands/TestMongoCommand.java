package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * TestMongoCommand represents a Discord slashCommand that is used to verify job data can be stored
 * and retrieved from MongoDB. When users use slashCommand /testMongo on getofferbot, it is expected
 * that the bot returns the default jobs stored in JobController.java.
 */
// TODO: delete this command when the official slash command are implemented
public class TestMongoCommand implements Command {

    private GenericRepository<Job> jobRepository;

    @Override
    public String getName() {
        return "testmongo";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        Job job = null;
        StringBuilder sb = new StringBuilder();
        for (Job j : this.jobRepository.getAll()) {
            job = j;
            System.out.print(job.getJobTitle());
            sb.append(job.getJobTitle());
            sb.append('\n');
        }
        event.reply(sb.toString()).queue();
    }

    @Override
    //
    public CommandData getCommandData() {
        return new CommandData(
                "testmongo", "verify that Job can be stored and retrieved from MongoDB");
    }

    @Override
    public void setJobRepository(GenericRepository<Job> jobRepository) {
        this.jobRepository = jobRepository;
    }
}
