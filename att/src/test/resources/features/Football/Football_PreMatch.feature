@Football
Feature: Football pre-match

  @Intralot
  @F-BrMirrorPreMatch
  Scenario: Football pre-match with resulting
    Given a "football" event with market data "FtbMarketData" set as authorized, displayed and in-play
      | Incidents | BETRADAR_MIRROR |
      | Pricing   | BETRADAR_MIRROR |
    And mirror resulting "FtbMarketData" is sent to ATS
    Then the relevant markets should get settled

  #@Intralot
  @F-BrMirrorPreMatchHandicap
  Scenario: Football pre-match Handicap Markets with resulting
    Given a "football" event with market data "FtbHandicap" set as authorized, displayed and in-play
      | Incidents | BETRADAR_MIRROR |
      | Pricing   | BETRADAR_MIRROR |
    And mirror resulting "FtbHandicap" is sent to ATS
    Then the relevant markets should get settled