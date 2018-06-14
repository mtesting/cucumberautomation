@selenium @MyBet @BoB
@Withdraw
Feature: Withdraw Action

  Scenario: Withdraw with a previous registered method
    Given the User is logged onto the customer Sportsbook
    When User clicks on Withdraw
    And Enters a withdraw amount
    And continues with the process
    Then A successful message is displayed