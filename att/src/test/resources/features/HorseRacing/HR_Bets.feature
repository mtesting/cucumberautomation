@HR @ps
@HRbets
Feature: HR Bets Placement

  Background:
    Given the User is logged onto the customer Sportsbook

  @HR-betsSP
  Scenario: Horse racing SP bets placement
    Given user is able to create 1 HR event
    And user successfully place random bets for HR
      | betType   | SINGLE |
      | mktType   | WEW    |
      | winType   | WIN    |
      | priceType | SP     |
    When user runs the event E2E
    Then results are updated in ATS database
    And the race results are saved in ats

  @HR-betsLP
  Scenario: Horse racing LP bets placement
    Given user is able to create 1 HR event
    And the LP flag is set to true
    And user successfully place random bets for HR
      | betType   | SINGLE |
      | mktType   | WEW    |
      | winType   | WIN    |
      | priceType | LP     |
    When user runs the event E2E
    Then results are updated in ATS database
    And the race results are saved in ats