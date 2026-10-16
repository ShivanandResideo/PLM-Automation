Feature: Test to verify Overview page

  @Charlie11
  Scenario Outline: Reach overview page and check attributes
    Given the <User> user login is successful
    When user reaches overview page
    Then user checks availability of task features
    Examples:
      | User    |
      | charlie |

  @Charlie11
  Scenario Outline: Reach overview page and check attributes
    Given the <User> user login is successful
    When user reaches <Type> edit page
    Then user checks editability for <Type>
    Examples:
      | User    | Type               |
      | charlie | Items              |
      | charlie | Documents          |
      | charlie | Manufacturer Parts |
