package edu.northeastern.cs5500.scraper;

import java.io.IOException;
import java.util.ArrayList;

import edu.northeastern.cs5500.model.Job;
import edu.northeastern.cs5500.model.Location;

public interface Scraper {
    public ArrayList<Job> ScrapeLocation(String location) throws IOException, RuntimeException;

    public ArrayList<Job> Scrape(String filter) throws IOException, RuntimeException;

    public ArrayList<Job> Scrape(String filter, String location) throws IOException, RuntimeException;
}
