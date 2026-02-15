
package com.skyhigh.core.controller;

import com.skyhigh.core.model.Seat;
import com.skyhigh.core.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SeatController {

  private final SeatService seatService;
  private final BaggageService baggageService;

  public SeatController(SeatService seatService, BaggageService baggageService) {
    this.seatService = seatService;
    this.baggageService = baggageService;
  }

  @PostMapping("/hold")
  public Seat hold(@RequestParam String seatNumber, @RequestParam String passengerId) {
    return seatService.holdSeat(seatNumber, passengerId);
  }

  @PostMapping("/confirm")
  public Seat confirm(@RequestParam String seatNumber, @RequestParam String passengerId) {
    return seatService.confirmSeat(seatNumber, passengerId);
  }

  @GetMapping("/seats")
  public List<Seat> seatMap() {
    return seatService.seatMap();
  }

  @PostMapping("/baggage")
  public String baggage(@RequestParam double weight) {
    return baggageService.requiresPayment(weight)
        ? "WAITING_FOR_PAYMENT"
        : "IN_PROGRESS";
  }
}
