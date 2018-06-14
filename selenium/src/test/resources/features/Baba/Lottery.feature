@selenium @Baba
@Lottery
Feature: Lottery

  Scenario Outline: Place a <bet_type> bet on a lottery draw
    Given the User is logged onto the customer Sportsbook
    And user choose a lottery draw
    When user adds <draw_numbers> draw numbers
    And user selects a <bet_type> bet
    Then the bet must be placed successfully

    Examples:
      | draw_numbers | bet_type |
      | 1            | NAP_1    |
      | 2            | NAP_2    |
      | 3            | NAP_3    |
      | 4            | NAP_4    |
      | 5            | NAP_5    |
      | 3            | PERM_2   |
      | 4            | PERM_3   |
      | 5            | PERM_4   |
      | 6            | PERM_5   |