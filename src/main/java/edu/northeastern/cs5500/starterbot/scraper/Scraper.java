package edu.northeastern.cs5500.starterbot.scraper;

import edu.northeastern.cs5500.starterbot.model.Job;
import java.util.ArrayList;

public interface Scraper {
    /**
     * encodes the job attributs to be queried into a web link and call helper function to scrap
     * Indeed only supports location as a separate query input, the reset of the attributes are all
     * grouped together into one query input.
     *
     * @param filter job attribute (company, title...) other than location in the form of a String
     * @param location job location in the form of a String
     * @return a list of Job object from the web scrapping based on job details, returns null if
     *     scraping failed
     */
    ArrayList<Job> Scrape(String filter, String location);
}
