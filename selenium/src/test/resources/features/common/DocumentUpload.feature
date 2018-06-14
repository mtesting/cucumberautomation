@selenium @MyBet
@DocumentUpload
Feature: Document Upload Action
  In order to validate my account
  As a final user
  I want to upload a verification document

  Scenario: Upload of a Proof of ID document
    Given the User is logged onto the customer Sportsbook
    And user access to the Document Upload section
    When selects a document to upload
    And enters the required details
    Then a message confirms upload success