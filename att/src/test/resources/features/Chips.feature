@MyBet-integration
@chips-transaction
Feature: Chips Transactions

  Background:
    Given the User is logged onto the customer Sportsbook

  Scenario: Successful buy chips
    When User "buy" "10" chips
    Then the wallets balance must be updated

  Scenario: Successful sell chips
    When User "sell" "10" chips
    Then the wallets balance must be updated