@selenium @MyBet @BoB @Gana @RedZone @Baba
@Login
Feature: Login Action

  Background:
    Given User is on the customer web

  Scenario: Failed Login with Invalid Credentials
    When User enters invalid credentials
    And User clicks on Login button
    Then Message displayed Invalid credentials entered

  Scenario: Successful Login with Valid Credentials
    When User enters valid credentials
    And User clicks on Login button
    Then Message displayed Login Successfully

#  Scenario Outline: Mobile User Successful Login with Valid Credentials
#    Given a "<os>" user using "<browser>"
#    When User is on the "MyBet" web
#    And User clicks on Login
#    And User enters UserName "frantzes" and Password "Test1234"
#    And User clicks on Login
#    Then Message displayed Login Successfully

#    Examples:
#      | browser       | os      |
#      | chromeMobile  | windows |
#      | chromeMobile  | linux   |