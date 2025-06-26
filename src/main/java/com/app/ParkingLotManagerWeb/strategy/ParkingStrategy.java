package com.app.ParkingLotManagerWeb.strategy;

import com.app.ParkingLotManagerWeb.model.ParkingSlot;
import com.app.ParkingLotManagerWeb.model.Vehicle;

import java.util.List;

public interface ParkingStrategy {
    ParkingSlot findSlot(List<ParkingSlot> slots, Vehicle vehicle);
}