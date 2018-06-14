@Tennis
Feature: Tennis event data update
  In order to test the ATS for tennis event
  As an Amelco tester
  I want to run a tennis event E2E and validate the market settlement

  @betfair
  @T-brEventTimeUpdate-algoBSO
  Scenario: To test inplay flag and event time is updated after the first point and not after server set (Semi Automated)
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisDelayedStart.xlsx" sheet "set1"
    And wait 1 minute
    When the event runs the tennis incidents from "Tennis/TennisDelayedStart.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisDelayedStart.xlsx" sheet "set3"
