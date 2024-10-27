import java.util.ArrayList;

public class Globe {
    private static final double EARTH_RADIUS = 6371; //kilometers
    private static final double MAX_DISTANCE = 2*Math.PI*EARTH_RADIUS;

    private final ArrayList<Country> countries;

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
            double d = Math.abs(getGreatCircleDistance(lat, lon, c.getLatitude(), c.getLongitude()) - distance);
            if (d < nearestDistance) {
                nearestDistance = d;
                closestCountry = c;
            }
        }
        return closestCountry;
    }

    public Country getCountryClosestToDistance(Country center, double distance) {
        return getCountryClosestToDistance(center.getLatitude(), center.getLongitude(), distance);
    }

    public Country getClosestCountry(double lat, double lon) {
        return getCountryClosestToDistance(lat, lon, 0);
    }

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

    public static double getGreatCircleDistance(Country c1, Country c2) {
        return getGreatCircleDistance(c1.getLatitude(), c1.getLongitude(), c2.getLatitude(), c2.getLongitude());
    }

    Country getCountry(String name) {
        for (Country c : countries) {
            if (c.getName().toLowerCase().equals(name.toLowerCase())) return c;
        }
        return null;
    }

}
