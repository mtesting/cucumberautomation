@HR
Feature: HR Rule4

  Background:
    Given the User is logged onto the customer Sportsbook

  @Test
  Scenario Outline: Rule 4 with SINGLE bets <bet_type> bet with resulting <outcome>
    Given user is able to create "1" HR event
    And user place "SINGLE" bets on the market "WEW" for HR
      | betType   | <bet_type> |
      | outcome   | <outcome>  |
      | priceType | SP         |
    And there is a deduction of ".80" applied on that selection
    When user runs the event E2E
    Then results are updated in ATS database
    When the race results are saved in ats
    And the event settle button is clicked
    Then the payout would be calculated from the stake, odds and deduction

    Examples:
      | bet_type | outcome |
      | WIN      | W       |
      #| EACH_WAY | W       |
      #| WIN      |    L    |
      #| EACH_WAY |    L    |
      #| WIN      |    V    |
      #| EACH_WAY |    V    |