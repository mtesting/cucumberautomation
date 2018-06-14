@selenium @MyBet
@BackOffice
@Transactions
Feature: Initiate Transaction

  Background:
    Given a user is logged into the CMS backoffice

  Scenario Outline: Initiate a positive <transaction_type> transaction through CMS
    And the user searches for a customer account
    When the user initiates a transaction "<transaction_type>"
    And input the details:
      | Funds Type | Amount | Reason           |
      | Online     | 100    | testing positive |
    Then the customers account is debited accordingly

    Examples:
      | transaction_type               |
      | Affiliate program (commission) |
      #| Bonus marketing                  |
      #| Goodwill                         |
      #| Correction                       |
      #| Finance correction on deposit    |


  Scenario Outline: Initiate a negative <transaction_type> transaction
    And the user searches for a customer account
    When the user initiates a transaction "<transaction_type>"
    And input the details:
      | Funds Type | Amount | Reason           |
      | Online     | -100   | testing negative |
    Then the customers account is debited accordingly

    Examples:
      | transaction_type             |
      #| Affiliate program (commission)   |
      #| Correction                       |
      #| Finance correction on withdrawal |
      #| International withdrawal         |
      | International withdrawal fee |
      #| Payment fee                      |