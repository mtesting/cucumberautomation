@HR
@HR-betsSettlement
Feature: HR Bets settlement

  Background:
    Given the User is logged onto the customer Sportsbook

  @HR-betsSingleResulting
  Scenario Outline: HR Single <bet_type> bet with resulting <outcome>
    Given user is able to create "1" HR event
    And user place "SINGLE" bets on the market "WEW" for HR
      | betType   | <bet_type> |
      | outcome   | <outcome>  |
      | priceType | SP         |
    When user runs the event E2E
    Then results are updated in ATS database
    When the race results are saved in ats
    And the event settle button is clicked
    Then the wallets balance updated

  @HR-betsSingleResulting-Win @ps
    Examples:
      | bet_type | outcome |
      | WIN      | W       |
      | WIN      | L       |
      | WIN      | V       |

  @HR-betsSingleResulting-EW
    Examples:
      | bet_type | outcome |
      | EACH_WAY | W       |
      | EACH_WAY | L       |
      | EACH_WAY | V       |

  @HR-betsDoubleResulting
  Scenario Outline: HR Multiple <bet_type> bets with resulting <outcome>
    Given user is able to create "2" HR event
    And user place "DOUBLE" bets on the market "WEW" for HR
      | betType   | <bet_type> |
      | outcome   | <outcome>  |
      | priceType | SP         |
    When user runs the event E2E
    Then results are updated in ATS database
    When the race results are saved in ats
    And the event settle button is clicked
    Then the wallets balance updated

  @HR-betsDoubleResulting-Win @ps
    Examples:
      | bet_type | outcome |
      | WIN      | W       |
      | WIN      | L       |
      | WIN      | V       |

  @HR-betsDoubleResulting-EW
    Examples:
      | bet_type | outcome |
      | EACH_WAY | W       |
      | EACH_WAY | L       |
      | EACH_WAY | V       |