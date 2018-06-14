@Tennis
Feature: Tennis server update
  In order to Betradar Service Update incident update the server correctly
  As an Amelco tester
  I want Betradar to send a Server and an update Server

  @betfair
  @T-ServerUpdate-algoBSO
  Scenario: To test Betradar Service Update incident update the server correctly (Semi Automated)
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisServerUpdate.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisServerUpdate.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisServerUpdate.xlsx" sheet "set3"
