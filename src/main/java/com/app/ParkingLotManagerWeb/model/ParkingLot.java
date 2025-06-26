package com.app.ParkingLotManagerWeb.model;

import com.app.ParkingLotManagerWeb.strategy.ParkingStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    private final List<ParkingSlot> slots;
    private final Map<String, ParkingSlot> vehicleSlotMap;
    private final ParkingStrategy strategy;

    public ParkingLot(List<ParkingSlot> slots, ParkingStrategy strategy) {
        this.slots = slots;
        this.vehicleSlotMap = new HashMap<>();
        this.strategy = strategy;
    }

    public boolean parkVehicle(Vehicle vehicle) {
        if (vehicleSlotMap.containsKey(vehicle.getLicensePlate())) {
            return false; // Vehicle already parked
        }
        ParkingSlot slot = strategy.findSlot(slots, vehicle);
        if (slot == null) {
            return false; // No suitable slot available
        }
        slot.parkVehicle(vehicle);
        vehicleSlotMap.put(vehicle.getLicensePlate(), slot);
        return true;
    }

    public boolean removeVehicle(String licensePlate) {
        ParkingSlot slot = vehicleSlotMap.get(licensePlate);
        if (slot == null) {
            return false; // Vehicle not found
        }
        slot.removeVehicle();
        vehicleSlotMap.remove(licensePlate);
        return true;
    }

    public List<ParkingSlot> getSlots() {
        return slots;
    }

    public Map<String, ParkingSlot> getVehicleSlotMap() {
        return vehicleSlotMap;
    }

    public int getTotalSlots() {
        return slots.size();
    }

    public int getOccupiedSlots() {
        return vehicleSlotMap.size();
    }

    public int getAvailableSlots() {
        return getTotalSlots() - getOccupiedSlots();
    }

    public ParkingStrategy getStrategy() {
        return strategy;
    }
}
