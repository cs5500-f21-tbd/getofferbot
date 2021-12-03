package edu.northeastern.cs5500.starterbot.listeners.commands;

import edu.northeastern.cs5500.starterbot.controller.UserController;
import edu.northeastern.cs5500.starterbot.model.User;
import javax.annotation.Nullable;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class PreferenceCommand implements Command {
    private UserController userController;

    public PreferenceCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public String getName() {
        return "preferences";
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        // TODO Auto-generated method stub
        User user = userController.getUserByDiscordId(event.getUser().getIdLong());
        OptionMapping zipcodeOption = event.getOption("zipcode");
        OptionMapping radiusOption = event.getOption("radius");

        if (zipcodeOption == null && radiusOption == null) {
            showPreferences(user, event);
        } else {
            String zipcode = null;
            Long radius = null;
            if (zipcodeOption != null) {
                zipcode = zipcodeOption.getAsString();
            }
            if (radiusOption != null) {
                radius = radiusOption.getAsLong();
            }
            setPreferences(user, event, zipcode, radius);
        }
    }

    void showPreferences(@Nullable User user, SlashCommandEvent event) {
        if (user == null) {
            event.reply("You have not set any preferences yet.");
            return;
        }
        // TODO: tell the user what their preferences are. Does radius have a default?
    }

    void setPreferences(
            @Nullable User user,
            SlashCommandEvent event,
            @Nullable String zipcode,
            @Nullable Long radius) {
        // TODO: call the correct methods of userController to set one or both of these preferences
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), "Set or get your preferences")
                .addOptions(
                        new OptionData(
                                        OptionType.STRING,
                                        "zipcode",
                                        "Your home zipcode; searches will use this to determine distance to you.")
                                .setRequired(false),
                        new OptionData(
                                        OptionType.INTEGER,
                                        "radius",
                                        "The radius in miles from your home zipcode to include results from; if 0, only remote jobs will be shown.")
                                .setRequired(false));
    }
}
