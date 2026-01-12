package ${package};

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Chronos project.
 */
@SpringBootApplication
@Slf4j
public class Application {

  private static final int DASH_REPETITIONS = 100;

  /**
   * Main method for the application.
   *
   * @param args the program arguments if using CLI.
   */
  public static void main(String[] args) {

    Instant applicationStart = Instant.now();
    SpringApplication.run(Application.class, args);
    Instant applicationReady = Instant.now();

    long periodToStartInMillis = applicationReady.toEpochMilli() - applicationStart.toEpochMilli();

    log.info("-".repeat(DASH_REPETITIONS));
    log.info("Application started in {} ms", periodToStartInMillis);
    log.info("-".repeat(DASH_REPETITIONS));
  }
}
