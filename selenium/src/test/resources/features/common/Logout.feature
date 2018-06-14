@selenium @MyBet @BoB @Gana @RedZone
@Logout
Feature: Logout Action

  Scenario: Successful LogOut
    Given the User is logged onto the customer Sportsbook
    When User LogOut from the Application
    Then Message displayed LogOut Successfully