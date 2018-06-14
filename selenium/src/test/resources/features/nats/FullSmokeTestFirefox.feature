@nats-smoketest
Feature: Nats full smoke-test with Firefox

  Background:
    Given the a "firefox" user is logged onto the Nats client

  @nats-smoketest-firefox
  Scenario Outline: Page <page> loads successfully with Firefox
    When User clicks on "<page>"
    Then the page gets loaded successfully

    Examples:
      | page                          |
      | Global Config                 |
      | Sportsbook Settings           |
      | Segments                      |
      | Global Event Limits           |
      | Match Format                  |
      | Node Settings                 |
      | Feed Management               |
      | Mapping                       |
      | Outrights Types               |
      | Odds Ladder                   |
      | Risk Manager                  |
      | Bet Ticker                    |
      | Overask                       |
      | Punter Limits                 |
      | Same Combination Bets         |
      | Alerts Ticker                 |
      | Alerts Config                 |
      | Event Codes Configuration     |
      | Event Codes Assignment        |
      | Selection Codes Configuration |
      | Competition Priorities        |
      | Campaign Manager              |
      | Campaign Template             |
      | Bulk Awards                   |
      | Account Management            |
      | Role Permissions              |
      | Event Logs                    |
      | User Logs                     |