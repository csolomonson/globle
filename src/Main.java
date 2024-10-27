import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Globe globe;
        try {
            CsvParser parse = new CsvParser("src/positions.csv");
            parse.scanFile();
            globe = new Globe(parse.countries);

        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found :(");
            return;
        }
        Scanner input = new Scanner(System.in);
        ArrayList<Country> countriesGuessed = new ArrayList<>();
        ArrayList<Double> distances = new ArrayList<>();
        String countryName = "";
        while (!countryName.equals("done")) {
            System.out.println("Enter the country you guessed: ");
            countryName = input.nextLine();
            Country c = globe.getCountry(countryName);
            if (c != null) {
                System.out.println("Enter distance in km: ");
                double distance = input.nextDouble();
                countriesGuessed.add(c);
                distances.add(distance);
                Country ans = globe.triangulate(countriesGuessed, distances);
                double actualDist = Globe.getGreatCircleDistance(c, ans);
                System.out.printf("Country: %s, distance: %f%n", ans.name(), actualDist);
            }
        }

    }
}