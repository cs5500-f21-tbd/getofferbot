package edu.northeastern.cs5500.starterbot.service;

import com.google.gson.Gson;
import edu.northeastern.cs5500.starterbot.model.JsonDistance;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

// IMPORTANT NOTE: This is more like a calculator instead of a service and needs refactoring to make
// sure
// it conforms to the software engineering definition of a service. Nevertheless, it produces the
// distance,
// in miles, between two zip codes. See related tests for details.

@Singleton
@Slf4j
public class ZipCodeDistanceService implements Service {

    private static final Double KILOMETERS_IN_A_MILE = 1.609344d;
    @Getter Double miles;

    private URI appendBaseUri(String thisZip, String otherZip) throws URISyntaxException {
        URI baseUri = new URI("https://zipcode-radius.glitch.me/v1/distance/");
        String newPath = baseUri.getPath() + thisZip + "/" + otherZip;
        return baseUri.resolve(newPath);
    }

    public ZipCodeDistanceService(String thisZip, String otherZip)
            throws URISyntaxException, IOException {
        URI appendedUri = appendBaseUri(thisZip, otherZip);
        InputStreamReader reader = new InputStreamReader(appendedUri.toURL().openStream());
        Gson gson = new Gson();
        JsonDistance kilometers = gson.fromJson(reader, JsonDistance.class);
        miles = kilometers.getKilometers() / KILOMETERS_IN_A_MILE;
    }

    @Override
    public void register() {
        log.info("ZipCodeDistanceService > register");
    }
}
