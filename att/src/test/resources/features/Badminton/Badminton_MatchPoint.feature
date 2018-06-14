@Badminton @betfair
Feature: Badminton Match Point test

  @B-MatchPoint
  Scenario: Badminton test for Match Point ( Semi Automated )
    Given a badminton event set as authorized, displayed and in-play
      | Incidents | IMG      |
      | StartTime | tomorrow |
    When the badminton event runs the incidents from "incidents/Badminton/E2EMatchPoint.xlsx"
