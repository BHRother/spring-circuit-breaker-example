package com.bhrother.github.services

import groovy.util.logging.Slf4j
import org.springframework.retry.annotation.CircuitBreaker
import org.springframework.retry.annotation.Recover
import org.springframework.stereotype.Service

/**
 * Created by brunohenriquerother on 22/05/2017.
 */
@Service
@Slf4j
class ExternalSystemService {

  /**
   * Annotated with @CircuitBreaker setting maxAttemps = 2 for test purposes.
   * After throw an Exceptions, the next calls goes direct to fallbackForCall() method.
   *
   * @return
   */
  @CircuitBreaker(maxAttempts = 2, openTimeout = 5000l, resetTimeout = 10000l)
  int call() {
    log.info("Calling call method...")
    if (Math.random() > 0.5) {
      Thread.sleep(1000l)
      throw new RuntimeException("Error happened")
    }
    log.info("Success calling external system")
    return 1
  }

  /**
   * The recover method needs to have same return type and parameters.
   *
   * @return
   */
  @Recover
  private int fallbackForCall() {
    log.info("Fallback for call invoked")
    return 0
  }
}
