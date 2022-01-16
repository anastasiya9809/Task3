package com.epam.taxi;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String path = "src/main/resources/passengers";

    public static void main(String[] args) throws TaxiException {
        Taxis taxis = Taxis.getInstance();
        taxis.addTaxiToFrontOfList(new Taxi(new Location(0, 0)));
        taxis.addTaxiToFrontOfList(new Taxi(new Location(2, 2)));

        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();

        try {
            Passengers passengers = mapper.readValue(file, Passengers.class);
            List<Passenger> passengerList = passengers.getPassengers();

            ExecutorService executorService = Executors.newFixedThreadPool(4);

            for (Passenger passenger : passengerList) {
                executorService.execute(passenger);
            }

            executorService.shutdown();
            TimeUnit.SECONDS.sleep(1);
        }
        catch (IOException | InterruptedException e) {
            throw new TaxiException(e.getMessage(), e);
        }
    }
}
