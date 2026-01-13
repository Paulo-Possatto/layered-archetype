package ${package}.integration;

import ${package}.config.TestcontainersConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@Testcontainers(disabledWithoutDocker = true)
@ActiveProfiles(value = {"local", "dev"})
@Import(TestcontainersConfig.class)
public class ApplicationIT {

   @LocalServerPort
   private Integer port;
}
