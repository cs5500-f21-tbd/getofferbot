package edu.northeastern.cs5500.starterbot.scraper;

import static com.google.common.truth.Truth.assertThat;

import edu.northeastern.cs5500.starterbot.model.Job;
import java.util.ArrayList;

public class IndeedScraperTest {
    @test
    void testIndeedScraper() {
        ArrayList<Job> jobs = new IndeedScraper().Scrape("", "Seattle, WA");
        assertThat(jobs).isNotNull();
        assertThat(jobs.size()).isEqualTo(15);
        for (Job job : jobs) {
            assertThat(job).isNotNull();
        }
    }
}
