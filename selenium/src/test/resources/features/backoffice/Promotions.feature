@selenium @MyBet
@BackOffice
@Promotions
Feature: Create a promotion

  Background:
    Given a user is logged into the CMS backoffice
    And the user tries to create a new promotion

  Scenario Outline: Create a new <promotion_type> promotion and added it to the Carousel Widget
    When selects the "<promotion_type>" promotion style
    And the user populates the <promotion name>, <time frame> And relevant defaults
    Then the promotion is created successfully
    And user selects the "/promotions/sports" section at the Page Builder
    When user adds the "<promotion_type>" promotion type to the Carousel Widget
    And user goes to the front-end Promotions section
    Then promotion should be displayed successfully

    Examples:
      | promotion_type |
      | Sports         |
      | Promotion      |


    #Scenario: Successfully creating a promotion PICTURE ONLY
    #-2) Bonus tile is not relevant within promo cases , I'm afraid it's not in use
    #When selects the "Picture only" promotion style
    #And the user populates the <promotion name>, <time frame> And relevant defaults
    #Then the promotion is created successfully

  #Scenario: Successfully creating a promotion BONUS TILE
  #4) sports live tile - might also need a test case with live events as the styling is slightly different
  #wait for ATT integration? look for live events first? (data preparation needed)
    #When ticks the <Bonus Tile> box
    #And the user populates the <promotion name>, <time frame> And relevant defaults
    #Then the promotion is created successfully