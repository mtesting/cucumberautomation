@selenium @MyBet
@BackOffice
Feature: BackOffice Login

  Background:
    Given a user is on the CMS site

  Scenario: Successfully logging in with valid credentials
    When user enters invalid credentials
    And user clicks on login button
    Then an error message is displayed: “Invalid Credentials”

  Scenario: Login with valid credentials
    When user enters valid credentials
    And user clicks on login button
    Then the user is successfully logged in And redirected to the CMS backoffice