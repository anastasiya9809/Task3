package com.epam.taxi;

public class Taxi {
    private Location location;

    public Taxi(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void drivePassengerToDestination(Passenger passenger, Taxis taxis) {
        location = passenger.getDestinationLocation();
        taxis.addTaxiToFrontOfList(this);
    }
}
