// Hui Jiang
// Nov 13, 2021
// NEU CS5500 TBD GetOfferBot
// Placeholder for task #71

public class Location {
    private Integer ZipCode;
    private String City;
    private String State;
    private String Country;

    public Location() {
        ZipCode = null;
        City = null;
        State = null;
        Country = null;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setState(String state) {
        State = state;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setZipCode(Integer zipCode) {
        ZipCode = zipCode;
    }

    public Integer getZipCode() {
        return ZipCode;
    }

    public String getCity() {
        return City;
    }

    public String getState() {
        return State;
    }

    public String getCountry() {
        return Country;
    }

    public Location(String city, String state) {
        this.City = city;
        this.State = state;
    }
}
