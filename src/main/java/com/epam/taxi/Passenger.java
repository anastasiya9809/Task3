package com.epam.taxi;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Passenger implements Runnable {
    private Location initialLocation;
    private Location destinationLocation;

    public Location getInitialLocation() {
        return initialLocation;
    }

    public Location getDestinationLocation() {
        return destinationLocation;
    }

    @Override
    public void run() {
        Taxis taxis = Taxis.getInstance();

        Taxi taxi = taxis.orderTaxi(this);
        taxi.drivePassengerToDestination(this, taxis);
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "initialLocation=" + initialLocation +
                ", destinationLocation=" + destinationLocation +
                '}';
    }
}
