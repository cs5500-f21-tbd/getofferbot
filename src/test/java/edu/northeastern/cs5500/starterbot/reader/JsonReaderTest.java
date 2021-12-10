package edu.northeastern.cs5500.starterbot.reader;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonReaderTest {
    JsonReader testReader;

    @BeforeEach
    void setUpReader() {
        testReader = new JsonReader();
    }

    @Test
    void testRead() throws Exception {
        Throwable e = null;
        try {
            String path = "";
            testReader.read(path);

        } catch (Throwable ex) {
            e = ex;
        }
        assertTrue(e instanceof FileNotFoundException);
    }
}
