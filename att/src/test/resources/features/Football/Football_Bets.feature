@Football
Feature: Football Bets Placement

  Background:
    Given the User is logged onto the customer Sportsbook

  @ps
  @F-BrTxBets
  Scenario: Football single bet placement
    When "1" football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    Then user successfully place bets
      | betType   | SINGLE |
      | mktType   | MRES   |

  @ps
  @F-BrTxBets
  Scenario: Football double bet placement
    When "2" football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    Then user successfully place bets
      | betType   | DOUBLE |
      | mktType   | MRES   |

  @Intralot
  @F-BrMirrorBetsSingle @F-BrMirrorBets
  Scenario: Football Single bet placement
    When a "football" event with market data "FtbMarketData" set as authorized, displayed and in-play
      | Incidents | BETRADAR_MIRROR |
      | Pricing   | BETRADAR_MIRROR |
    Then user successfully place bets
      | betType   | SINGLE        |
      | mktType   | SOCCER:FT:AXB |

  @Intralot
  @F-BrMirrorBetsDouble @F-BrMirrorBets
  Scenario: Football Double bet placement
    When "2" football event set as authorized, displayed and in-play
      | Incidents  | BETRADAR_MIRROR |
      | Pricing    | BETRADAR_MIRROR |
      | excelSheet | FtbMarketData   |
    Then user successfully place bets
      | betType   | DOUBLE        |
      | mktType   | SOCCER:FT:AXB |

  @Intralot
  @F-BrMirrorBetsTrixie @F-BrMirrorBets
  Scenario: Football Trixie bet placement
    When "3" football event set as authorized, displayed and in-play
      | Incidents  | BETRADAR_MIRROR |
      | Pricing    | BETRADAR_MIRROR |
      | excelSheet | FtbMarketData   |
    Then user successfully place bets
      | betType   | TRIXIE        |
      | mktType   | SOCCER:FT:AXB |

  @F-MyBetAtt
  Scenario: Football single bet placement
    When a "football" event with market data "FtbMarketData" set as authorized, displayed and in-play
      | Incidents | BETRADAR_MIRROR |
      | Pricing   | BETRADAR_MIRROR |
    Then user successfully place bets
      | betType   | SINGLE |
      | mktType   | MRES   |