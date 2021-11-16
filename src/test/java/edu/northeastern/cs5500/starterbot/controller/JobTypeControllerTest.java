package edu.northeastern.cs5500.starterbot.controller;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.JobType;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import org.junit.jupiter.api.Test;

class JobTypeControllerTest {
    @Test
    void testDefaultJobType() {
        InMemoryRepository<JobType> jobTypeRepository = new InMemoryRepository<>();
        JobTypeController jobTypeController = new JobTypeController(jobTypeRepository);

        assertThat(jobTypeRepository.getAll()).isNotEmpty();
    }

    @Test
    void testDefaultJobTypeHasIntern() {
        InMemoryRepository<JobType> jobTypeRepository = new InMemoryRepository<>();
        JobTypeController jobTypeController = new JobTypeController(jobTypeRepository);

        assertThat(jobTypeController.getJobTypeByLabel("fulltime")).isNotNull();
    }
}
