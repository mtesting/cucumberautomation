@HR
@SmokeTest
Feature: HR SmokeTest

  Scenario: Horse racing
    Given user is able to create "1" HR event
    When user runs the event E2E
    Then results are updated in ATS database
    And the race results are saved in ats