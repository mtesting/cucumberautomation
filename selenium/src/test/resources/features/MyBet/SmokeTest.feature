@selenium
@MyBetSmokeTest

Feature: Smoke Test
  As a tester
  I want to be able to open the clients from different browsers
  So that I can assert the product compatibility and performance

  Scenario Outline: Linux User can load MyBet website properly
    Given a "Linux" user
    When they access the homepage using "<browser>"
    Then the homepage should load successfully

    Examples:
      | browser      |
      | firefox      |
      | chrome       |
      | chromeMobile |