package edu.northeastern.cs5500.starterbot.controller;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.User;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserControllerTest {

    InMemoryRepository<User> userRepository;
    UserController userController;

    @BeforeEach
    void setUpRepoAndController() {
        userRepository = new InMemoryRepository<>();
        userController = new UserController(userRepository);
    }

    @Test
    void testThatInitialRepoIsEmpty() {
        User user = userController.getUserByDiscordId(1L);
        assertThat(user).isNull();
    }

    User setUpUser1WithZipCode98005() {
        userController.setUserZipcode(1L, "98005");
        return userController.getUserByDiscordId(1L);
    }

    User setUpUser1AndChangeToZipCode98103() {
        User user1 = setUpUser1WithZipCode98005();
        user1.setZipcode("98103");
        return user1;
    }

    User setUpUser2WithRadius25Miles() {
        userController.setUserSearchRadiusMiles(2L, 25d);
        return userController.getUserByDiscordId(2L);
    }

    User setUpUser2AndChangeTo2000Miles() {
        User user2 = setUpUser2WithRadius25Miles();
        user2.setSearchRadiusMiles(2000d);
        return user2;
    }

    @Test
    void testNullDiscordIdBehaviorOfSetZipCode() {
        User user1 = setUpUser1WithZipCode98005();
        assertThat(user1).isNotNull();
        assertThat(user1.getDiscordId()).isEqualTo(1L);
        assertThat(user1.getZipcode()).isEqualTo("98005");
    }

    @Test
    void testNonnullDiscordIdBehaviorOfSetZipCode() {
        User user1 = setUpUser1AndChangeToZipCode98103();
        assertThat(user1).isNotNull();
        assertThat(user1.getDiscordId()).isEqualTo(1L);
        assertThat(user1.getZipcode()).isNotEqualTo("98005");
        assertThat(user1.getZipcode()).isEqualTo("98103");
    }

    @Test
    void testNullDiscordIdBehaviorOfSetRadius() {
        User user2 = setUpUser2WithRadius25Miles();
        assertThat(user2).isNotNull();
        assertThat(user2.getDiscordId()).isEqualTo(2L);
        assertThat(user2.getSearchRadiusMiles()).isEqualTo(25d);
    }

    @Test
    void testNonnullDiscordIdBehaviorOfSetRadius() {
        User user2 = setUpUser2AndChangeTo2000Miles();
        assertThat(user2).isNotNull();
        assertThat(user2.getDiscordId()).isEqualTo(2L);
        assertThat(user2.getSearchRadiusMiles()).isNotEqualTo(25d);
        assertThat(user2.getSearchRadiusMiles()).isEqualTo(2000d);
    }
}
