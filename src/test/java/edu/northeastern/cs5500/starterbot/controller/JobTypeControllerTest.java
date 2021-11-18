package edu.northeastern.cs5500.starterbot.controller;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.JobType;
import edu.northeastern.cs5500.starterbot.repository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JobTypeControllerTest {

    InMemoryRepository<JobType> jobTypeRepository;
    JobTypeController jobTypeController;

    @BeforeEach
    void setUpRepoAndController() {
        jobTypeRepository = new InMemoryRepository<>();
        jobTypeController = new JobTypeController(jobTypeRepository);
    }

    @Test
    void testDefaultJobType() {
        assertThat(jobTypeRepository.getAll()).isNotEmpty();
    }

    @Test
    void testDefaultJobTypeHasFullTime() {
        assertThat(jobTypeController.getJobTypeByLabel("fulltime")).isNotNull();
    }
}
