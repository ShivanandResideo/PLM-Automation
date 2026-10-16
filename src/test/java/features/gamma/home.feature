Feature: Test to check home page functionalities

#   1 min 8 sec

  @Gamma @home @All
  Scenario Outline: Check home page functionalities
    Given the <User> user login is successful
    When user tries to click <Icon> on top bar to check for <Functionality> for <Special case>
    Then user is to be able to see and navigate to relevant objects
    Examples:
      | User  | Icon | Functionality    | Special case | 06 March | 08 March |
      | Gamma | Home | Things to Finish | NA           | pass     | pass     |
#      | Gamma | Star | Recent Items     | NA           |
# yet to verify as the first case required more notifications
