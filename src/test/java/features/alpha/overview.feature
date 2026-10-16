Feature: Test to verify Overview page

  @Alpha @All
  Scenario Outline: <User> reaches overview page and check task features
    Given the <User> user login is successful
    When user reaches overview page
    Then user checks availability of task features
    Examples:
      | User  | July 2026 |
      | alpha | pass      |

  @Alpha @All
  Scenario Outline: <User> reaches overview page and check search features
    Given the delta user login is successful
    And user tries to create object of type <Type>
    Then the <User> user login is successful
    When user reaches overview page
    Then user checks availability of search features for <Type>
    Examples:
      | User  | Type               | Result                                                            |
#      | alpha | Items              | pass bug reported for middle part of name not able to search       |
#      | alpha | Documents          | pass bug reported for middle part of name not able to search       |
#      | alpha | Change Orders      | pass                                                              |
#      | alpha | Change Requests    | pass                                                              |
#      | alpha | Manufacturer Parts | pass                                                              |
#      # visit later page not loading
#      | alpha | Manufacturer       | |
##  visit later
#    | alpha | Multiple Items     | covered above |
#      | alpha | Multiple Documents | covered above |

  @Alpha @All
  Scenario Outline: <User> reaches overview page and check editability
    Given the <User> user login is successful
    When user reaches <Type> edit page
    Then user checks editability for <Type>
    Examples:
      | User  | Type               | result |
      | alpha | Items              | pass   |
      | alpha | Documents          | pass   |
      | alpha | Manufacturer Parts | pass   |

  @Alpha @All
  Scenario Outline: <User> reaches overview page and check Favorites
    Given the <User> user login is successful
    When user reaches new edit page for type <Type>
    Then user checks the ability to add favorites for <Type>
    Examples:
      | User  | Type               | result |
      | alpha | Items              | fail   |
      | alpha | Documents          | IP     |
      | alpha | Change Orders      | IP     |
      | alpha | Change Requests    | IP     |
      | alpha | Manufacturer Parts | IP     |

    # the below is covered in tab.feature
#  @Alpha
#  Scenario Outline: <User> reaches edit page and check editability
#    Given the <User> user login is successful
#    When user reaches edit page for type <Type>
#    Then user checks the ability to edit different information for <Type>
#    Examples:
#      | User  | Type               |
#      | alpha | Items              |
#      | alpha | Documents          |
#      | alpha | Change Orders      |
#      | alpha | Change Requests    |
#      | alpha | Manufacturer Parts |