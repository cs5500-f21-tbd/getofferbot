package edu.northeastern.cs5500.starterbot.scraper;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.Job;
import java.util.List;
import org.junit.jupiter.api.Test;

public class IndeedScraperTest {
    @Test
    void testIndeedScraperNoInput() {
        List<Job> jobs = new IndeedScraper().scrape("", "");
        assertThat(jobs.size()).isEqualTo(15);
        for (Job job : jobs) {
            assertThat(job).isNotNull();
        }
    }

    @Test
    void testIndeedScraperNoLocation() {
        List<Job> jobs = new IndeedScraper().scrape("amazon", "");
        assertThat(jobs.size()).isEqualTo(15);
        for (Job job : jobs) {
            assertThat(job).isNotNull();
        }
    }

    @Test
    void testIndeedScraperWOKeyword() {
        List<Job> jobs = new IndeedScraper().scrape("", "Seattle, WA");
        assertThat(jobs).isNotNull();
        assertThat(jobs.size()).isEqualTo(15);
        for (Job job : jobs) {
            assertThat(job).isNotNull();
        }
    }

    @Test
    void testIndeedScraperWKeyword() {
        List<Job> jobs = new IndeedScraper().scrape("amazon", "Seattle, WA");
        assertThat(jobs.size()).isEqualTo(15);
        for (Job job : jobs) {
            assertThat(job).isNotNull();
        }
    }
}
