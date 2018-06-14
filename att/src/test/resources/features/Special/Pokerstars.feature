Feature: Testing sprint tickets

  @RT-5726
  Scenario: Football RB kick off and BR unbooked
    Given "1" football event set as authorized, displayed and in-play
      | Incidents | RUNNINGBALL |
      | Pricing   | TIPEX       |
    And the event runs the incidents from "FtbIncidentsFirstHalfStartOnly.csv"
    And a LiveOdds update "LiveOdds_Unbook.xlsx" is sent to ATS

  @RT-5810
  Scenario: Football BR event time change
    Given "1" football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    And a LiveOdds update "LiveOdds_TimeChange.xlsx" is sent to ATS


  @RT-6419
  Scenario: Football RB game cancelled due weather conditions
    Given "1" football event set as authorized, displayed and in-play
      | Incidents | RUNNINGBALL |
      | Pricing   | TIPEX       |
    When the event runs the incidents from "FtbIncidentsFirstHalfStartOnly.csv"
    And the event runs the incidents from "FtbIncidentsCoverageCancelled.csv"