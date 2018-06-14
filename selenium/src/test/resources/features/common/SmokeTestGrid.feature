@selenium @SmokeTestGrid

Feature: Smoke Test
  As a tester
  I want to be able to open the homepage from different browsers/OS
  So that I can assert the product compatibility and performance

  @SmokeTestGrid-OnLinux
  Scenario Outline: Linux User can load the homepage properly on <browser>
    Given a "Linux" user
    When they access the homepage using "<browser>"
    Then the homepage should load successfully

    Examples:
      | browser |
      | firefox |
      | chrome  |


  @SmokeTestGrid-OnWindows8
  Scenario Outline: Windows 8 User can load the homepage properly on <browser>
    Given a "Windows8.1" user
    When they access the homepage using "<browser>"
    Then the homepage should load successfully

    Examples:
      | browser |
      | firefox |
      | ie      |
      | chrome  |

  @SmokeTestGrid-OnWindows10
  Scenario Outline: Windows 10 User can load the homepage properly on <browser>
    Given a "Windows10" user
    When they access the homepage using "<browser>"
    Then the homepage should load successfully

    Examples:
      | browser |
      | edge    |
      | ie      |


#  @SmokeTestGrid-OnAndroid
#  Scenario Outline: Android User can load the homepage properly on <browser>
#    Given a "Android" user
#    When they access the homepage using "<browser>"
#    Then the homepage should load successfully

#    Examples:
#      | browser       |
#      |	android		  |


  @SmokeTestGrid-OnMac
  Scenario Outline: Mac User can load the homepage properly on <browser>
    Given a "ElCapitan" user
    When they access the homepage using "<browser>"
    Then the homepage should load successfully

    Examples:
      | browser |
      | safari  |

  @SmokeTestGrid-OniOS
  Scenario Outline: iOS User can load the homepage properly on <browser>
    Given a "Mac" user
    When they access the homepage using "<browser>"
    Then the homepage should load successfully

    Examples:
      | browser |
      | iphone  |
      | ipad    |