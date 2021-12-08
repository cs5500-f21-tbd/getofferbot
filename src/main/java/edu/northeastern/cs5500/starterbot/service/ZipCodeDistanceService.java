package edu.northeastern.cs5500.starterbot.service;

import com.google.gson.Gson;
import edu.northeastern.cs5500.starterbot.model.JsonDistance;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.annotation.Nullable;
import javax.inject.Singleton;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class ZipCodeDistanceService implements Service {

    public static final Double KILOMETERS_IN_A_MILE = 1.609344d;
    private static final String URL_TEMPLATE = "https://zipcode-radius.glitch.me/v1/distance/%s/%s";
    Gson gson;

    // can never throw MalformedURLException because all inputs are validated before creating the
    // URI
    @SneakyThrows(MalformedURLException.class)
    URL uriDistanceKilometers(String fromZip, String toZip) throws IllegalArgumentException {
        try {
            Integer.parseInt(fromZip);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("fromZip was not a valid 5-digit numeric zip code");
        }
        try {
            Integer.parseInt(toZip);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("toZip was not a valid 5-digit numeric zip code");
        }

        return new URL(String.format(URL_TEMPLATE, fromZip, toZip));
    }

    Double extractKilometersFromJsonReader(InputStreamReader reader) {
        JsonDistance kilometers = gson.fromJson(reader, JsonDistance.class);
        return kilometers.getKilometers();
    }

    @Nullable
    public Double getDistanceInKilometers(String fromZip, String toZip) {
        URL requestUrl = uriDistanceKilometers(fromZip, toZip);
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(requestUrl.openStream());
        } catch (IOException e) {
            // This means the server is down and there's nothing we can do about that.
            log.error("IOException reaching the zipcode-radius server", e);
            return null;
        }

        return extractKilometersFromJsonReader(reader);
    }

    @Nullable
    public Double getDistanceInMiles(String fromZip, String toZip) {
        Double kilometers = getDistanceInKilometers(fromZip, toZip);
        if (kilometers == null) {
            return null;
        }
        return kilometers / KILOMETERS_IN_A_MILE;
    }

    public ZipCodeDistanceService() {
        gson = new Gson();
    }

    @Override
    public void register() {
        log.info("ZipCodeDistanceService > register");
    }
}
