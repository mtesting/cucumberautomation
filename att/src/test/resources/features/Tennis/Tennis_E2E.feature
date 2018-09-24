@Tennis
Feature: Tennis events E2E
  In order to test the ATS for tennis event
  As an Amelco tester
  I want to run a tennis event E2E and validate the market settlement

  @T-imgE2E
  Scenario: Tennis IMG E2E with resulting
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When the event runs the tennis incidents from "Tennis/TennisE2E.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisE2E.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisE2E.xlsx" sheet "set3"
    Then the relevant markets should get settled

  @coral
  @T-imgE2E-openbet
  Scenario: Tennis IMG E2E with resulting and Openbet
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When the event runs the tennis incidents from "Tennis/TennisE2E.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisE2E.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisE2E.xlsx" sheet "set3"
    Then the relevant markets should get settled
    And the relevant tennis markets should be settled in "Openbet"

  @ps
  @T-BrE2E
  Scenario: Tennis BR E2E with resulting
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When the event runs the tennis incidents from "Tennis/TennisE2E.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisE2E.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisE2E.xlsx" sheet "set3"
    Then the relevant markets should get settled

  @ppb-TennisSmokeTest
  @T-brE2E-algoBSO
  @T-brE2E21-algoBSO
  Scenario: Tennis BR AlgoMgr E2E with resulting for 3 sets 2-1 scenario
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisE2E.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisE2E.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisE2E.xlsx" sheet "set3"
    #Then the markets should be settled in "BETSYNCOUT" as specified in "Tennis/TennisE2E.xlsx" sheet "result" within 3 minutes

  @ppb-TennisSmokeTest
  @T-brE2E20-algoBSO
  Scenario: Tennis BR AlgoMgr E2E with resulting for 3 sets 2-0 scenario
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisE2E_short.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisE2E_short.xlsx" sheet "set2"
    #Then the markets should be settled in "BETSYNCOUT" as specified in "Tennis/TennisE2E_short.xlsx" sheet "result" within 3 minutes

  @betfair
  @T-imgE2E-algoBSO
  Scenario: Tennis IMG E2E with resulting ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisImgE2E.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisImgE2E.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisImgE2E.xlsx" sheet "set3"


  @betfair
  @T-imgE2E-5Set-algoBSO
  Scenario: Tennis IMG E2E with resulting ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisImgE2E-5set.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisImgE2E-5set.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisImgE2E-5set.xlsx" sheet "set3"
    When the event runs the tennis incidents from "Tennis/TennisImgE2E-5set.xlsx" sheet "set4"
    When the event runs the tennis incidents from "Tennis/TennisImgE2E-5set.xlsx" sheet "set5"


  @betfair
  @T-imgFaultPenalty-algoBSO
  Scenario: Tennis IMG E2E test with Fault and Penalty incidents included ( Semi Automated )
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    When the event runs the tennis incidents from "Tennis/TennisImgFaultPenalty.xlsx" sheet "set1"
    When the event runs the tennis incidents from "Tennis/TennisImgFaultPenalty.xlsx" sheet "set2"
    When the event runs the tennis incidents from "Tennis/TennisImgFaultPenalty.xlsx" sheet "set3"

  @MyBetAtt @Intralot
  @T-E2E-BrMr
  Scenario: Tennis BrMirror E2E with resulting
    Given a "tennis" event with market data "TennisMarketdata" set as authorized, displayed and in-play
      | Incidents | BETRADAR_MIRROR |
      | Pricing   | BETRADAR_MIRROR |
    And mirror resulting "TennisMarketdata" is sent to ATS
    Then the relevant markets should get settled