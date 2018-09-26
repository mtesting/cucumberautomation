@Tennis
Feature: Tennis Abandon

  @T-ImgAbandonWalkover-algoBSO
  Scenario Outline: Tennis Abandon-Walkover on <abandon_period>
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    And the event runs the tennis for Abandon on "<abandon_period>"

    Examples:
      | abandon_period |
      | pre-match      |
      #| serve          |
      #| 1st set        |
  #Walkover is always at prematch


  @T-imgRetirement3setIllnessinset1-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Illness ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset1.xlsx" sheet "set1"
# For running this test need to select Abandon.Name as Illness from IncidentsHelper'java


  @T-imgRetirement3setIllnessinset2-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Illness ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set2"
#When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set3"
# For running this test need to select Abandon.Name as Illness from IncidentsHelper'java


  @T-imgRetirement3setIllnessinset3-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-illness ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set3"
# For running this test need to select Abandon.Name as Illness from IncidentsHelper'java


  @T-imgRetirement3setInjuryinset1-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Injury ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset1.xlsx" sheet "set1"
# For running this test need to select Abandon.Name as Injury from IncidentsHelper'java


  @T-imgRetirement3setInjuryinset2-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Injury ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set2"
# For running this test need to select Abandon.Name as Injury from IncidentsHelper'java
  

  @T-imgRetirement3setInjuryinset3-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-injury ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set3"
# For running this test need to select Abandon.Name as Injury from IncidentsHelper'java

  @T-imgRetirement3setUnspecifiedinset1-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Unspecified ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset1.xlsx" sheet "set1"
# For running this test need to select Abandon.Name as Unspecified from IncidentsHelper'java

  @T-imgRetirement3setUnspecifiedinset2-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Unspecified ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set2"
# For running this test need to select Abandon.Name as Unspecified from IncidentsHelper'java

  @T-imgRetirement3setUnspecifiedinset3-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Unspecified ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set3"
# For running this test need to select Abandon.Name as Unspecified from IncidentsHelper'java

  @T-imgRetirement3setUnknowninset1-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Unknown ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset1.xlsx" sheet "set1"
# For running this test need to select Abandon.Name as Unknown from IncidentsHelper'java

  @T-imgRetirement3setUnknowninset2-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Unknown ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set2"
# For running this test need to select Abandon.Name as Unknown from IncidentsHelper'java

  @T-imgRetirement3setUnknowninset3-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Unknown ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set3"
# For running this test need to select Abandon.Name as Unknown from IncidentsHelper'java


  @T-imgRetirement3setOtherinset1-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Other( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset1.xlsx" sheet "set1"
# For running this test need to select Abandon.Name as Other from IncidentsHelper'java

  @T-imgRetirement3setOtherinset2-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Injury ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set2"
# For running this test need to select Abandon.Name as Other from IncidentsHelper'java


  @T-imgRetirement3setOtherinset3-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Other ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set3"
# For running this test need to select Abandon.Name as Other from IncidentsHelper'java


  @T-imgRetirement3setBallAbuseinset1-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-BallAbuse( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset1.xlsx" sheet "set1"
# For running this test need to select Abandon.Name as Ballabuse from IncidentsHelper'java

  @T-imgRetirement3setOtherinset2-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Injury ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset2.xlsx" sheet "set2"
# For running this test need to select Abandon.Name as Other from IncidentsHelper'java

  @T-imgRetirement3setOtherinset3-algoBSO
  Scenario: Tennis IMG E2E 3 set match test with Retirement-Other ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/Tennis3set2-1-retirementinset3.xlsx" sheet "set3"
# For running this test need to select Abandon.Name as Other from IncidentsHelper'java
