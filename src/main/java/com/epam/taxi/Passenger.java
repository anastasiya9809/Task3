package com.epam.taxi;

public class Passenger {
    private final int id;
    private final Location initialLocation;
    private final Location destinationLocation;

    public Passenger(int id, Location initialLocation, Location destinationLocation) {
        this.id = id;
        this.initialLocation = initialLocation;
        this.destinationLocation = destinationLocation;
    }

    public Location getInitialLocation() {
        return initialLocation;
    }

    public Location getDestinationLocation() {
        return destinationLocation;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", initialLocation=" + initialLocation +
                ", destinationLocation=" + destinationLocation +
                '}';
    }
}
