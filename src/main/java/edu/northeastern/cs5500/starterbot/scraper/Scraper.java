package edu.northeastern.cs5500.starterbot.scraper;

import edu.northeastern.cs5500.starterbot.model.Job;
import java.io.IOException;
import java.util.ArrayList;

public interface Scraper {
    public ArrayList<Job> scrapeLocation(String location) throws IOException, RuntimeException;

    public ArrayList<Job> scrape(String filter) throws IOException, RuntimeException;

    public ArrayList<Job> scrape(String filter, String location)
            throws IOException, RuntimeException;
}
