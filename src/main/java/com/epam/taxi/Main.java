package com.epam.taxi;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String path = "src/main/resources/taxis";

    public static void main(String[] args) throws TaxiException {
        Passengers passengers = Passengers.getInstance();
        passengers.add(new Passenger(1, new Location(1, 1),
                new Location(2,2)));
        passengers.add(new Passenger(2,new Location(2, 3),
                new Location(0,1)));
        passengers.add(new Passenger(3, new Location(1, 3),
                new Location(3,3)));
        passengers.add(new Passenger(4,new Location(0, 3),
                new Location(1,1)));

        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();

        try {
            Taxis taxis = mapper.readValue(file, Taxis.class);

            ExecutorService executorService = Executors.newFixedThreadPool(2);

            while (true) {
                for (Taxi taxi: taxis.getTaxis()) {
                    executorService.execute(taxi);

                    if (Taxi.getNumberOfTimesInARowPassengerWasNotFound() >= 5) {
                        executorService.shutdown();
                        TimeUnit.SECONDS.sleep(1);
                        return;
                    }
                }
            }
        }
        catch (IOException | InterruptedException e) {
            throw new TaxiException(e.getMessage(), e);
        }
    }
}
