package com.epam.taxi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Taxis {
    private static Taxis INSTANCE;

    private static final double MAXIMUM_RADIUS = 5;

    private final List<Taxi> taxis;

    public Taxis(List<Taxi> taxis) {
        this.taxis = taxis;
    }

    public static Taxis getInstance() {
        Taxis localInstance = INSTANCE;
        if (localInstance == null) {
            Lock lock = new ReentrantLock();
            lock.lock();

            localInstance = INSTANCE;
            if (localInstance == null) {
                INSTANCE = localInstance = new Taxis(new ArrayList<>());
            }

            lock.unlock();
        }

        return localInstance;
    }

    public void addTaxiToFrontOfList(Taxi taxi) {
        taxis.add(0, taxi);
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

    public Taxi orderTaxi(Passenger passenger) {
        double minimumDistance = 10;
        Taxi result = null;

        do {
            Lock lock = new ReentrantLock();
            lock.lock();

            for (Taxi taxi : taxis) {
                double distance = calculateDistance(passenger, taxi);
                if (distance <= MAXIMUM_RADIUS && distance < minimumDistance) {
                    minimumDistance = distance;
                    result = taxi;
                }
            }

            if (result != null) {
                taxis.remove(result);
                return result;
            }

            lock.unlock();
        } while (true);
    }
}
