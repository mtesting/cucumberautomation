@Football
@Football-disconnection
Feature: Football feed disconnection
  In order to test the ATS for football event
  As an Amelco tester
  I want to run a football event E2E and validate the market settlement

  @betfair
  @F-BrDisconnection
  Scenario: Football E2E with feed disconnection
    Given 1 football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    When the event runs the incidents from "FtbIncidentsFirstHalfStartOnly.csv"
    And there is a feed disconnection
    And the event runs the incidents from "FtbIncidentsPostGoalToEnd.csv"
    Then the relevant markets should get settled

  @betfair
  @F-BrPartialDisconnection
  Scenario: Football E2E with partial disconnection
    Given 1 football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    When the event runs the incidents from "FtbIncidentsFirstHalfStartOnly.csv"
    And there is a partial feed disconnection
    And the event runs the incidents from "FtbIncidentsPostGoalToEnd.csv"
    Then the relevant markets should get settled

  @F-BrStopAliveReply
  Scenario: Football E2E with heartbeat stop
    Given 1 football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    When the event runs the incidents from "FtbIncidentsFirstHalfStartOnly.csv"
    And the feed heartbeat alive reply stops
    And the event runs the incidents from "FtbIncidentsPostGoalToEnd.csv"
    Then the relevant markets should get settled