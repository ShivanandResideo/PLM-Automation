Feature: Test to verify Overview page

  @Delta @All @Overview
  Scenario Outline: <User> reaches overview page and check task features
    Given the <User> user login is successful
    When user reaches overview page
    Then user checks availability of task features
    Examples:
      | User  | July 2026 |
      | delta | pass      |
