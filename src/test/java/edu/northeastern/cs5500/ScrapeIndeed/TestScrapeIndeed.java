// Hui Jiang
// Nov 13, 2021
// NEU CS5500 TBD GetOfferBot task #72
// Class to test ScrapeIndeed class

import java.sql.Timestamp;
import java.util.ArrayList;

public class TestScrapeIndeed {
    public static void main(String[] args) {
        String loc = "Seattle, WA";
        String que = "";

        ArrayList<Job> jobs = new ScrapeIndeed().Scrape(que, loc);
        if (jobs == null) {
            System.out.println("ERROR: Error Scraping Indeed");
        }
        for (Job job : jobs) {
            System.out.println("Job Title: " + job.getJobTitle());
            System.out.println("Job Company: " + job.getCompany());

            Location location = job.getLocation();
            if (location.getZipCode() == null) {
                System.out.println(
                        "Job Location: " + location.getCity() + ", " + location.getState());
            } else {
                System.out.println(
                        "Job Location: "
                                + location.getCity()
                                + ", "
                                + location.getState()
                                + " "
                                + location.getZipCode().toString());
            }

            System.out.println("Link to Apply: " + job.getLinkToApply());

            float rating = job.getStarRating();
            if (rating != -1.0f) {
                System.out.println("Company Rating: " + rating);
            }

            float payment = job.getAnnualPay();
            if (payment != -1.0f) {
                System.out.println("Annual Payment: " + payment);
            }

            Timestamp created = job.getCreated();
            if (created != null) {
                System.out.println("Job Created at: " + created.toString());
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }
}
