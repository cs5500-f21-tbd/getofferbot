package edu.northeastern.cs5500.starterbot.controller;

import edu.northeastern.cs5500.starterbot.model.Experience;
import edu.northeastern.cs5500.starterbot.repository.GenericRepository;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This is the controller class of Experience, which converts experience input (a String) into
 * concrete Experience objects. Also create default cases upon initialization with an empty repo.
 */
public class ExperienceController {
    GenericRepository<Experience> experienceRepository;

    /**
     * Constructor. Initializes the controller and create default cases if Experience repo is empty.
     *
     * @param experienceRepository repo for Experience objects
     */
    public ExperienceController(GenericRepository<Experience> experienceRepository) {
        this.experienceRepository = experienceRepository;

        createDefaults();
    }

    /** Create default cases for experience. */
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

    /**
     * Produces Experience objects when supplied with String labels
     *
     * @param label - a non-null string, such as "intern"
     * @return desired Experience object
     */
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
