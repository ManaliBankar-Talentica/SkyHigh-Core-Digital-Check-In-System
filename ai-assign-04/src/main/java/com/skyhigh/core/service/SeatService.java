
package com.skyhigh.core.service;

import com.skyhigh.core.model.*;
import com.skyhigh.core.repository.SeatRepository;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SeatService {

  private static final Logger log = LoggerFactory.getLogger(SeatService.class);
  private final SeatRepository repo;

  public SeatService(SeatRepository repo) {
    this.repo = repo;
  }

  @Transactional
  public Seat holdSeat(String seatNumber, String passengerId) {
    Seat seat = repo.lockSeat(seatNumber)
        .orElseThrow(() -> new RuntimeException("Seat not found"));

    if (seat.getState() != SeatState.AVAILABLE)
      throw new RuntimeException("Seat not available");

    seat.setState(SeatState.HELD);
    seat.setHeldBy(passengerId);
    seat.setHoldExpiry(LocalDateTime.now().plusSeconds(120));
    log.info("Seat {} held by {}", seatNumber, passengerId);

    return repo.save(seat);
  }

  @Transactional
  public Seat confirmSeat(String seatNumber, String passengerId) {
    Seat seat = repo.lockSeat(seatNumber)
        .orElseThrow(() -> new RuntimeException("Seat not found"));

    if (!passengerId.equals(seat.getHeldBy()))
      throw new RuntimeException("Not seat holder");

    seat.setState(SeatState.CONFIRMED);
    log.info("Seat {} confirmed", seatNumber);
    return repo.save(seat);
  }

  public List<Seat> seatMap() {
    return repo.findAll();
  }
}
