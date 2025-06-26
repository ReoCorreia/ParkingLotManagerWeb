package com.app.ParkingLotManagerWeb.controller;

import com.app.ParkingLotManagerWeb.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SetupController {
    private final ParkingLotService parkingLotService;

    @Autowired
    public SetupController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @GetMapping("/setup")
    public String showSetupPage() {
        return "slot-setup";
    }

    @PostMapping("/setup")
    public String handleSetup(@RequestParam String setupOption,
                              @RequestParam(required = false) Integer totalSlots,
                              @RequestParam(required = false) Integer smallSlots,
                              @RequestParam(required = false) Integer largeSlots,
                              @RequestParam(required = false) Integer oversizeSlots) {

        // Initialize the parking lot
        if ("custom".equals(setupOption)) {
            int small = smallSlots != null ? smallSlots : 0;
            int large = largeSlots != null ? largeSlots : 0;
            int oversize = oversizeSlots != null ? oversizeSlots : 0;
            parkingLotService.initializeCustom(small, large, oversize);
        } else {
            int total = totalSlots != null ? totalSlots : 0;
            parkingLotService.initializeEvenly(total);
        }
        return "redirect:/parking";
    }
}
