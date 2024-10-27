import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {

    @Test
    void scanFile() {
        boolean success;
        try {
            CsvParser parser = new CsvParser("src/positions.csv");
            parser.scanFile();
            success = true;
        } catch (FileNotFoundException e) {
            success = false;
        }
        assertTrue(success);
    }

    @Test
    void getCountries() {
        boolean success;
        try {
            CsvParser parser = new CsvParser("src/positions.csv");
            parser.scanFile();
            success = true;
            ArrayList<Country> countries = parser.getCountries();
            assertEquals(196, countries.size());
            assertEquals(countries.getFirst(), new Country(34.5289, 69.1725, "Afghanistan"));
        } catch (FileNotFoundException e) {
            success = false;
        }
        assertTrue(success);

    }
}