@Tennis @betfair
Feature: Tennis match suspension
  In order to test a Tennis event suspension
  As an Amelco tester
  I want to run incidents stopping the game play

  @T-BrMatchSusp
  Scenario: Tennis event suspended when no incidents received from Betradar ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisSuspend.xlsx" sheet "set1"
    Then the relevant markets should get settled