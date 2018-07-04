@Football
Feature: Football Cancellations

  @F-BrCoverageCancel
  Scenario: Football E2E with coverage cancel
    Given 1 football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    When the event runs the incidents from "FtbIncidentsFirstHalfCoverageCancel.csv"
    And wait 3 minute
    And the event runs the incidents from "FtbIncidentsPostCoverageCancel.csv"
    Then the relevant markets should get settled

  @ps
  @F-RbGoalCancel
  Scenario: Football goal cancelled
    Given 1 football event set as authorized, displayed and in-play
      | Incidents | RUNNINGBALL |
      | Pricing   | TIPEX       |
    When the event runs the incidents from "FtbGoalCancel.csv"
    Then the Correct Score market gets resulted successfully
      | 0-1 | WIN  |
      | 0-2 | LOSE |
      | 0-0 | LOSE |