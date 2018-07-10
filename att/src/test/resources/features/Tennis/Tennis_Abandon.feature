@Tennis @betfair
Feature: Tennis Abandon

  @T-BrAbandon-algoBSO
  Scenario Outline: Tennis Abandon on <abandon_period>
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | BETRADAR    |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    And the event runs the tennis for Abandon on "<abandon_period>"
    Then the relevant markets should get settled

    Examples:
      | abandon_period |
      | pre-match      |
      | serve          |
      | 1st set        |

  @T-ImgAbandon-algoBSO
  Scenario Outline: Tennis Abandon on <abandon_period>
    Given a tennis event set as authorized, displayed and in-play
      | Incidents | IMG         |
      | StartTime | currenttime |
    When user sets the tier level in trader client
    And the event runs the tennis for Abandon on "<abandon_period>"
    Then the relevant markets should get settled

    Examples:
      | abandon_period |
      | pre-match      |
      | serve          |
      | 1st set        |