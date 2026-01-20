package ${package}.smoke;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;
import ${package}.config.TestcontainersConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "qa")
@Testcontainers(disabledWithoutDocker = true)
@Import(TestcontainersConfig.class)
class SmokeDevTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void applicationStartsAndHealthEndpointWorks() {
    ResponseEntity<String> response = restTemplate.getForEntity(
        "/actuator/health",
        String.class
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).contains("UP");
  }

  // TODO: Add simple tests for created endpoints.
}
