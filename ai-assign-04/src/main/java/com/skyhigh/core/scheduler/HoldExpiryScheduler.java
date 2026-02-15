
package com.skyhigh.core.scheduler;

import com.skyhigh.core.model.*;
import com.skyhigh.core.repository.SeatRepository;
import org.slf4j.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class HoldExpiryScheduler {

  private static final Logger log = LoggerFactory.getLogger(HoldExpiryScheduler.class);
  private final SeatRepository repo;

  public HoldExpiryScheduler(SeatRepository repo) {
    this.repo = repo;
  }

  @Scheduled(fixedRate = 30000)
  public void releaseExpiredSeats() {
    List<Seat> heldSeats = repo.findByState(SeatState.HELD);
    for (Seat seat : heldSeats) {
      if (seat.getHoldExpiry() != null &&
          seat.getHoldExpiry().isBefore(LocalDateTime.now())) {
        seat.setState(SeatState.AVAILABLE);
        seat.setHeldBy(null);
        seat.setHoldExpiry(null);
        repo.save(seat);
        log.info("Released expired seat {}", seat.getSeatNumber());
      }
    }
  }
}
