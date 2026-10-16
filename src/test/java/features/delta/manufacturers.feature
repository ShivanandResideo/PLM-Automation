Feature: Test to Create manufacturers

  @Delta @All
  Scenario Outline: Create manufacturers and check attributes
    Given the <User> user login is successful
    When user tries to create new manufacturers
#    Then part details page is to be reached // option being active is enough
    Examples:
      | User  | 06 march | 08 March |
      | delta | pass     | pass     |