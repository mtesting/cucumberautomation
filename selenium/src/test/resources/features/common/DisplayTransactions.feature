@selenium @MyBet
@DisplayTransactions
Feature: Display Transactions
  In order to check the account activity
  As a final user
  I want to see all my previous transactions

  Background:
    Given the User is logged onto the customer Sportsbook

  Scenario Outline: Display <trans_type> transactions
    And the user has perform a "<trans_type>" operation
    And the sportsbook user navigates to their account transactions
    When they filter by "<trans_type>" only
    Then All the relevant transactions must be displayed
    And no other transactions will be visible

    Examples:
      | trans_type     |
      | Bets           |
      | Deposit        |
      | Withdrawal     |
      | Chips Transfer |