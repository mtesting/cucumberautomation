@Football
Feature: Football events E2E
  In order to test the ATS for football event
  As an Amelco tester
  I want to run a football event E2E and validate the market settlement

  @ps @betfair
  @F-BrE2E
  Scenario: Football event E2E with pricing
    Given "1" football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    When the event runs the incidents from "FtbIncidentsFirstHalfStartOnly.csv"
    And the event runs the incidents from "FtbIncidentsPostGoalToEnd.csv"
    Then the relevant markets should get settled

  #@Intralot
  @F-BrMirrorE2E
  Scenario: Football event E2E with pricing
    Given "1" football event set as authorized, displayed and in-play
      | Incidents  | BETRADAR_MIRROR |
      | Pricing    | BETRADAR_MIRROR |
      | excelSheet | FtbMarketData   |
    When the event runs the incidents from "FtbIncidentsFirstHalfStartOnly.csv"
    And the event runs the incidents from "FtbIncidentsPostGoalToEnd.csv"
    Then the relevant markets should get settled

  @coral
  @F-BrE2E-Openbet
  Scenario: Football event E2E with pricing
    Given "1" football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    When the event runs the incidents from "FtbIncidentsFirstHalfStartOnly.csv"
    And the event runs the incidents from "FtbIncidentsPostGoalToEnd.csv"
    Then the relevant markets should get settled
    And the relevant markets should be settled in "Openbet"

  @ps
  @F-RbE2E
  Scenario: Football event E2E with pricing
    Given "1" football event set as authorized, displayed and in-play
      | Incidents | RUNNINGBALL |
      | Pricing   | TIPEX       |
    When the event runs the incidents from "FtbIncidentsFirstHalfStartOnly.csv"
    And the event runs the incidents from "FtbIncidentsPostGoalToEnd.csv"
    Then the relevant markets should get settled

  @F-LsportsE2E
  Scenario: Football Lsports E2E with pricing
    Given a "football" event with market data "FtbMarketData" set as authorized, displayed and in-play
      | Incidents | LSPORTS |
      | Pricing   | LSPORTS |
    When the event gets score updates from "LSportsIncidentsFootball.csv"
    Then the relevant markets should get settled