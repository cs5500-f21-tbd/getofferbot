package edu.northeastern.cs5500.starterbot.controller;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.Experience;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExperienceControllerTest {

    InMemoryRepository<Experience> experienceRepository;
    ExperienceController experienceController;

    @BeforeEach
    void setUpRepoAndController() {
        experienceRepository = new InMemoryRepository<>();
        experienceController = new ExperienceController(experienceRepository);
    }

    @Test
    void testDefaultExperienceIsCreated() {
        assertThat(experienceRepository.getAll()).isNotEmpty();
    }

    @Test
    void testDefaultExperienceHasIntern() {
        assertThat(experienceController.getExperienceByLabel("intern")).isNotNull();
    }
}
