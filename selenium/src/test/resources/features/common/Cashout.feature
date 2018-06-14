@selenium @Betstars
@CashOut
Feature: Bets Cash Out

  Background:
    Given the User is logged onto the customer Sportsbook

  Scenario Outline: Cash Out a <bet_type> bet
    And user has placed a "<bet_type>" bet
    When User tries to cash out the bet placed
    Then the money has to be paid back into the account

    Examples:
      | bet_type |
      | Single   |
      | System   |

  #TODO implement And one of the selections has been settled as "<outcome>"
#  Scenario Outline: Cash Out a System bet with an outcome resulted as <outcome>
#    And user has placed a "System" bet
#    And one of the selections has been settled as "<outcome>"
#    When User tries to cash out the bet placed
#    Then the money has to be paid back into the account
#
#    Examples:
#      | outcome |
#      | Win     |
#      | Lose    |
#      | Void    |