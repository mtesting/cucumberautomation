@selenium @MyBet @BoB @Baba @Betstars
@Bets
Feature: Bets placement

  Background:
    Given the User is logged onto the customer Sportsbook

  Scenario: Place Single bets
    And User clicks on "1" selections
    When User enters a "Single" bet amount "1"
    And User clicks on Place Bet
    Then A Bet Placed message is displayed

  Scenario: Place Multiple bets
    And User clicks on "2" selections
    When User enters a "Multiple" bet amount "1"
    And User clicks on Place Bet
    Then A Bet Placed message is displayed

  Scenario: Place System bets
    And User clicks on "3" selections
    When User enters a "System" bet amount "1"
    And User clicks on Place Bet
    Then A Bet Placed message is displayed