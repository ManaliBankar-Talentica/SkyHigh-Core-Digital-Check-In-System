
package com.skyhigh.core.repository;

import com.skyhigh.core.model.Seat;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

  Optional<Seat> findBySeatNumber(String seatNumber);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT s FROM Seat s WHERE s.seatNumber = :seatNumber")
  Optional<Seat> lockSeat(@Param("seatNumber") String seatNumber);

  List<Seat> findByState(com.skyhigh.core.model.SeatState state);
}
