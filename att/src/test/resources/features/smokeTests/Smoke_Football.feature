@Football
@SmokeTest
Feature: Football SmokeTest

  Scenario: Football Betradar event creation
    Given a football event is sent by the feed to ATS
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    Then the event gets successfully created

  @SmokeTest-football-BrMirror
  Scenario: Football BrMirror event creation
    Given a "football" event with market data "FtbMarketData" set as authorized, displayed and in-play
      | Incidents | BETRADAR_MIRROR |
      | Pricing   | BETRADAR_MIRROR |