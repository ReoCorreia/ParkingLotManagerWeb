package com.app.ParkingLotManagerWeb.controller;

import com.app.ParkingLotManagerWeb.enums.VehicleSize;
import com.app.ParkingLotManagerWeb.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/parking")
public class ParkingLotController {

    private final ParkingLotService parkingLotService;

    @Autowired
    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @GetMapping
    public String showParkingLot(Model model) {
        // Redirect to setup if not initialized
        if (parkingLotService.getAllSlots() == null || parkingLotService.getAllSlots().isEmpty()) {
            return "redirect:/setup";
        }

        // Add parking lot data to the model
        model.addAttribute("slots", parkingLotService.getAllSlots());

        // Add individual statistics for simpler Thymeleaf usage
        var parkingLot = parkingLotService.getParkingLot();
        if (parkingLot != null) {
            model.addAttribute("totalSlots", parkingLot.getTotalSlots());
            model.addAttribute("availableSlots", parkingLot.getAvailableSlots());
            model.addAttribute("occupiedSlots", parkingLot.getOccupiedSlots());
        } else {
            model.addAttribute("totalSlots", 0);
            model.addAttribute("availableSlots", 0);
            model.addAttribute("occupiedSlots", 0);
        }

        return "parking-lot";
    }

    @PostMapping("/park")
    public String parkVehicle(@RequestParam String licensePlate,
                              @RequestParam VehicleSize vehicleSize,
                              RedirectAttributes redirectAttributes) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "License plate is required");
            return "redirect:/parking";
        }

        if (vehicleSize == null) {
            redirectAttributes.addFlashAttribute("error", "Vehicle size is required");
            return "redirect:/parking";
        }

        String result = parkingLotService.parkVehicle(licensePlate, vehicleSize);

        if (result.equals("SUCCESS")) {
            redirectAttributes.addFlashAttribute("success",
                    "Vehicle " + licensePlate + " parked successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", result);
        }

        return "redirect:/parking";
    }

    @PostMapping("/remove")
    public String removeVehicle(@RequestParam String licensePlate, RedirectAttributes redirectAttributes) {
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "License plate is required");
            return "redirect:/parking";
        }

        boolean success = parkingLotService.removeVehicle(licensePlate);

        if (success) {
            redirectAttributes.addFlashAttribute("success",
                    "Vehicle " + licensePlate + " removed successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error",
                    "Vehicle " + licensePlate + " not found in parking lot.");
        }

        return "redirect:/parking";
    }
}