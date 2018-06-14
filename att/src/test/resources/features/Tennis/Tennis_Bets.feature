@Tennis
Feature: Tennis Bets Placement

  Background:
    Given the User is logged onto the customer Sportsbook

  @Intralot
  @T-Bets-BrMr
  Scenario: Tennis single bet placement
    Given a "tennis" event with market data "TennisMarketdata" set as authorized, displayed and in-play
      | Incidents | BETRADAR_MIRROR |
      | Pricing   | BETRADAR_MIRROR |
    Then user successfully place bets
      | betType   | SINGLE        |
      | mktType   | TENNIS:FT:ML  |
      | winType   | WIN           |
      | priceType | LIVE          |