import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to handle parsing a csv of country names and locations
 */
public class CsvParser {
    Scanner fileScanner;
    ArrayList<Country> countries;

    /**
     * Start a scanner on the filename of the csv to read
     * @param filename File path of the csv to read
     * @throws FileNotFoundException Thrown if the specified file does not exist
     */
    public CsvParser(String filename) throws FileNotFoundException {
        fileScanner = new Scanner(new File(filename));
        countries = new ArrayList<>();
    }

    /**
     * Goes through the specified file and adds to the countries ArrayList
     */
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

    /**
     * An ArrayList of all the Country objects represented in the given csv file
     * Only works after scanFile() is run.
     * @return ArrayList of all the Country's in the csv
     */
    public ArrayList<Country> getCountries() {
        return countries;
    }
}
