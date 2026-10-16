Feature: Test to verify Advanced Search page

  @Alpha1 @All
  Scenario Outline: Test <TestID>: <User> reaches Advanced Search page and check features
    Given the <User> user login is successful
    When user reaches Advanced Search page for <type>
    Then user checks advanced search features of <type> for <specialCase>
      # revisit required for notification count
    Examples:
      | TestID | User  | type               | specialCase | July                                                                                    |
      | A-1    | alpha | Items              | a           | pass send object failed  PASS                                                             |
      | A-2    | alpha | Documents          | a           | pass send object failed  PASS                                                               |
      | A-3    | alpha | Change Orders      | a           | pass view -> asc, dsc missing. Copy missing. Img hide show missing. Send object failed. |
      # (AdvancedSearchSteps.java:260) -> (Commons.java:1014)
      | A-4    | alpha | Change Requests    | a           | pass same as above                                                                      |
       # (AdvancedSearchSteps.java:260) -> (Commons.java:1014)
      | A-5    | alpha | Manufacturer Parts | a           | removing search fields is failing - BUG                                                 |
  # (AdvancedSearchSteps.java:155) -> (Commons.java:1014)
      | A-6    | alpha | Manufacturers      | a           | view reorder columns not visible, excel copy detach etc not working                     |
        # (AdvancedSearchSteps.java:196)