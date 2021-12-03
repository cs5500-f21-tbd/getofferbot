package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.User;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;

public class UserController {
    GenericRepository<User> userRepository;

    public UserController(GenericRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByDiscordId(long discordId) {
        // TODO: get all users and return the one that matches this discordId
        return null;
    }

    public void setUserZipcode(long discordId, String zipcode) {
        // TODO: if a user doesn't exist, create them here, otherwise update
    }

    public void setUserSearchRadiusMiles(long discordId, Double searchRadiusMiles) {
        // TODO: if a user doesn't exist, create them here, otherwise update
    }
}
