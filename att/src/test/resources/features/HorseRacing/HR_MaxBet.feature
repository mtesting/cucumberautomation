@HR @ps
@HRmaxBet
Feature: Bets Max allowed stake

  Background:
    Given the User is logged onto the customer Sportsbook

  @HRmaxBet-SP
  Scenario: HR SP max bet stake
    Given user is able to create "1" HR event
    And user selects a random selection for HR
      | betType   | SINGLE |
      | mktType   | WEW    |
      | winType   | WIN    |
      | priceType | SP     |
    When user clicks on the max button
    Then the stake must be correctly calculated

  @HRmaxBet-LP
  Scenario: HR LP max bet stake
    Given user is able to create "1" HR event
    And the LP flag is set to true
    And user selects a random selection for HR
      | betType   | SINGLE |
      | mktType   | WEW    |
      | winType   | WIN    |
      | priceType | LP     |
    When user clicks on the max button
    Then the stake must be correctly calculated