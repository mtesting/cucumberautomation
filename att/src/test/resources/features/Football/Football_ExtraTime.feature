@Football
@Football-ExtraTime
Feature: Football events Extra-Time
  In order to test the ATS system for extra time markets
  As an Amelco tester
  I want to run a football event E2E with Extra time markets and validate the results

  @ps
  @F-BrExtraTime
  Scenario: Football extra time with Betradar
    Given 1 football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    When the event runs the incidents from "FtbExtraTimeIncidents1.csv"
    And the event runs the incidents from "FtbExtraTimeIncidents2.csv"
    Then the extra markets should get "OPEN"
    When the event runs the incidents from "FtbExtraTimeIncidents3.csv"
    Then the extra markets should get "CLOSED,SUSPENDED,COMPLETED"

  @ps
  @F-RBExtraTime
  Scenario: Football extra time with Runningball
    Given 1 football event set as authorized, displayed and in-play
      | Incidents | RUNNINGBALL |
      | Pricing   | TIPEX       |
    When the event runs the incidents from "FtbExtraTimeIncidents1.csv"
    And the event runs the incidents from "FtbExtraTimeIncidents2.csv"
    Then the extra markets should get "OPEN"
    When the event runs the incidents from "FtbExtraTimeIncidents3.csv"
    Then the extra markets should get "CLOSED,SUSPENDED,COMPLETED"

  @MyBetAtt
  @F-BrMirrorExtraTime
  Scenario: Football extra time with BrMirror
    Given a "football" event with market data "FtbMarketData" set as authorized, displayed and in-play
      | Incidents | BETRADAR_MIRROR |
      | Pricing   | BETRADAR_MIRROR |
    And a LiveOdds update "LiveOdds_ExtraTime.xlsx" is sent to ATS
    And mirror resulting "FtbExtraTime" is sent to ATS
    Then the relevant markets should get settled

  @ps
  @F-BrExtraTimePenalty
  Scenario: Football extra time penalty with Betradar
    Given 1 football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    When the event runs the incidents from "FtbExtraTimeIncidents1.csv"
    And the event runs the incidents from "FtbExtraTimeIncidents2.csv"
    When the event runs the incidents from "FtbExtraTimeIncidents3_penalties.csv"
    When the event runs the incidents from "FtbExtraTimeIncidents4_penalties.csv"