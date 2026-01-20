package ${package}.cucumber.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

public class FunctionalityStepDefinitions {

  @Autowired
  private TestRestTemplate restTemplate;

  private ResponseEntity<String> response;

  @Given("the application started")
  public void app_start(){
  }

  @When("the actuator validates de app status")
  public void validate_status(){
    response = restTemplate.getForEntity(
        "/actuator/health",
        String.class
    );
  }

  @Then("the result should be {int}")
  public void validate_response_code(int code){
    Assertions.assertEquals(code, response.getStatusCode().value());
  }
}
