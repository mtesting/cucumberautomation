@Football
Feature: Football Abelson markets

  @F-Abelson
  Scenario: Football event E2E with pricing
    Given 1 football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    And Abelson Goal Scorer markets added
    #When the event runs the incidents from "FtbIncidentsFirstHalfStartOnly.csv"
    #And the event runs the incidents from "FtbIncidentsPostGoalToEnd.csv"
    #Then the relevant markets should get settled