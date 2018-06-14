@selenium @Nats
Feature: Login Action

  Background:
    Given User is on the NATs login page

  Scenario: Failed Login with Invalid Credentials
    When User enters invalid credentials
    And User clicks on Login button
    Then Message displayed Invalid credentials entered

  Scenario: Successful Login with Valid Credentials
    When User enters valid credentials
    And User clicks on Login button
    Then Message displayed Login Successfully