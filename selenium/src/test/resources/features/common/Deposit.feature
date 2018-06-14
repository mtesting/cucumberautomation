@selenium @MyBet @Baba
@Deposit
Feature: Deposit Action

  Scenario: Deposit with a valid previous registered method
    Given the User is logged onto the customer Sportsbook
    When User clicks on Deposit
    And User enters a deposit amount "10"
    And Clicks on Continue To Payment Method
    And pays with a valid method
    Then Message displayed Your deposit was successful

#Scenario: Successful deposit with a new credit card
#    Given the User is logged onto the customer Sportsbook
#    When User clicks on Deposit
#    And User enters a deposit amount "100"
#    And Clicks on Continue To Payment Method
#    And pays with a new method
#    Then Message displayed Your deposit was successful