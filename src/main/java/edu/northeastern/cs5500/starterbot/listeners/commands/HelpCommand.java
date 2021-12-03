package edu.northeastern.cs5500.starterbot.listeners.commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class HelpCommand implements Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        String content =
                "These are the commands you can use: \n"
                        + "/help        Get a menu explaining how to use GetOfferBot \n"
                        + "/search      Look up 10 SDE jobs ordered by post date by default \n"
                        + "OPTIONS(optional): \n"
                        + "level:       the level of the job       /search --level-intern \n"
                        + "location:    The location of the job    /search --location-Seattle \n"
                        + "company:     The name of the compnay    /search --company-Meta \n"
                        + "wfh:         Remote friendly or not     /search --wfh -true \n"
                        + "sponsorship: Sponsor or not             /search --sponsorship -true \n"
                        + "/sort        Sort the jobs based on input keywords \n"
                        + "OPTIONS: \n"
                        + "salary:      Sort by salary             /sort --salary -asc/-desc \n"
                        + "rating:      Sort by company rating     /sort --rating -asc/-desc \n"
                        + "location:    Sort by distance to user   /sort --location -asc/-desc \n"
                        + "postdate     Sort by post date          /sort --time -old/new \n"
                        + "filter:      Filter base on one or more keywords \n"
                        + "OPTIONS: \n"
                        + "title:       The title of job           /filter --title<Intern> \n"
                        + "type:        The type of job            /filter --type[ft/pt] \n"
                        + "company:     The name of company        /filter --company<Meta> \n"
                        + "distance:    The max distance of job    /filter --distance<zipcode><miles> \n"
                        + "post time:   The post date of job       /filter --timeposted<Number of days> \n"
                        + "rating:      The rating of the company  /filter --rating<Rating number> \n"
                        + "annual pay   The annual pay of the job  /filter --annualpay<Salary number> \n"
                        + "visa:        The visa sponsorship       /filter --visa[-true/-false] \n";

        event.reply(content).queue();
    }

    @Override
    //
    public CommandData getCommandData() {
        return new CommandData("help", "Help menu of the bot");
    }
}
