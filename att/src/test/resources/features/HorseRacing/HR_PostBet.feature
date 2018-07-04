@HR
@HRpostbet
Feature: Post bet placement
  HR EW terms changing post bet placement

  Background:
    Given the User is logged onto the customer Sportsbook
    And user is able to create 1 HR event

  @HRpostbetSP
  Scenario Outline: Selection odds change in SP
    And user place "SINGLE" bets on the market "WEW" for HR
      | betType   | <bet_type> |
      | outcome   | <outcome>  |
      | priceType | SP         |
    And selections odds get higher
    When user runs the event E2E
    Then results are updated in ATS database
    When the race results are saved in ats
    And the event settle button is clicked
    Then the wallets balance updated

    Examples:
      | bet_type | outcome |
      | WIN      | W       |
      | EACH_WAY | W       |
      | WIN      | L       |
      | EACH_WAY | L       |
      | WIN      | V       |
      | EACH_WAY | V       |

  @HRpostbetLP
  Scenario Outline: Selection odds change in LP
    And the LP flag is set to true
    And user place "SINGLE" bets on the market "WEW" for HR
      | betType   | <bet_type> |
      | outcome   | <outcome>  |
      | priceType | LP         |
    And selections odds get higher
    When user runs the event E2E
    Then results are updated in ATS database
    When the race results are saved in ats
    And the event settle button is clicked
    Then the wallets balance updated

    Examples:
      | bet_type | outcome |
      | WIN      | W       |
      | EACH_WAY | W       |
      | WIN      | L       |
      | EACH_WAY | L       |
      | WIN      | V       |
      | EACH_WAY | V       |

  @HRpostbetPlaces
  Scenario Outline: Number of places change
    And user place "SINGLE" bets on the market "WEW" for HR
      | betType   | <bet_type> |
      | outcome   | <outcome>  |
      | priceType | SP         |
    And race number of places gets lower
    When user runs the event E2E
    Then results are updated in ATS database
    When the race results are saved in ats
    And the event settle button is clicked
    Then the wallets balance updated

    Examples:
      | bet_type | outcome |
      | WIN      | W       |
      | EACH_WAY | W       |
      | WIN      | L       |
      | EACH_WAY | L       |
      | WIN      | V       |
      | EACH_WAY | V       |

  @HRpostbetPlacesOdds
  Scenario Outline: Selection odds and number of places change
    And user place "SINGLE" bets on the market "WEW" for HR
      | betType   | <bet_type> |
      | outcome   | <outcome>  |
      | priceType | SP         |
    And selections odds get higher
    And race number of places gets lower
    When user runs the event E2E
    Then results are updated in ATS database
    When the race results are saved in ats
    And the event settle button is clicked
    Then the wallets balance updated

    Examples:
      | bet_type | outcome |
      | WIN      | W       |
      | EACH_WAY | W       |
      | WIN      | L       |
      | EACH_WAY | L       |
      | WIN      | V       |
      | EACH_WAY | V       |