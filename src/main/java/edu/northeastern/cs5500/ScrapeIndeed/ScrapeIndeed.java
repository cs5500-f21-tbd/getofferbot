// Hui Jiang
// Nov 13, 2021
// NEU CS5500 TBD GetOfferBot task #72
// Class to scrape Indeed.com frontpage for job postings for SED or SDE with additional user inputs
//   user input can include job location or other information
//
// Reference https://mkyong.com/java/jsoup-basic-web-crawler-example/
//   https://www.geeksforgeeks.org/scraping-indeed-job-data-using-python/
//   https://www.youtube.com/watch?v=PPcgtx0sI2E

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrapeIndeed {
    private ArrayList<Job> _Scrape(String URL) {
        // INPUT: URL (String) url to request to the Indeed webpage, address includes job querying
        // details
        // OUTPUT: (ArrayList<Job>) a list of Job object from the web scraping based on job details
        // DESCRIPTION: workhouse to conduct the scraping, details of the jobs to be queried from
        // Indeed in encoded
        //   in the input url, will try to fill as many variables of the Job class as available from
        // the page
        //   will return null if query failed
        try {
            // Fetch the HTML code
            Document document = Jsoup.connect(URL).get();
            // Elements divs = document.select("div.job_seen_beacon");
            Elements divs = document.select("a[class^=tapItem fs-unmask]");
            ArrayList<Job> jobs = new ArrayList<Job>();

            // debug
            // System.out.println(divs.get(0).text());

            for (Element div_tmp : divs) {
                // for(Element div: divs) {
                // get link to apply
                String link_to_apply = "https://www.indeed.com" + div_tmp.attr("href");

                Element div = div_tmp.select("div.job_seen_beacon").first();
                // set company
                String company = div.select("span.companyName").text();

                // set job_title
                Elements job_title_tmp = div.select("span");
                String job_title;
                if (job_title_tmp.get(0).text().equals("new")) {
                    job_title = job_title_tmp.get(1).text();
                } else {
                    job_title = job_title_tmp.get(0).text();
                }

                // set location
                String loc_tmp = div.select("div.companyLocation").text();
                Location location = new Location();
                location.setCity(loc_tmp.split(",")[0]);
                location.setState(loc_tmp.split(",")[1].substring(1, 3));
                location.setCountry("USA"); // No plans of supporting job search outside of US
                try {
                    Integer zip = Integer.parseInt(loc_tmp.split(",")[1].substring(4, 9));
                    location.setZipCode(zip);
                } catch (Exception e) {
                    ; // some job location do not include zipcode, quietly move on
                }

                // set company rating, Indeed does not post rating for specific job on scrape page,
                // only rating
                //   for some companies are posted
                float rating = -1.0f;
                try {
                    rating =
                            Float.parseFloat(
                                    div.select("span.ratingNumber")
                                            .attr("aria-label")
                                            .substring(0, 3));
                } catch (Exception e) {
                    ; // some jobs do not post company rating, quietly move on
                }

                // set annual payment
                float payment = -1.0f;
                try {
                    // due to definition of the AnnualPayment variable, only load jobs with exact
                    // annual payment info
                    //   hourly payment and payment range is not parsed
                    String[] tmp_str = div.select("div.attribute_snippet").toString().split("\\$");
                    if (tmp_str.length == 2) {
                        if (tmp_str[1].split(" ")[2].charAt(0) == 'y') {
                            payment =
                                    Float.parseFloat(
                                            String.join("", tmp_str[1].split(" ")[0].split(",")));
                        }
                    }
                } catch (Exception e) {
                    ; // some jobs do not post payment of payment, quietly move on
                }

                // Indeed does not post other Job variables in a standard format on scrape page
                //   leave these variables in default value

                // load info into a new Job object and add it to returning array
                Job job = new Job();
                job.setCompany(company);
                job.setJobTitle(job_title);
                job.setLocation(location);
                job.setLinkToApply(link_to_apply);
                if (rating != -1.0f) {
                    job.setStarRating(rating);
                }
                if (payment != -1.0f) {
                    job.setAnnualPay(payment);
                }
                jobs.add(job);
            }
            return jobs;

        } catch (IOException e) {
            // if query failed, return null to calling function indicating scraping error
            return null;
        }
    }

    public ArrayList<Job> ScrapeLocation(String location) {
        // INPUT: location (String) job location in the form of a String
        // OUTPUT: (ArrayList<Job>) a list of Job object from the web scraping based on job details
        // DESCRIPTION: encodes the job location to be queried into a web link and call helper
        // function to scrape
        String search_link = "https://www.indeed.com/jobs?q=software+development+engineer&l=";

        // only append location info if location String not empty
        if (location != null) {
            if (!location.equalsIgnoreCase("")) {
                search_link += location;
            }
        }
        return _Scrape(search_link);
    }

    public ArrayList<Job> ScrapeLocation(Location location) {
        // INPUT: location (Location) job location in the form of a Location object
        // OUTPUT: (ArrayList<Job>) a list of Job object from the web scraping based on job details
        // DESCRIPTION: encodes the job location to be queried into a web link and call helper
        // function to scrape

        String location_str = "";
        if (location == null
                || (location.getCity() == null
                        && location.getState() == null
                        && location.getZipCode() == null)) {
            ; // search SDE directly if Location object is empty,
        } else {
            if (location.getCity() != null) {
                location_str += location.getCity() + " ";
            }
            if (location.getState() != null) {
                location_str += location.getState() + " ";
            }
            if (location.getZipCode() != null) {
                location_str += location.getZipCode() + " ";
            }
        }
        String search_link = "https://www.indeed.com/jobs?q=software+development+engineer&l=";
        if (!location_str.equalsIgnoreCase("")) {
            search_link += location_str;
        }
        return _Scrape(search_link);
    }

    public ArrayList<Job> Scrape(String que_not_location) {
        // INPUT: que_not_location (String) job attribute (company, title...) other than location in
        // the form of a String
        // OUTPUT: (ArrayList<Job>) a list of Job object from the web scrpping based on job details
        // DESCRIPTION: encodes the job attribute to be queried into a web link and call helper
        // function to scrape
        //   Indeed only supports location as a separate query input, the reset of the attributes
        // are all grouped
        //   together into one query input
        String search_link = "https://www.indeed.com/jobs?q=";
        if (que_not_location == null || que_not_location.equalsIgnoreCase("")) {
            // que_not_location String empty
            search_link += "software+development+engineer";
        } else {
            search_link += que_not_location + "+software+development+engineer";
        }
        return _Scrape(search_link);
    }

    public ArrayList<Job> Scrape(String que_not_location, String location) {
        // INPUT: que_not_location (String) job attribute (company, title...) other than location in
        // the form of a String
        // INPUT: location (String) job location in the form of a String
        // OUTPUT: (ArrayList<Job>) a list of Job object from the web scraping based on job details
        // DESCRIPTION: encodes the job attributs to be queried into a web link and call helper
        // function to scrape
        //   Indeed only supports location as a separate query input, the reset of the attributes
        // are all grouped
        //   together into one query input
        String search_link = "https://www.indeed.com/jobs?q=";
        if (que_not_location == null || que_not_location.equalsIgnoreCase("")) {
            // que_not_location String empty
            search_link += "software+development+engineer";
        } else {
            search_link += que_not_location + "+software+development+engineer";
        }

        // only append location info if location String not empty
        if (location != null) {
            if (!location.equalsIgnoreCase("")) {
                search_link += "&l=" + location;
            }
        }
        return _Scrape(search_link);
    }
}
