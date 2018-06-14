@selenium @MyBet
@BetsSettlement
Feature: Bets settlement

  Background:
    Given the User is logged onto the customer Sportsbook

  Scenario Outline: Settle a <bet_type> bet as <bet_outcome>
    And user has placed a "<bet_type>" bet
    When the user settles the bet as "<bet_outcome>"
    Then the user cash balance gets updated properly

    Examples:
      | bet_type | bet_outcome |
      | Single   | Win         |
      | Single   | Lose        |
      | Single   | Void - Feed |