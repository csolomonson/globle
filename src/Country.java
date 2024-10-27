/**
 * Record class to store the name, latitude, and longitude of a country
 */
public record Country(double latitude, double longitude, String name) {
    /**
     * @param latitude  latitude in degrees of capital city
     * @param longitude longitude in degrees of capital city
     * @param name      name of country
     */
    public Country {
    }

    /**
     * Get the latitude of the capital city
     *
     * @return latitude in degrees of capital city
     */
    @Override
    public double latitude() {
        return latitude;
    }

    /**
     * Get tge longitude of the capital city
     *
     * @return longitude in degrees of capital city
     */
    @Override
    public double longitude() {
        return longitude;
    }

    /**
     * Get the name of the country
     *
     * @return name of country
     */
    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Country c)) return false;
        return c.name().equals(name) && c.latitude() == latitude && c.longitude() == longitude;
    }


}
