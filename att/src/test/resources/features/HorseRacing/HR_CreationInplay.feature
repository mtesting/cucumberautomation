@HR @betfair
Feature: Horse Racing

  Scenario: Horse races - test creation, inplay and results
    Given user is able to create 1 HR event
    When user launch HR event
    And send PA update for Off
    And send PA update for finish
    And send PA update for Result
    Then results are updated in ATS database