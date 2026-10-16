Feature: Test to check overview page functionalities

  @Gamma @All
  Scenario Outline: Check overview page functionalities
    Given the <User> user login is successful
    When user reaches overview page
    Then <Functionality> works as expected for <Special case>
    Examples:
      | User  | Functionality      | Special case | 06 March | 08 March |
      | Gamma | Waiting for review | NA           | pass     | fail     |
