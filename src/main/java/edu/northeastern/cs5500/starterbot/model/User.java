package edu.northeastern.cs5500.starterbot.model;

import edu.northeastern.cs5500.starterbot.service.ZipCodeDistanceService;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements Model {
    private ObjectId id;
    // event.getUser().getIdLong() gets this value
    @Nonnull private Long discordId;
    @Nullable private String zipcode;
    // search radius, in kilometers, for the user
    @Nullable private Double searchRadiusKilometers;

    public Double getSearchRadiusMiles() {
        return (searchRadiusKilometers == null)
                ? null
                : (searchRadiusKilometers / ZipCodeDistanceService.KILOMETERS_IN_A_MILE);
    }

    public void setSearchRadiusMiles(Double searchRadiusMiles) {
        searchRadiusKilometers =
                (searchRadiusMiles == null)
                        ? null
                        : (searchRadiusMiles * ZipCodeDistanceService.KILOMETERS_IN_A_MILE);
    }

    // add setter to set the zipcode 
    public void setZipcode(String zipcode) {
        zipcode = (zipcode == null) ? null : zipcode; 
    }
}
