Feature: Test to check home page functionalities

  @Delta @Home @All
  Scenario Outline: Check home page functionalities for <Functionality>
    Given the <User> user login is successful
    When user tries to click <Icon> on top bar to check for <Functionality> for <Special case>
    Then user is to be able to see and navigate to relevant objects
    Examples:
      | User   | Icon | Functionality | Special case | 06 March | 08 March |
      | delta  | Star | Favorites     | NA           | pass     | pass     |
      | delta  | Star | Recent Items  | NA           | pass     | pass     |
