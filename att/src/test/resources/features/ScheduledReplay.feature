Feature: Replay scenarios from dataset
  In order to re-play an event
  As an Amelco tester
  I want ATT to run an specific data-set

  @F-Brreplay
  Scenario: Football event replay E2E with pricing
    Given a "football" event set as authorized, displayed, in-play
      | DataSet   | LOCO_BR_TX |
      | Incidents | BETRADAR   |
      | Pricing   | TIPEX      |
    Then incidents from dataset will be replayed for the event

  @F-BrLoadtest
  Scenario: Load test an environment by creating n number of football events
    Given 5 football event set as authorized, displayed and in-play
      | Incidents | BETRADAR |
      | Pricing   | TIPEX    |
    Then incidents from dataset will be replayed for the event

  @T-BrReplay
  Scenario: Tennis event replay E2E with pricing
    Given a "tennis" event set as authorized, displayed, in-play
      | DataSet   | TN-BR-E2E |
      | Incidents | BETRADAR  |
      | Pricing   |           |
    Then incidents from dataset will be replayed for the event

  @T-Brreplay-Betfairalgo
  Scenario: Tennis event replay E2E with pricing
    Given a "tennis" event set as authorized, displayed, in-play
      | DataSet   | BR_TEN_3 |
      | Incidents | BETRADAR |
      | Pricing   |          |
    Then incidents from dataset will be replayed for the event

  @T-BrreplayRandomDataset
  Scenario: Tennis event replay E2E with pricing
    Given a "tennis" event set as authorized, displayed, in-play
      | Incidents | BETRADAR |
      | Pricing   |          |
    Then incidents from dataset will be replayed for the event

  @T-ImgReplay
  Scenario: Tennis event replay E2E with pricing
    Given a "tennis" event set as authorized, displayed, in-play
      | DataSet   | IMG-E2E |
      | Incidents | IMG     |
      | Pricing   |         |
    Then incidents from dataset will be replayed for the event

#  @tennis-loadtest-Br
#  Scenario: Tennis event replay E2E with pricing
#    Given there are 5 tennis event set as authorized, displayed, in-play
#      | Incidents | BETRADAR    |
#      | StartTime | currenttime |
#    Then incidents from dataset will be replayed for the event