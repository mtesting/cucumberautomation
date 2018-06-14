@Tennis @betfair
@Tennis-delays
Feature: Tennis Delays (Semi Automated)

  @T-BrChallenge-algoBSO
  Scenario: Tennis Challenge
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisChallenge.xlsx" sheet "set1"


  @T-ImgChallenge-algoBSO
  Scenario: Tennis Challenge
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisChallenge.xlsx" sheet "set1"


  @T-BrHeatDelay-algoBSO
  Scenario: Tennis HeatDelay
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisHeatDelay.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisHeatDelay.xlsx" sheet "set2"
    And wait 3 minute
    When the event runs the tennis incidents from "Tennis/TennisHeatDelay.xlsx" sheet "set13"


  @T-ImgHeatDelay-algoBSO
  Scenario: Tennis HeatDelay
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisHeatDelay.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisHeatDelay.xlsx" sheet "set2"
    And wait 3 minute
    When the event runs the tennis incidents from "Tennis/TennisHeatDelay.xlsx" sheet "set13"


  @T-BrMedTimeOut-algoBSO
  Scenario: Tennis Medical time out delay
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisMedTO.xlsx" sheet "set1"


  @T-ImgMedTimeOut-algoBSO
  Scenario: Tennis Medical time out delay
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisMedTO.xlsx" sheet "set1"


  @T-BrOCCouch-algoBSO
  Scenario: Tennis on court coach
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisOCCouch.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisOCCouch.xlsx" sheet "set2"
    And wait 3 minute
    When the event runs the tennis incidents from "Tennis/TennisOCCouch.xlsx" sheet "set3"


  @T-imgOCCouch-algoBSO
  Scenario: Tennis On court coach
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When the event runs the tennis incidents from "Tennis/TennisOCCouch.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisOCCouch.xlsx" sheet "set2"
    And wait 3 minute
    When the event runs the tennis incidents from "Tennis/TennisOCCouch.xlsx" sheet "set3"


  @T-BrRainDelay-algoBSO
  Scenario: Tennis RainDelay
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisRainDelay.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisRainDelay.xlsx" sheet "set2"
    And wait 3 minute
    When the event runs the tennis incidents from "Tennis/TennisRainDelay.xlsx" sheet "set3"

  @T-BrWeatherDelay-algoBSO
  Scenario: TennisWeatherDelay
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisWeatherDelay.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisWeatherDelay.xlsx" sheet "set2"
    And wait 3 minute
    When the event runs the tennis incidents from "Tennis/TennisWeatherDelay.xlsx" sheet "set3"

  @T-imgRainDelay-algoBSO
  Scenario: Tennis RainDelay
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisRainDelay.xlsx" sheet "set1"


  @T-BrToiletBreak-algoBSO
  Scenario: Tennis ToiletBreak
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisToiletBreak.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisToiletBreak.xlsx" sheet "set2"
    And wait 3 minute
    When the event runs the tennis incidents from "Tennis/TennisToiletBreak.xlsx" sheet "set3"


  @T-imgToiletBreak-algoBSO
  Scenario: Tennis ToiletBreak
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisToiletBreak.xlsx" sheet "set1"
