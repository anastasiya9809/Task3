package com.epam.taxi;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Passengers {
    private static final Logger LOGGER = Logger.getLogger(Passengers.class);

    private static final double MAXIMUM_RADIUS = 5;

    private static Passengers INSTANCE;

    private final List<Passenger> passengers = new ArrayList<>();

    public static Passengers getInstance() {
        Passengers localInstance = INSTANCE;
        if (localInstance == null) {
            Lock lock = new ReentrantLock();
            lock.lock();

            localInstance = INSTANCE;
            if (localInstance == null) {
                INSTANCE = localInstance = new Passengers();
            }

            lock.unlock();
        }

        return localInstance;
    }

    public void add(Passenger passenger) {
        passengers.add(passenger);
    }

    public double calculateDistance(Passenger passenger, Taxi taxi) {
        Location passengerLocation = passenger.getInitialLocation();
        Location taxiLocation = taxi.getLocation();

        double passengerXCoordinate = passengerLocation.getXCoordinate();
        double passengerYCoordinate = passengerLocation.getYCoordinate();

        double taxiXCoordinate = taxiLocation.getXCoordinate();
        double taxiYCoordinate = taxiLocation.getYCoordinate();

        double distanceSquared = Math.pow(passengerXCoordinate - taxiXCoordinate, 2) +
                Math.pow(passengerYCoordinate - taxiYCoordinate, 2);

        return Math.sqrt(distanceSquared);
    }

    public Passenger findPassenger(Taxi taxi) {
        double minimumDistance = 10;

        Passenger result = null;

        for (Passenger passenger : passengers) {
            double distance = calculateDistance(passenger, taxi);
            if (distance <= MAXIMUM_RADIUS && distance < minimumDistance) {
                minimumDistance = distance;
                result = passenger;
            }
        }

        if (result != null) {
            passengers.remove(result);
            taxi.setAvailable(false);

            LOGGER.info(taxi + "\n" + result + "\n\n");
        }

        return result;
    }
}
