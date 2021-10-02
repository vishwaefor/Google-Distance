package org.google.distance.engines;

import com.google.maps.model.TravelMode;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DataCollectionRequest {

    private String origin;
    private String destination;
    private TravelMode travelMode;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ZoneOffset zoneOffset;
    private long frequencyInSeconds;


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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ZoneOffset getZoneOffset() {
        return zoneOffset;
    }

    public void setZoneOffset(ZoneOffset zoneOffset) {
        this.zoneOffset = zoneOffset;
    }

    public long getFrequencyInSeconds() {
        return frequencyInSeconds;
    }

    public void setFrequencyInSeconds(long frequencyInSeconds) {
        this.frequencyInSeconds = frequencyInSeconds;
    }

    public DataCollectionRequest origin(String origin) {
        this.setOrigin(origin);
        return this;
    }

    public DataCollectionRequest destination(String destination) {
        this.setDestination(destination);
        return this;
    }

    public DataCollectionRequest travelMode(TravelMode travelMode) {
        this.setTravelMode(travelMode);
        return this;
    }

    public DataCollectionRequest startTime(LocalDateTime startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public DataCollectionRequest endTime(LocalDateTime endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public DataCollectionRequest zoneOffset(ZoneOffset zoneOffset) {
        this.setZoneOffset(zoneOffset);
        return this;
    }

    public DataCollectionRequest frequencyInSeconds(long frequencyInSeconds) {
        this.setFrequencyInSeconds(frequencyInSeconds);
        return this;
    }
}
