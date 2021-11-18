// Class to scrap Indeed.com frontpage for job postings for SED or SDE with additional user inputs
//   user input can include job location or other information
//
// Reference https://mkyong.com/java/jsoup-basic-web-crawler-example/
//   https://www.geeksforgeeks.org/scraping-indeed-job-data-using-python/
//   https://www.youtube.com/watch?v=PPcgtx0sI2E

package edu.northeastern.cs5500.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import edu.northeastern.cs5500.model.Job;
import edu.northeastern.cs5500.model.Location;

public class IndeedScraper implements Scraper{
    /**
     * scrape link to apply
     * @param div Element to parse
     * @return String containing link to apply
     */
    private String _scrapeLinkToApply(Element div) throws RuntimeException {
        String tmp_link = div.attr("href");
        if (tmp_link.equals("")){
            throw new RuntimeException("Error: parsing link to apply.");
        }
        return "https://www.indeed.com" + tmp_link;
    }

    /**
     * scrape link to apply
     * @param div Element to parse
     * @return String containing link to apply
     */
    private String _scrapeCompany(Element div) {
        String tmp_company = div.select("span.companyName").text();
        if (tmp_company.equals("")){
            throw new RuntimeException("Error: parsing company name");
        }
        return tmp_company;
    }

    /**
     * scrape location info
     * @param div Element to parse
     * @return a location object
     */
    private Location _scrapeLocation(Element div) throws RuntimeException{
        // set location
        String loc_tmp;
        Location location = new Location();
        try {
            loc_tmp = div.select("div.companyLocation").text();
            location.setCity(loc_tmp.split(",")[0]);
            location.setState(loc_tmp.split(",")[1].substring(1, 3));
        }catch (Exception e){
            throw new RuntimeException("Error: parsing location");
        }
        location.setCountry("USA"); // No plans of supporting job search outside of US
        try{
            String zip = loc_tmp.split(",")[1].substring(4, 9);
            location.setZipCode(zip);
        }catch (Exception e){
            location.setZipCode(""); // some job location do not include zipcode, quietly move on
        }
        return location;
    }

    /**
     *  scrape company rating
     * @param div Element to parse
     * @return company rating, 0.0f indicates rating missing
     */
    private float _scapeRating(Element div) {
        // set company rating, Indeed does not post rating for specific job on scrap page, only rating
        //   for some companies are posted
        float rating;
        try{
            rating = Float.parseFloat(div.select("span.ratingNumber").attr("aria-label").substring(0, 3));
        }catch (Exception e){
            return 0.0f;
        }
        return rating;
    }

    /**
     *  scrape job title
     * @param div Element to parse
     * @return the title of the job
     * @throws RuntimeException
     */
    private String _scrapeJobTitle(Element div) throws RuntimeException {
        // set job_title
        Elements job_title_tmp = div.select("span");
        String job_title;
        try {
            if (job_title_tmp.get(0).text().equals("new")) {
                job_title = job_title_tmp.get(1).text();
            } else {
                job_title = job_title_tmp.get(0).text();
            }
        }catch (Exception e){
            throw new RuntimeException("Error: parsing job title");
        }
        return job_title;
    }

    private float _scrapeAnnualPayment(Element div) {
        float payment = 0.0f;
        try{
            // due to definition of the AnnualPayment variable, only load jobs with exact annual payment info
            //   hourly payment and payment range is not parsed
            String[] tmp_str = div.select("div.attribute_snippet").toString().split("\\$");
            if (tmp_str.length == 2){
                if (tmp_str[1].split(" ")[2].charAt(0) == 'y') {
                    payment = Float.parseFloat(String.join("", tmp_str[1].split(" ")[0].split(",")));
                }
            }
        }catch (Exception e){
            return 0.0f; // some jobs do not post payment of payment, quietly move on
        }
        return payment;
    }

    /**
     * working function to only scrape 1 job attribute from Element
     * @param div_tmp Element to parase
     * @return a Job object
     * @throws IOException
     * @throws RuntimeException
     */
    private Job _scrapeOneJob(Element div_tmp) throws IOException, RuntimeException {
        String link_to_apply;
        String company;
        Element div;
        Location location;
        String job_title;
        try{
            link_to_apply = _scrapeLinkToApply(div_tmp);
            div = div_tmp.select("div.job_seen_beacon").first();
            company = _scrapeCompany(div);
            job_title = _scrapeJobTitle(div);
            location = _scrapeLocation(div);
        } catch (RuntimeException e){
            throw new RuntimeException("Error: error parsing ");
        }

        float rating = _scapeRating(div);
        float payment = _scrapeAnnualPayment(div);


        /* Indeed does not post other Job variables in a standard format on scrap page
             leave these variables in default value
         */

        // load info into a new Job object and add it to returning array
        Job job = new Job();
        job.setCompany(company);
        job.setJobTitle(job_title);
        job.setLocation(location);
        job.setLinkToApply(link_to_apply);
        if (rating != 0.0f) { job.setStarRating(rating); }
        if (payment != 0.0f) { job.setAnnualPay(payment); }
        return job;
    }

