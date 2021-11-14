// Hui Jiang
// Nov 13, 2021
// NEU CS5500 TBD GetOfferBot
// Placeholder for task #71

import java.sql.Timestamp;

enum Experience {intern, entry, mid, senior;}

public class Job {
    private Integer JobId;
    private String JobTitle;
    private String JobType;
    private Experience Experience;
    private String Company;
    private Timestamp Created;
    private float AnnualPay;
    private float StarRating;
    private boolean Sponsorship;
    private String LinkToApply;
    private Location Location;

    public Job(){
        JobId = null;
        JobTitle = null;
        JobType = null;
        Experience = null;
        Company = null;
        Created = null;
        AnnualPay = -1.0f;
        StarRating = -1.0f;
        Sponsorship = false;
        LinkToApply = null;
        Location = null;
    }

    public Job(String job_title, String company, Location location){
        this.JobTitle = job_title;
        this.Company = company;
        this.Location = location;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public void setLocation(Location location) {
        Location = location;
    }

    public void setCreated(Timestamp created) {
        Created = created;
    }

    public void setLinkToApply(String linkToApply) {
        LinkToApply = linkToApply;
    }

    public void setAnnualPay(float annualPay) {
        AnnualPay = annualPay;
    }

    public void setStarRating(float starRating) {
        StarRating = starRating;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public String getCompany() {
        return Company;
    }

    public Location getLocation() {
        return Location;
    }

    public Timestamp getCreated() {
        return Created;
    }

    public float getStarRating() {
        return StarRating;
    }

    public float getAnnualPay() {
        return AnnualPay;
    }

    public String getLinkToApply() {
        return LinkToApply;
    }
}