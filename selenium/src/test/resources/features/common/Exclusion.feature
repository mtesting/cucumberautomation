@selenium @MyBet @Baba
@Exclusion
Feature: Exclusion

  Scenario: Set exclusion limits
    Given the User is logged onto the customer Sportsbook
    And user goes to the limits/exclusion section
    When user enters some limit details
      | Daily Deposit Limit   | 1000   |
      | Weekly Deposit Limit  | 10000  |
      | Monthly Deposit Limit | 100000 |
    Then A message is displayed saying Changes Saved