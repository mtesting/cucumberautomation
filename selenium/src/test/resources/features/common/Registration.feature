@selenium @MyBet @BoB @Gana @RedZone @Baba
@Registration
Feature: Account Registration

  Scenario: Registration with valid data
    Given User is on the customer web
    When User clicks on Register
    And User enters valid data
    And User ticks the confirmation checkbox
    And User clicks on Next Step button
    Then A Confirmation message is displayed

  Scenario: Registration with duplicate email address
    Given user is already registered with the email "autotest1@amelco.co.uk"
    When user tries to register with the same email address "autotest1@amelco.co.uk"
    Then display a message "This email address already exists in our system."

#  Scenario: User tries to register with duplicate username
#    Given user is already registered with the username "autotest1"
#    When user tries to register with the same username "autotest1"
#    Then display a message "Your desired username already exists. Please choose another one."