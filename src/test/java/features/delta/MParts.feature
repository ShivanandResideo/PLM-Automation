Feature: Test to Create manufacturer parts

  @Delta @All
  Scenario Outline: Create parts and check attributes for <SpecialCase>
    Given the <User> user login is successful
    When user tries to create new manufacturer Parts with part number <MPN> under Manufacturer <Manufacturer> with Status <Status> for <SpecialCase>
    Then part details page is to be reached
    Examples:
      | User  | Manufacturer              | MPN         | Status   | SpecialCase         | March 06 | 08 March |
      | delta | AUTOMATION COMPONENTS INC | NA          | Empty    | search manufacturer | pass     | pass     |
      | delta | AUTOMATION COMPONENTS INC | @ut0 m@{10n | Active   | MPN special char    | pass     | pass     |
      | delta | AUTOMATION COMPONENTS INC | NA          | Inactive | description empty   | pass     | pass     |
      | delta | AUTOMATION COMPONENTS INC | NA          | Pending  | NA                  | pass     | pass     |
#