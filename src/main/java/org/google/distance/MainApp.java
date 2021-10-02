package org.google.distance;

import com.google.maps.model.TravelMode;
import org.google.distance.engines.APIConsumer;
import org.google.distance.engines.DataCollectionRequest;
import org.google.distance.engines.FileWriter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class MainApp {

    private static final String API_KEY = "put your key here";
    private static final String OUTPUT_DIRECTORY = "D:/output";

    public static void main(String[] args) throws InterruptedException {

        // Add any number of requests like t1 and t2 ....

        var t1 = new Thread(() -> {
            var request = new DataCollectionRequest()
                    .origin("Colombo, Sri Lanka")
                    .destination("Kandy, Sri Lanka")
                    .travelMode(TravelMode.DRIVING)
                    .startTime(LocalDateTime.of(2021, 10, 16, 8, 30)) // from 2021-10-16 8:30
                    .endTime(LocalDateTime.of(2021, 10, 16, 10, 30)) // to 2021-10-16 10:30
                    .zoneOffset(ZoneOffset.ofHoursMinutes(5, 30)) // Sri Lanka
                    .frequencyInSeconds(300); // 5 Minutes


            new APIConsumer(API_KEY).getDistanceMatrixAsync(request, d -> new FileWriter(OUTPUT_DIRECTORY).writeResults(d));

        });

        var t2 = new Thread(() -> {
            var request = new DataCollectionRequest()
                    .origin("Colombo, Sri Lanka")
                    .destination("Polonnaruwa, Sri Lanka")
                    .travelMode(TravelMode.DRIVING)
                    .startTime(LocalDateTime.of(2021, 10, 16, 8, 30)) // from 2021-10-16 8:30
                    .endTime(LocalDateTime.of(2021, 10, 16, 10, 30)) // to 2021-10-16 10:30
                    .zoneOffset(ZoneOffset.ofHoursMinutes(5, 30)) // Sri Lanka
                    .frequencyInSeconds(600); // 10 Minutes

            new APIConsumer(API_KEY).getDistanceMatrixAsync(request, d -> new FileWriter(OUTPUT_DIRECTORY).writeResults(d));

        });

        // Start

        t1.start();
        t2.start();

        // Wait until finish

        t1.join();
        t2.join();


    }
}
