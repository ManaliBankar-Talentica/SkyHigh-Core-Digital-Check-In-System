
package com.skyhigh.core.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="seats")
public class Seat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String seatNumber;

  @Enumerated(EnumType.STRING)
  private SeatState state = SeatState.AVAILABLE;

  private String heldBy;

  private LocalDateTime holdExpiry;

  @Version
  private Long version;

  public Long getId() { return id; }
  public String getSeatNumber() { return seatNumber; }
  public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }
  public SeatState getState() { return state; }
  public void setState(SeatState state) { this.state = state; }
  public String getHeldBy() { return heldBy; }
  public void setHeldBy(String heldBy) { this.heldBy = heldBy; }
  public LocalDateTime getHoldExpiry() { return holdExpiry; }
  public void setHoldExpiry(LocalDateTime holdExpiry) { this.holdExpiry = holdExpiry; }
}
