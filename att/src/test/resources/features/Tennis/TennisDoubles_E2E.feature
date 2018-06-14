@Tennis
Feature: Tennis Doubles E2E tests

  @ppb-TennisSmokeTest
  @TD-brE2E20-algoBSO
  Scenario: Tennis BR AlgoMgr E2E with resulting for 3 sets 2-0 scenario
    Given a tennis doubles event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisE2E_short.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisE2E_short.xlsx" sheet "set2"
    Then the markets should be settled in "BETSYNCOUT" as specified in "Tennis/TennisE2E_short.xlsx" sheet "result" within 3 minutes


  @TD-BrUndoFP
  Scenario: Tennis BR undo incidents ( Semi Automated )
    Given a tennis doubles event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisUndoFP_6.xlsx" sheet "set1"

