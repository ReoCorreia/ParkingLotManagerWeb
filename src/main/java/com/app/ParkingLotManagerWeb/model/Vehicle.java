package com.app.ParkingLotManagerWeb.model;

import com.app.ParkingLotManagerWeb.enums.VehicleSize;

public class Vehicle {
    private final String licensePlate;
    private final VehicleSize size;

    public Vehicle(String licensePlate, VehicleSize vehicleSize) {
        this.licensePlate = licensePlate;
        this.size = vehicleSize;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleSize getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "licensePlate='" + licensePlate + '\'' +
                ", size=" + size +
                '}';
    }
}
