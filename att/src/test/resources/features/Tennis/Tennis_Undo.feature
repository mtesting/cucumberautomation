@Tennis
Feature: Tennis undo incident

  @ps @betfair
  @T-BrUndo
  Scenario: Tennis BR undo incidents ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When the event runs the tennis incidents from "Tennis/TennisUndo.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisUndo.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisUndo.xlsx" sheet "set3"

  @ps @betfair
  @T-imgUndo
  Scenario: Tennis Img undo incidents ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG    |
      | StartTime | currenttime |
    When the event runs the tennis incidents from "Tennis/TennisImgUndo.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisImgUndo.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisImgUndo.xlsx" sheet "set3"

  @T-BrUndoFP
  Scenario: Tennis BR undo incidents ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisUndoFP.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisUndoFP.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisUndoFP.xlsx" sheet "set3"

  @T-BrUndoFPinGame
  Scenario: Tennis BR undo incident and same team score, undo incident and other team score, undo incident at game winning point( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisUndoFP_2.xlsx" sheet "set1"

  @T-BrUndoFPinSetAndMatch
  Scenario: Tennis BR undo the winning point incident of a set and the whole match ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisUndoFP_3.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisUndoFP_3.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisUndoFP_3.xlsx" sheet "set3"

  @T-BrUndoFPwithWait
  Scenario: Tennis BR undo incidents with wait time inbetween( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisUndoFP_5.xlsx" sheet "set1"
    And wait 1 minute
    When the event runs the tennis incidents from "Tennis/TennisUndoFP_5.xlsx" sheet "set2"
    And wait 1 minute
    When the event runs the tennis incidents from "Tennis/TennisUndoFP_5.xlsx" sheet "set3"


  @T-BrMultipleUndoFP
  Scenario: Tennis BR undo incidents ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisUndoFP_4.xlsx" sheet "set1"

