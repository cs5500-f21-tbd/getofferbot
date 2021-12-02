package edu.northeastern.cs5500.starterbot.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northeastern.cs5500.starterbot.model.Education;
import java.io.File;
import java.io.FileNotFoundException;

// reference: https://www.baeldung.com/jackson-object-mapper-tutorial
public class JsonReader {

    public JsonReader() {}

    public void read(String path) throws FileNotFoundException {

        // create object mapper instance
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("Could not find files");
        } else {
            // convert JSON string to Book object
            try {
                ObjectMapper mapper = new ObjectMapper();
                Education edu = mapper.readValue(file, Education.class);
                System.out.println(edu.getName());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
