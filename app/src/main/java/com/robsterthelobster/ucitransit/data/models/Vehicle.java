package com.robsterthelobster.ucitransit.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by robin on 10/16/2017.
 */

public class Vehicle extends RealmObject{

    @SerializedName("standing_capacity")
    public String standingCapacity;
    @SerializedName("description")
    public String description;
    @SerializedName("seating_capacity")
    public String seatingCapacity;
    @SerializedName("last_updated_on")
    public String lastUpdatedOn;
    @SerializedName("call_name")
    public String callName;
    @SerializedName("speed")
    public double speed;
    @SerializedName("vehicle_id")
    public int vehicleId;
    @SerializedName("segment_id")
    public int segmentId;
    @SerializedName("passenger_load")
    public String passengerLoad;
    @SerializedName("route_id")
    public int routeId;
    @SerializedName("arrival_estimates")
    public RealmList<Arrival> arrivalEstimates = null;
    @SerializedName("tracking_status")
    public String trackingStatus;
    @SerializedName("location")
    public Coordinate location;
    @SerializedName("heading")
    public int heading;

    public String getStandingCapacity() {
        return standingCapacity;
    }

    public void setStandingCapacity(String standingCapacity) {
        this.standingCapacity = standingCapacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(String seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(int segmentId) {
        this.segmentId = segmentId;
    }

    public String getPassengerLoad() {
        return passengerLoad;
    }

    public void setPassengerLoad(String passengerLoad) {
        this.passengerLoad = passengerLoad;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public RealmList<Arrival> getArrivalEstimates() {
        return arrivalEstimates;
    }

    public void setArrivalEstimates(RealmList<Arrival> arrivalEstimates) {
        this.arrivalEstimates = arrivalEstimates;
    }

    public String getTrackingStatus() {
        return trackingStatus;
    }

    public void setTrackingStatus(String trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public int getHeading() {
        return heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }
}
