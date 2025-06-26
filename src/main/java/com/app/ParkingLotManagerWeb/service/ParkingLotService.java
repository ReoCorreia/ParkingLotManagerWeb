package com.app.ParkingLotManagerWeb.service;

import com.app.ParkingLotManagerWeb.enums.SlotSize;
import com.app.ParkingLotManagerWeb.enums.VehicleSize;
import com.app.ParkingLotManagerWeb.model.ParkingLot;
import com.app.ParkingLotManagerWeb.model.ParkingSlot;
import com.app.ParkingLotManagerWeb.model.Vehicle;
import com.app.ParkingLotManagerWeb.strategy.FirstAvailableStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotService {
    private ParkingLot parkingLot = null;

    public ParkingLotService() {
        this.parkingLot = null;
    }

    public String parkVehicle(String licensePlate, VehicleSize vehicleSize) {
        if (parkingLot == null)
            return "Parking lot is not initialized. Please set up the parking lot first.";
        Vehicle vehicle = new Vehicle(licensePlate, vehicleSize);
        // Check if vehicle is already parked
        if (parkingLot.getVehicleSlotMap().containsKey(licensePlate)) {
            return "Vehicle " + licensePlate + " is already parked in the lot.";
        }

        // Try to find a suitable slot
        ParkingSlot slot = parkingLot.getStrategy().findSlot(parkingLot.getSlots(), vehicle);
        if (slot == null) {
            return "No suitable parking slot available for " + vehicleSize
                    + " vehicle. All compatible slots are occupied.";
        }

        // Park the vehicle
        slot.parkVehicle(vehicle);
        parkingLot.getVehicleSlotMap().put(licensePlate, slot);
        return "SUCCESS";
    }

    public boolean removeVehicle(String licensePlate) {
        if (parkingLot == null)
            return false;
        return parkingLot.removeVehicle(licensePlate);
    }

    public List<ParkingSlot> getAllSlots() {
        if (parkingLot == null)
            return new ArrayList<>();
        return parkingLot.getSlots();
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public VehicleSize[] getVehicleSizes() {
        return VehicleSize.values();
    }

    public SlotSize[] getSlotSizes() {
        return SlotSize.values();
    }

    public void initializeCustom(int small, int large, int oversize) {
        List<ParkingSlot> slots = new ArrayList<>();
        int id = 1;
        for (int i = 0; i < small; i++) {
            slots.add(new ParkingSlot(id++, SlotSize.SMALL));
        }
        for (int i = 0; i < large; i++) {
            slots.add(new ParkingSlot(id++, SlotSize.LARGE));
        }
        for (int i = 0; i < oversize; i++) {
            slots.add(new ParkingSlot(id++, SlotSize.OVERSIZE));
        }
        this.parkingLot = new ParkingLot(slots, new FirstAvailableStrategy());
    }

    public void initializeEvenly(int total) {
        int perType = total / 3;
        int remainder = total % 3;
        int small = perType;
        int large = perType;
        int oversize = perType;
        if (remainder > 0)
            small++;
        if (remainder > 1)
            large++;
        initializeCustom(small, large, oversize);
    }
}
