@ps
@bets-cashout
Feature: Bets cash-out
  #TODO Assert cash out value returned by ATS based on te stake and pricing

  Background:
    Given the User is logged onto the customer Sportsbook

  @bets-cashout-single
  Scenario: Football Single bet cashout
    Given 1 football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    When user successfully place bets for football
      | betType   | SINGLE |
      | mktType   | MRES   |
      | winType   | WIN    |
      | priceType | LIVE   |
    When User tries to cash out the bet placed
    Then the wallets balance gets updated after cashout

  @bets-cashout-multiple
  Scenario: Football Multiple bet cashout
    Given 1 football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    When user successfully place bets for football
      | betType   | DOUBLE |
      | mktType   | MRES   |
      | winType   | WIN    |
      | priceType | LIVE   |
    When User tries to cash out the bet placed
    Then the wallets balance gets updated after cashout