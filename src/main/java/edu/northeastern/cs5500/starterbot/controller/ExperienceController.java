package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.Experience;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExperienceController {
    GenericRepository<Experience> experienceRepository;

    public ExperienceController(GenericRepository<Experience> experienceRepository) {
        this.experienceRepository = experienceRepository;

        createDefaults();
    }

    private void createDefaults() {
        if (experienceRepository.count() > 0) {
            // Only create default experience levels if none exist
            return;
        }
        experienceRepository.add(new Experience("intern", "Intern"));
        experienceRepository.add(new Experience("entry", "Entry-level"));
        experienceRepository.add(new Experience("mid", "Mid-level"));
        experienceRepository.add(new Experience("senior", "Senior"));
    }

    @Nullable
    public Experience getExperienceByLabel(@Nonnull String label) {
        for (Experience experience : experienceRepository.getAll()) {
            if (label.equals(experience.getLabel())) {
                return experience;
            }
        }
        return null;
    }
}
