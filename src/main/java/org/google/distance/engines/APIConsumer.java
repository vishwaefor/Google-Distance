package org.google.distance.engines;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class APIConsumer {

    private String apiKey;
    private static Logger logger = LoggerFactory.getLogger(APIConsumer.class);

    public APIConsumer(String apiKey) {
        this.apiKey = apiKey;
    }


    public void getDistanceMatrixAsync(DataCollectionRequest request, Consumer<DataCollectionResponse> consumerFunction) {

        var origin = request.getOrigin();
        var destination = request.getDestination();
        Instant startTime;
        Instant endTime;
        long frequency;
        TravelMode travelMode;
        ZoneOffset zoneOffset;

        if (origin == null || origin.isBlank() || origin.isEmpty()) {
            logger.info("Origin is not valid");
            return;
        }

        if (destination == null || destination.isBlank() || destination.isEmpty()) {
            logger.info("Destination is not valid");
            return;
        }

        if (request.getTravelMode() != null) {
            travelMode = request.getTravelMode();
        } else {
            travelMode = TravelMode.DRIVING;
        }

        if (request.getZoneOffset() != null) {
            zoneOffset = request.getZoneOffset();
        } else {
            zoneOffset = ZoneOffset.UTC;
        }

        if (request.getStartTime() != null) {
            startTime = request.getStartTime().toInstant(zoneOffset);
        } else {
            startTime = Instant.now();
        }

        if (request.getFrequencyInSeconds() <= 0) {
            frequency = 5L;
        } else {
            frequency = request.getFrequencyInSeconds();
        }

        if (request.getEndTime() != null) {
            endTime = request.getEndTime().toInstant(zoneOffset);
        } else {
            endTime = startTime.plus(frequency * 5, ChronoUnit.SECONDS);
        }

        var context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        var departureDateTime = startTime;

        while (departureDateTime.isBefore(endTime)) {
            try {
                var apiRequest = DistanceMatrixApi
                        .getDistanceMatrix(context, new String[]{request.getOrigin()}, new String[]{request.getDestination()})
                        .mode(travelMode)
                        .departureTime(departureDateTime);


                Instant finalDepartureDateTime = departureDateTime;
                apiRequest.setCallback(new PendingResult.Callback<>() {

                    @Override
                    public void onResult(DistanceMatrix result) {
                        logger.info("Received results for {},{} pair on {} with mode {}", origin, destination, finalDepartureDateTime, travelMode);
                        var response = new DataCollectionResponse(result)
                                .origin(origin)
                                .destination(destination)
                                .travelMode(travelMode)
                                .departureDate(finalDepartureDateTime, zoneOffset);
                        consumerFunction.accept(response);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        logger.debug(e.getMessage());
                    }
                });

                TimeUnit.SECONDS.sleep(1); // for matching google api limits

                departureDateTime = departureDateTime.plus(frequency, ChronoUnit.SECONDS);

            } catch (InterruptedException e) {
                logger.debug(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        context.shutdown();
        logger.info("Finished successfully");


    }
}
