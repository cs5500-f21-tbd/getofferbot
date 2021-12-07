package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.User;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;

import javax.annotation.Nullable;

/**
 * This is the controller class for User, which converts user-related inputs into concrete
 * User objects.
 */
public class UserController {
    GenericRepository<User> userRepository;

    public UserController(GenericRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    @Nullable
    public User getUserByDiscordId(Long discordId) {
        for (User user: userRepository.getAll()) {
            if (user.getDiscordId().equals(discordId)) {
                return user;
            }
        }
        return null;
    }

    public void setUserZipcode(Long discordId, String zipcode) {
        User user = getUserByDiscordId(discordId);
        if (user != null) {
            user.setZipcode(zipcode);
        } else {
            User newUser = new User(discordId);
            newUser.setZipcode(zipcode);
            userRepository.add(newUser);
        }
    }

    public void setUserSearchRadiusMiles(Long discordId, Double searchRadiusMiles) {
        User user = getUserByDiscordId(discordId);
        if (user != null) {
            user.setSearchRadiusMiles(searchRadiusMiles);
        } else {
            User newUser = new User(discordId);
            newUser.setSearchRadiusMiles(searchRadiusMiles);
            userRepository.add(newUser);
        }
    }
}
