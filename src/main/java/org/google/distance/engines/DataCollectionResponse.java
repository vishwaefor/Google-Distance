package org.google.distance.engines;

import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DataCollectionResponse {

    private String origin;
    private String destination;
    private TravelMode travelMode;
    private LocalDateTime departureDate;
    private ZoneOffset zoneOffset;
    private DistanceMatrix result;


    public DataCollectionResponse(DistanceMatrix result) {
        this.result = result;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public TravelMode getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(TravelMode travelMode) {
        this.travelMode = travelMode;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public ZoneOffset getZoneOffset() {
        return zoneOffset;
    }

    public void setZoneOffset(ZoneOffset zoneOffset) {
        this.zoneOffset = zoneOffset;
    }

    public DistanceMatrix getResult() {
        return result;
    }

    public void setResult(DistanceMatrix result) {
        this.result = result;
    }

    public DataCollectionResponse origin(String origin) {
        this.setOrigin(origin);
        return this;
    }

    public DataCollectionResponse destination(String destination) {
        this.setDestination(destination);
        return this;
    }

    public DataCollectionResponse travelMode(TravelMode travelMode) {
        this.setTravelMode(travelMode);
        return this;
    }

    public DataCollectionResponse departureDate(Instant departureDate, ZoneOffset zoneOffset) {
        this.setZoneOffset(zoneOffset);
        this.setDepartureDate(LocalDateTime.ofInstant(departureDate, zoneOffset));
        return this;
    }


}
