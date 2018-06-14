@selenium @MarketsStreaming
Feature: Markets Streaming

  Scenario: Football game markets streaming
    Given a football event
    When the event kicks off
    Then the markets should be updated accordingly