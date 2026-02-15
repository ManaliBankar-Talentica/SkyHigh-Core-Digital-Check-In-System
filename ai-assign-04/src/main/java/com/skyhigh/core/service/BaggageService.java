
package com.skyhigh.core.service;

import org.springframework.stereotype.Service;

@Service
public class BaggageService {

  public boolean requiresPayment(double weight) {
    return weight > 25.0;
  }
}
