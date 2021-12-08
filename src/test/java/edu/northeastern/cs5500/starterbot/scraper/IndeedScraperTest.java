package edu.northeastern.cs5500.starterbot.scraper;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.Job;
import java.util.List;
import org.junit.jupiter.api.Test;

public class IndeedScraperTest {
    @Test
    void testIndeedScraper() {
        List<Job> jobs = new IndeedScraper().scrape("", "Seattle, WA");
        assertThat(jobs).isNotNull();
        assertThat(jobs.size()).isEqualTo(15);
        for (Job job : jobs) {
            assertThat(job).isNotNull();
        }
    }
}
