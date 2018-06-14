@Badminton @betfair
Feature: Badminton events E2E ( Semi Automated )
  In order to test the ATS for badminton games
  As an Amelco tester
  I want to run a badminton event E2E


  @B-ImgE2E
  @B-ImgE2E_20
  Scenario: Badminton E2E with pricing 20
    Given a badminton event set as authorized, displayed and in-play
      | Incidents | IMG      |
      | StartTime | tomorrow |
    When the badminton event runs the incidents from "incidents/Badminton/E2E.xlsx"

  @B-ImgE2E
  @B-ImgE2E_21
  Scenario: Badminton E2E with pricing 21
    Given a badminton event set as authorized, displayed and in-play
      | Incidents | IMG      |
      | StartTime | tomorrow |
    When the badminton event runs the incidents from "incidents/Badminton/E2E21.xlsx"

  @B-Disconnect
  Scenario: Badminton E2E with feed disconnection
    Given a badminton event set as authorized, displayed and in-play
      | Incidents | IMG      |
      | StartTime | tomorrow |
    When the badminton event runs the incidents from "incidents/Badminton/Disconnect.xlsx"

  @B-ScoutOffline
  Scenario: Badminton E2E with Scout Offline
    Given a badminton event set as authorized, displayed and in-play
      | Incidents | IMG      |
      | StartTime | tomorrow |
    When the badminton event runs the incidents from "incidents/Badminton/ScoutOffline.xlsx"
