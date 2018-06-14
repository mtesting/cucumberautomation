@Tennis @betfair
Feature: Tennis Abandon

  @T-imgAbandon-algoBSO
  Scenario: Tennis Abandon
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisAbandon.xlsx" sheet "set1"
    Then the relevant markets should get settled

  @ps
  @T-BrAbandon-algoBSO
  Scenario: Tennis Abandon on 1st set
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisAbandon.xlsx" sheet "set1"
    Then the markets should be settled in "BETSYNCOUT" as specified in "Tennis/TennisAbandon.xlsx" sheet "result" within 3 minutes

  @ps
  @T-BrAbandonSet2-algoBSO
  Scenario: Tennis Abandon on 2nd set
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisAbandonSet2.xlsx" sheet "set1"
    Then the relevant markets should get settled