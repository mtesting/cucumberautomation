@Tennis
@SmokeTest
Feature: Tennis SmokeTest

  @SmokeTest-Tennis
  Scenario: Tennis BrMirror event creation
    Given a "tennis" event with market data "TennisMarketdata" set as authorized, displayed and in-play
      | Incidents | BETRADAR_MIRROR |
      | Pricing   | BETRADAR_MIRROR |