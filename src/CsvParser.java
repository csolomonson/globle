import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CsvParser {
    Scanner fileScanner;
    ArrayList<Country> countries;

    public CsvParser(String filename) throws FileNotFoundException {
        fileScanner = new Scanner(new File(filename));
    }

    public void scanFile() {
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] tokens = line.split(",");
            String name = tokens[0];
            double lat = Double.parseDouble(tokens[2]);
            double lon = Double.parseDouble(tokens[3]);
            Country c = new Country(lat, lon, name);
            countries.add(c);
        }
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }
}
