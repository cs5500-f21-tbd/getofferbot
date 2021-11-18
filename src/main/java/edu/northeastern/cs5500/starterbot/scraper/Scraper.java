package edu.northeastern.cs5500.starterbot.scraper;

import edu.northeastern.cs5500.starterbot.model.Job;
import java.io.IOException;
import java.util.ArrayList;

public interface Scraper {
    public ArrayList<Job> ScrapeLocation(String location) throws IOException, RuntimeException;

    public ArrayList<Job> Scrape(String filter) throws IOException, RuntimeException;

    public ArrayList<Job> Scrape(String filter, String location)
            throws IOException, RuntimeException;
}
