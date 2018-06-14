@selenium @MyBet
@Chips
Feature: Chips Transactions

  Background:
    Given the User is logged onto the customer Sportsbook
    And User clicks on Chips Buy/sell

  Scenario: Successful buy chips
    When User clicks on Buy Chips
    And User enters an amount "4"
    And User clicks on Buy Chips orange button
    Then Message displayed Your transaction was successful
    And The account cash balance updated

  Scenario: Successful sell chips
    When User clicks on Sell Chips
    And User enters an amount "4"
    And User clicks on Sell Chips orange button
    Then Message displayed Your transaction was successful
    And The account cash balance updated

  Scenario: Unsuccessful Purchase of Casino Chips (insufficient funds in OLD WALLET)
    And User clicks on Buy Chips
    When User enters an amount "1000000.00"
    Then display Message "The maximum number of chips you can buy right now is <OLD WALLET>"
    And do not redirect the Punter to the success page

  Scenario: Unsuccessful Sale of Casino Chips (insufficient funds in CHIPS WALLET)
    And User clicks on Sell Chips
    When User enters an amount "1000000.00"
    Then display Message "The maximum number of chips you can sell right now is <CHIPS WALLET>"
    And do not redirect the Punter to the success page