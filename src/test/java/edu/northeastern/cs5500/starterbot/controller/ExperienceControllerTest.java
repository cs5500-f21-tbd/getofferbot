package edu.northeastern.cs5500.starterbot.controller;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.Experience;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import org.junit.jupiter.api.Test;

class ExperienceControllerTest {
    @Test
    void testDefaultExperience() {
        InMemoryRepository<Experience> experienceRepository = new InMemoryRepository<>();
        ExperienceController experienceController = new ExperienceController(experienceRepository);

        assertThat(experienceRepository.getAll()).isNotEmpty();
    }

    @Test
    void testDefaultExperienceHasIntern() {
        InMemoryRepository<Experience> experienceRepository = new InMemoryRepository<>();
        ExperienceController experienceController = new ExperienceController(experienceRepository);

        assertThat(experienceController.getExperienceByLabel("intern")).isNotNull();
    }
}
