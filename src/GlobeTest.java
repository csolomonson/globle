import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GlobeTest {
    static Globe globe;
    static CsvParser parser;
    static ArrayList<Country> countries;
    @BeforeAll
    static void setup() {
        try {
            parser = new CsvParser("src/positions.csv");
            parser.scanFile();
            countries = parser.getCountries();
            globe = new Globe(countries);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void getCountryClosestToDistance() {
        assertEquals("Andorra", globe.getCountryClosestToDistance(31.415, 21.724, 2169).getName());
    }

    @Test
    void getClosestCountry() {
        assertEquals("Andorra", globe.getClosestCountry(42.5, 1.52).getName());
    }

    @Test
    void getGreatCircleDistance() {
        assertEquals(Globe.getGreatCircleDistance(-13.37, 42.069, 31.415, 21.724), 5432, 1);
    }
}