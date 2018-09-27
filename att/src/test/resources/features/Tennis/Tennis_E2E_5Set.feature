@Tennis @betfair
Feature: Tennis events E2E 5 Set
  In order to test the ATS for tennis event
  As an Amelco tester
  I want to run a tennis event E2E and validate the market settlement

  @T-brE2E-5Set-algoBSO
  Scenario: Tennis BR AlgoMgr E2E with resulting for 5 sets 3-2 scenario
    Given a tennis event set as authorized, displayed and in-play for BR Set
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisE2E-5Set.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisE2E-5Set.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisE2E-5Set.xlsx" sheet "set3"
    When the event runs the tennis incidents from "Tennis/TennisE2E-5Set.xlsx" sheet "set4"
    When the event runs the tennis incidents from "Tennis/TennisE2E-5Set.xlsx" sheet "set5"

  @T-brE2E5Set-algoBSO
  Scenario: Tennis BR AlgoMgr E2E with resulting for 5 sets 3-1 scenario
    Given a tennis event set as authorized, displayed and in-play for BR Set
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisE2E-new-5Set.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisE2E-new-5Set.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisE2E-new-5Set.xlsx" sheet "set3"
    When the event runs the tennis incidents from "Tennis/TennisE2E-new-5Set.xlsx" sheet "set4"



  @T-brE2E5Set30-algoBSO
  Scenario: Tennis BR AlgoMgr E2E with resulting for 5 sets 3-0 scenario
    Given a tennis event set as authorized, displayed and in-play for BR Set
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisE2E-5Set-short.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisE2E-5Set-short.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisE2E-5Set-short.xlsx" sheet "set3"



  @T-imgE2E-5Set-algoBSO
  Scenario: Tennis IMG E2E with resulting for 5 set 3-2 scenario( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play for IMG
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisImgE2E-5set.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisImgE2E-5set.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisImgE2E-5set.xlsx" sheet "set3"
    When the event runs the tennis incidents from "Tennis/TennisImgE2E-5set.xlsx" sheet "set4"
    When the event runs the tennis incidents from "Tennis/TennisImgE2E-5set.xlsx" sheet "set5"



  @T-imgE2E5set-algoBSO
  Scenario: Tennis IMG E2E with resulting for 5 set 3-0 scenario( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play for IMG
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisImgE2E_new_5set.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisImgE2E_new_5set.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisImgE2E_new_5set.xlsx" sheet "set3"

