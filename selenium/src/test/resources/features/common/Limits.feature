@selenium @MyBet @BoB
@Limits
Feature: Limits

  Scenario: Deposit is higher than daily limit set in Sportsbook -> Exclusions
    Given the User is logged onto the customer Sportsbook
    And User clicks on Deposit
    When User enters a deposit amount "100000"
    And Clicks on Continue To Payment Method
    Then display a message "You have exceeded your daily deposit limit of 1000€. Your remaining deposit limit for today is"

  @Betstars
  Scenario: Bet Stake exceeds limits
    Given the User is logged onto the customer Sportsbook
    And User clicks on "1" selections
    When User enters a "Single" bet amount "9999999"
    And User clicks on Place Bet
    Then An unsuccessful message is displayed