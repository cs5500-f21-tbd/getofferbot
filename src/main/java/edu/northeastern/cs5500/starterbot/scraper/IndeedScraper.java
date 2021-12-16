// Class to scrap Indeed.com frontpage for job postings for SED or SDE with additional user inputs
//   user input can include job location or other information
//
// Reference https://mkyong.com/java/jsoup-basic-web-crawler-example/
//   https://www.geeksforgeeks.org/scraping-indeed-job-data-using-python/
//   https://www.youtube.com/watch?v=PPcgtx0sI2E

package edu.northeastern.cs5500.starterbot.scraper;

import edu.northeastern.cs5500.starterbot.model.Job;
import edu.northeastern.cs5500.starterbot.model.Location;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IndeedScraper implements Scraper {
    /**
     * scrape link to apply
     *
     * @param div Element to parse
     * @return String containing link to apply
     */
    private String scrapeLinkToApply(Element div) throws RuntimeException {
        String tmp_link = div.attr("href");
        if (tmp_link.equals("")) {
            throw new RuntimeException("Error: parsing link to apply.");
        }
        return "https://www.indeed.com" + tmp_link;
    }

    /**
     * scrape link to apply
     *
     * @param div Element to parse
     * @return String containing link to apply
     */
    private String scrapeCompany(Element div) {
        String tmp_company = div.select("span.companyName").text();
        if (tmp_company.equals("")) {
            throw new RuntimeException("Error: parsing company name");
        }
        return tmp_company;
    }

    /**
     * Scrape location info Note: as of now, return null if zipCode not available, but this behavior
     * could and should change
     *
     * @param div Element to parse
     * @return a location object, null if zipCode not available
     */
    private Location scrapeLocation(Element div) throws RuntimeException {
        String loc_tmp;
        String city;
        String state;
        String country;
        String zip;
        try {
            loc_tmp = div.select("div.companyLocation").text();
            city = loc_tmp.split(",")[0];
            state = loc_tmp.split(",")[1].substring(1, 3);
        } catch (Exception e) {
            throw new RuntimeException("Error: parsing location");
        }
        country = "USA";
        try {
            zip = loc_tmp.split(",")[1].substring(4, 9);
            int prob_zip = Integer.parseInt(zip);
            if (prob_zip == 0) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        /* TODO: still return a location object when zipCode is not available
            Location class needs to handle cases where zipCode is not available, or define an unknown value
        */
        Location location = new Location(zip, city, state, country);
        return location;
    }

    /**
     * Scrape company rating Note: set company rating, Indeed does not post rating for specific job
     * on scrap page, only rating for some companies are posted
     *
     * @param div Element to parse
     * @return company rating, 0.0f indicates rating missing
     */
    private Float scrapeRating(Element div) {
        Float rating;
        try {
            rating =
                    Float.parseFloat(
                            div.select("span.ratingNumber").attr("aria-label").substring(0, 3));
        } catch (Exception e) {
            return null;
        }
        return rating;
    }

    /**
     * Scrape job title
     *
     * @param div Element to parse
     * @return the title of the job
     * @throws RuntimeException
     */
    private String scrapeJobTitle(Element div) throws RuntimeException {
        Elements job_title_tmp = div.select("span");
        String job_title;
        try {
            if (job_title_tmp.get(0).text().equals("new")) {
                job_title = job_title_tmp.get(1).text();
            } else {
                job_title = job_title_tmp.get(0).text();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: parsing job title");
        }
        return job_title;
    }

    /**
     * Scrape annual payment. Note: due to definition of the AnnualPayment variable, only load jobs
     * with exact annual payment info hourly payment and payment range is not parsed
     *
     * @param div Element to parse
     * @return the annual payment in float, or null if the job posting does not include the right
     *     info
     */
    private Float scrapeAnnualPayment(Element div) {
        Float payment = null;
        try {
            String[] tmp_str = div.select("div.attribute_snippet").toString().split("\\$");
            if (tmp_str.length == 2) {
                if (tmp_str[1].split(" ")[2].charAt(0) == 'y') {
                    payment =
                            Float.parseFloat(String.join("", tmp_str[1].split(" ")[0].split(",")));
                }
            }
        } catch (Exception e) {
            return null;
        }
        return payment;
    }

    /**
     * working function to only scrape 1 job attribute from Element Note: Indeed does not post other
     * Job variables in a standard format on scrap page leave these variables in default value
     *
     * @param div_tmp Element to parse
     * @return a Job object
     * @throws RuntimeException
     */
    private Job scrapeOneJob(Element div_tmp) throws RuntimeException {
        String link_to_apply;
        String company;
        Element div;
        Location location;
        String job_title;
        try {
            link_to_apply = scrapeLinkToApply(div_tmp);
            div = div_tmp.select("div.job_seen_beacon").first();
            company = scrapeCompany(div);
            job_title = scrapeJobTitle(div);
            location = scrapeLocation(div);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error: error parsing key components");
        }

        Float rating = scrapeRating(div);
        Float payment = scrapeAnnualPayment(div);

        Job job = new Job(job_title, company, link_to_apply);
        if (location != null) {
            job.setLocation(location.getId());
        }
        if (rating != null) {
            job.setStarRating(rating);
        }
        if (payment != null) {
            job.setAnnualPay(payment);
        }
        return job;
    }

    /**
     * Workhouse to conduct the scrapping, details of the jobs to be queried from Indeed in encoded
     * in the input url, will try to fill as many variables of the Job class as available from the
     * page will return null if query failed
     *
     * @param URL (String) url to request to the Indeed webpage, address includes job querying
     *     details
     * @return (ArrayList<Job>) a list of Job object from the web scrapping based on job details
     * @throws IOException
     * @throws RuntimeException
     */
    private List<Job> scrape(String URL) throws IOException, RuntimeException {

        Document document;
        try {
            document = Jsoup.connect(URL).get();
        } catch (IOException e) {
            throw new IOException("Error: Indeed.com query failed.");
        }

        Elements divs = document.select("a[class^=tapItem fs-unmask]");
        List<Job> jobs = new ArrayList<Job>();

        for (Element div_tmp : divs) {
            Job job;
            try {
                job = scrapeOneJob(div_tmp);
            } catch (RuntimeException e) {
                throw new RuntimeException("Error: Jsoup Element parsing error.");
            }
            jobs.add(job);
        }
        return jobs;
    }

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
    @Nullable
    public List<Job> scrape(@Nullable String filter, @Nullable String location) {
        StringBuilder search_link = new StringBuilder("https://www.indeed.com/jobs?q=");
        if (filter == null || filter.equalsIgnoreCase("")) {
            search_link.append("software+development+engineer");
        } else {
            search_link.append(filter);
            search_link.append("+software+development+engineer");
        }

        // a location is required or Indeed.com will display a pop-up, breaking the scraper
        private static final String DEFAULT_LOCATION = "Seattle, WA";
        if (location == null || StringUtils.isBlank(location)) {
            location = DEFAULT_LOCATION;
        }
        search_link.append("&l=");
        search_link.append(location);

        try {
            return scrape(search_link.toString());
        } catch (Exception e) {
            return null;
        }
    }
}
