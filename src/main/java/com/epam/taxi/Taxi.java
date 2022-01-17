package com.epam.taxi;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Taxi implements Runnable {
    private static final Lock LOCK = new ReentrantLock();

    private static int NUMBER_OF_TIMES_IN_A_ROW_PASSENGER_WAS_NOT_FOUND = 0;

    private int id;

    private Location location;

    private boolean isAvailable = true;

    public static int getNumberOfTimesInARowPassengerWasNotFound() {
        return NUMBER_OF_TIMES_IN_A_ROW_PASSENGER_WAS_NOT_FOUND;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public void run() {
        LOCK.lock();

        if (!isAvailable) {
            return;
        }

        Passengers passengers = Passengers.getInstance();

        Passenger passenger = passengers.findPassenger(this);

        LOCK.unlock();

        if (passenger == null) {
            NUMBER_OF_TIMES_IN_A_ROW_PASSENGER_WAS_NOT_FOUND++;
            return;
        }

        NUMBER_OF_TIMES_IN_A_ROW_PASSENGER_WAS_NOT_FOUND = 0;
        drivePassengerToDestination(passenger);
    }

    public void drivePassengerToDestination(Passenger passenger) {
        Location location = passenger.getDestinationLocation();
        setLocation(location);
        isAvailable = true;
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "id=" + id +
                ", location=" + location +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
