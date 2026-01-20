Feature: Functionality
  Functionality example for retrieving the application status

  Scenario: Test application is up and running
    Given the application started
    When the actuator validates de app status
    Then the result should be 200