    /**
     * Workhouse to conduct the scrapping, details of the jobs to be queried from Indeed in encoded
     *   in the input url, will try to fill as many variables of the Job class as available from the page
     *   will return null if query failed
     * @param URL (String) url to request to the Indeed webpage, address includes job querying details
     * @return (ArrayList<Job>) a list of Job object from the web scrapping based on job details
     * @throws IOException
     * @throws RuntimeException
     */
    private ArrayList<Job> _scrape(String URL) throws IOException, RuntimeException{

        Document document;
        try {
            // Fetch the HTML code
            document = Jsoup.connect(URL).get();
        }catch (IOException e){
            throw new IOException("Error: Indeed.com query failed.");
        }

        Elements divs = document.select("a[class^=tapItem fs-unmask]");
        ArrayList<Job> jobs = new ArrayList<Job>();

        for (Element div_tmp: divs) {
            Job job;
            try {
                job = _scrapeOneJob(div_tmp);
            } catch (RuntimeException e){
                throw new RuntimeException("Error: Jsoup Element parsing error.");
            }
            jobs.add(job);
        }
        return jobs;
    }

    /**
     * encodes the job location to be queried into a web link and call helper function to scrap
     * @param location job location in the form of a String
     * @return a list of Job object from the web scrapping based on job details
     * @throws IOException
     * @throws RuntimeException
     */
    public ArrayList<Job> ScrapeLocation(String location) throws IOException, RuntimeException {
        StringBuilder search_link = new StringBuilder("https://www.indeed.com/jobs?q=software+development+engineer&l=");

        // only append location info if location String not empty
        if (location != null && !location.equals("")){
            search_link.append(location);
        }
        // handles exceptions
        try {
            return _scrape(search_link.toString());
        }catch (IOException e){
            throw new IOException("Indeed.com query failed.");
        }catch (RuntimeException e){
            throw new RuntimeException("Error: Jsoup Element parsing error.");
        }
    }

    /**
     * encodes the job attribute to be queried into a web link and call helper function to scrap
     *   Indeed only supports location as a separate query input, the reset of the attributes are all grouped
     *   together into one query input
     * @param filter job attribute (company, title...) other than location in the form of a String
     * @return a list of Job object from the web scrapping based on job details
     * @throws IOException
     * @throws RuntimeException
     */
    public ArrayList<Job> Scrape(String filter) throws IOException, RuntimeException {
        StringBuilder search_link = new StringBuilder("https://www.indeed.com/jobs?q=");
        if (filter == null || filter.equalsIgnoreCase("")) {
            // que_not_location String empty
            search_link.append("software+development+engineer");
        }else{
            search_link.append(filter);
            search_link.append("+software+development+engineer");
        }
        try{
            return _scrape(search_link.toString());
        }catch (IOException e){
            throw new IOException("Error: Indeed.com query failed.");
        }catch (RuntimeException e){
            throw new RuntimeException("Error: Jsoup Element parsing error.");
        }
    }

    /**
     * encodes the job attributs to be queried into a web link and call helper function to scrap
     *   Indeed only supports location as a separate query input, the reset of the attributes are all grouped
     *   together into one query input
     * @param filter job attribute (company, title...) other than location in the form of a String
     * @param location job location in the form of a String
     * @return a list of Job object from the web scrapping based on job details
     * @throws IOException
     * @throws RuntimeException
     */
    public ArrayList<Job> Scrape(String filter, String location) throws IOException, RuntimeException{
        StringBuilder search_link = new StringBuilder("https://www.indeed.com/jobs?q=");
        if (filter == null || filter.equalsIgnoreCase("")) {
            // que_not_location String empty
            search_link.append("software+development+engineer");
        }else{
            search_link.append(filter);
            search_link.append("+software+development+engineer");
        }

        // only append location info if location String not empty
        if (location != null){
            if (!location.equalsIgnoreCase("")){
                search_link.append("&l=");
                search_link.append(location);
            }
        }

        // handles exceptions
        try {
            return _scrape(search_link.toString());
        }catch (IOException e) {
            throw new IOException("Indeed.com query failed.");
        }catch (RuntimeException e) {
            throw new RuntimeException("Error: Jsoup Element parsing error.");
        }
    }
}
