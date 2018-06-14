@Badminton @betfair
@Badminton-Abandon
Feature: Badminton abandon ( Semi Automated )
  In order to test the ATS for abandon scenarios
  As an Amelco tester
  I want to run a badminton event with Abandon incident

  @B-AbandonWalkOver
  Scenario: Badminton with Abandon WalkOver
    Given a badminton event set as authorized, displayed and in-play
      | Incidents | IMG      |
      | StartTime | tomorrow |
    When the badminton event runs the incidents from "incidents/Badminton/Abandon_WalkOver.xlsx"

  @B-AbandonRetirement
  Scenario: Badminton event with Abandon Retirement
    Given a badminton event set as authorized, displayed and in-play
      | Incidents | IMG      |
      | StartTime | tomorrow |
    When the badminton event runs the incidents from "incidents/Badminton/Abandon_Retirement.xlsx"

  @B-AbandonDisqualification
  Scenario: Badminton event with Abandon Disqualification
    Given a badminton event set as authorized, displayed and in-play
      | Incidents | IMG      |
      | StartTime | tomorrow |
    When the badminton event runs the incidents from "incidents/Badminton/Abandon_Disqualification.xlsx"

  @B-AbandonWalkOverSet2
  Scenario: Badminton with Abandon WalkOver
    Given a badminton event set as authorized, displayed and in-play
      | Incidents | IMG      |
      | StartTime | tomorrow |
    When the badminton event runs the incidents from "incidents/Badminton/Abandon_WalkOver_set2.xlsx"

  @B-AbandonRetirementSet2
  Scenario: Badminton event with Abandon Retirement
    Given a badminton event set as authorized, displayed and in-play
      | Incidents | IMG      |
      | StartTime | tomorrow |
    When the badminton event runs the incidents from "incidents/Badminton/Abandon_Retirement_set2.xlsx"

  @B-AbandonDisqualificationSet2
  Scenario: Badminton event with Abandon Disqualification
    Given a badminton event set as authorized, displayed and in-play
      | Incidents | IMG      |
      | StartTime | tomorrow |
    When the badminton event runs the incidents from "incidents/Badminton/Abandon_Disqualification_set2.xlsx"
