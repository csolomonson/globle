import java.util.ArrayList;

/**
 * A class to contain a list of countries, and do distance calculations and triangulations between them
 */
public class Globe {
    private static final double EARTH_RADIUS = 6371; //kilometers
    private static final double MAX_DISTANCE = 2*Math.PI*EARTH_RADIUS;

    private final ArrayList<Country> countries;

    /**
     * Instantiate a Globe with specific countries to search through
     * @param countries All the country names and coordinates to search through and triangulate
     */
    public Globe(ArrayList<Country> countries) {
        this.countries = countries;
    }

    /**
     * Find the country in this object's ArrayList that is the closest to the given distance away from a certain point
     * @param lat latitude of the center point
     * @param lon longitude of the center point
     * @param distance distance in kilometers away, using great circle distance
     * @return Country object that is the closest to the given distance away from the given point
     */
    public Country getCountryClosestToDistance(double lat, double lon, double distance) {
        if (countries.isEmpty()) return null;
        Country closestCountry = null;
        double nearestDistance = MAX_DISTANCE + distance;
        for (Country c : countries) {
            double d = Math.abs(getGreatCircleDistance(lat, lon, c.latitude(), c.longitude()) - distance);
            if (d < nearestDistance) {
                nearestDistance = d;
                closestCountry = c;
            }
        }
        return closestCountry;
    }

    /**
     * Find the country that minimizes great circle distance to a given point
     * @param lat Latitude in degrees
     * @param lon Longitude in degrees
     * @return Country object that is the closest to the given coordinates
     */
    public Country getClosestCountry(double lat, double lon) {
        return getCountryClosestToDistance(lat, lon, 0);
    }

    /**
     * Get the best next candidate for a country from this object's countries list
     * @param countries List of countries guessed in order
     * @param distances List of reported globle distances corresponding to each of the countries passed. Pass a negative number if the distance is "cooler" than a previously guessed distance.
     * @return The country that minimizes a calculated error index (note that it will never return a country that has already been guessed)
     */
    public Country triangulate(ArrayList<Country> countries, ArrayList<Double> distances) {
        Country currentAns = null;
        double errorIndex = -1;
        for (Country k : this.countries) {
            double error = 1;
            double lastDistance = -1;
            Country lastCountry = null;
            boolean viable = true;
            for (int i = 0; i < countries.size(); i++) {
                Country c = countries.get(i);
                if (c.equals(k)) viable = false;
                double d = distances.get(i);
                if (d >= 0) {
                    //d = Math.max(d, 100);
                    error *= Math.abs(getGreatCircleDistance(k, c) - d);
                    //capital distance is always larger than reported globle distance
                    if (getGreatCircleDistance(k,c) < d) error *= 100000;
                    lastDistance = d;
                    lastCountry = c;
                } else if (lastDistance > -1) {
                    error *= Math.max(getGreatCircleDistance(lastCountry, k) - getGreatCircleDistance(k, c), 1);
                    //System.out.println(lastCountry.getName());
                }
            }
            if (error < errorIndex || errorIndex < 0) {
                if (viable) {
                    errorIndex = error;
                    currentAns = k;
                }
            }
        }
        return currentAns;
    }

    /**
     * Get the distance in kilometers between two points on earth, traveling around the globe on a great circle of the earth
     * @param lat1 latitude of point 1 in degrees
     * @param lon1 longitude of point 1 in degrees
     * @param lat2 latitude of point 2 in degrees
     * @param lon2 longitude of point 2 in degrees
     * @return Distance between the two given points in kilometers
     */
    public static double getGreatCircleDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;
        
        //Formula comes from here
        //https://www.movable-type.co.uk/scripts/latlong.html
        double sinLat = Math.sin(deltaLat / 2);
        double sinLon = Math.sin(deltaLon / 2);
        double a = sinLat*sinLat + Math.cos(lat1) * Math.cos(lat2) * sinLon*sinLon;
        double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return c*EARTH_RADIUS;
    }

    /**
     * Distance between the coordinates that two Country objects contain, traveling on a great circle along the earth.
     * @param c1 Country 1
     * @param c2 Country 2
     * @return Distance between Country 1 and 2 in kilometers
     */
    public static double getGreatCircleDistance(Country c1, Country c2) {
        return getGreatCircleDistance(c1.latitude(), c1.longitude(), c2.latitude(), c2.longitude());
    }

    /**
     * Get a country object with a given name
     * @param name name of the country (must match the Country's name field, case-insensitive)
     * @return Country object if contained in this Globe, or else null
     */
    Country getCountry(String name) {
        for (Country c : countries) {
            if (c.name().equalsIgnoreCase(name)) return c;
        }
        return null;
    }
}