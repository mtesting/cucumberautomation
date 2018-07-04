@Football
Feature: Football possible incidents
  In order to test the ATS for football market suspensions
  As an Amelco tester
  I want to run a football event with possible incidents

  @F-RbTxPossible
  Scenario: Football possible RedCard and possible Penalty
    Given 1 football event set as authorized, displayed and in-play
      | Incidents | RUNNINGBALL |
      | Pricing   | TIPEX       |
    When the event runs the incidents from "FtbPossible.csv"
    #TODO add an step testing the market suspension/re-activation
    Then the relevant markets should get settled