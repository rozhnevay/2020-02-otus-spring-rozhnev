package ru.otus.spring.healthcheck;

import java.util.Random;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class Mod2RandomHealthIndicator implements HealthIndicator {
  private final Random random = new Random();

  @Override
  public Health health() {
    boolean serverIsDown = Math.floorMod(random.nextInt(), 2) == 0;
    if (serverIsDown) {
      return Health.down().status(Status.DOWN).build();
    } else {
      return Health.up().status(Status.UP).build();
    }
  }
}
