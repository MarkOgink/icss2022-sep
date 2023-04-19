package nl.han.ica.icss.integration;

import nl.han.ica.icss.Pipeline;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class IntegrationTest {
    @Test
    void appTest() throws IOException {
        Pipeline pipeline = new Pipeline();
        //Open test file to parse
        ClassLoader classLoader = this.getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("level2.icss")) {
            assert inputStream != null;
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            pipeline.parseString(result);
            pipeline.check();
            pipeline.transform();
        }
        String level2 = pipeline.generate();
        try (InputStream inputStream = classLoader.getResourceAsStream("level0.icss")) {
            assert inputStream != null;
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            pipeline.parseString(result);
            pipeline.check();
            pipeline.transform();
        }
        String level0 = pipeline.generate();
        Assertions.assertEquals(level2,level0);
    }
}
