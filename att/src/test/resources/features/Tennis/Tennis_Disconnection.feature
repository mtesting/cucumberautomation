@Tennis @betfair
Feature: Tennis feed disconnection
  In order to test a tennis feed disconnection
  As an Amelco tester
  I want to run a tennis event and re-connect the feed

  @T-BrReconnectAfterMissingIncidents
  Scenario: Tennis BR reconnect after missing incidents
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisDisconnect2.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisDisconnect2.xlsx" sheet "set1a"
    And wait 3 minute
    And the event runs the tennis incidents from "Tennis/TennisDisconnect1.xlsx" sheet "set2a"
    Then the relevant markets should get settled

  @T-BrReconnectAfter3Min
  Scenario: Tennis BR reconnect after 3min
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisDisconnect1.xlsx" sheet "set1"
    And wait 3 minute
    And the event runs the tennis incidents from "Tennis/TennisDisconnect1.xlsx" sheet "set2"
    Then the relevant markets should get settled

  @T-BrLiveScoutAlive
  Scenario: Tennis LiveScout alive msg stop
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisE2E_short.xlsx" sheet "set1"
    And the tennis feed heartbeat alive reply stops
    And the event runs the tennis incidents from "Tennis/TennisE2E_short.xlsx" sheet "set2"
    Then the relevant markets should get settled

  ##this is for scout abandon
  @T-BrMatchCoverageCancelDueToConnectionProblems
  Scenario: Tennis coverage cancel due to connection problems
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime  | currentTime|
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisBRDisconnection-ScoutAbandon.xlsx" sheet "set1"