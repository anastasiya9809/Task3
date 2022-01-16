package com.epam.taxi;

public class Taxi implements Runnable {
    private Location location;

    private boolean isAvailable = true;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public void run() {
        Passengers passengers = Passengers.getInstance();

        Passenger passenger = passengers.findPassenger(this);

        if (passenger == null) {
            return;
        }

        drivePassengerToDestination(passenger);
    }

    public void drivePassengerToDestination(Passenger passenger) {
        Location location = passenger.getDestinationLocation();
        setLocation(location);
        //available;
    }
}
