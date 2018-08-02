@Tennis
Feature: Tennis events E2E
  In order to test the ATS for tennis event
  As an Amelco tester
  I want to run a tennis event E2E and validate the market settlement

  @T-imgAceDoublefaultCounter-algoBSO
    Scenario: Tennis IMG E2E with resulting ( Semi Automated )
        Given a tennis event set as authorized, displayed and in-play
           | Incidents | IMG         |
           | StartTime | currenttime |
        When user sets the tier level in trader client
        When the event runs the tennis incidents from "Tennis/TennisAceDoublefaultCounter.xlsx" sheet "set1"
