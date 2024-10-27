/**
 * Model class to store the name, latitude, and longitude of a country
 */
public class Country {
    private final double latitude;
    private final double longitude;
    private final String name;

    /**
     * @param latitude latitude in degrees of capital city
     * @param longitude longitude in degrees of capital city
     * @param name name of country
     */
    public Country(double latitude, double longitude, String name) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }

    /**
     * Get the latitude of the capital city
     * @return latitude in degrees of capital city
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Get tge longitude of the capital city
     * @return longitude in degrees of capital city
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Get the name of the country
     * @return name of country
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Country c)) return false;
        return c.getName().equals(name) && c.getLatitude() == latitude && c.getLongitude() == longitude;
    }


}
