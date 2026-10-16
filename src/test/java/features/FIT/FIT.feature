Feature: Test to check FIT functionalities

  @FIT @All
  Scenario Outline: Check FIT functionalities
    Given the <User> user login is successful
    When user creates Change of type <Type> and subtype <SubType> and reaches to <Stage> stage
    Then user is to be able to see and edit relevant objects as per <Stage> under tab <Tab>
    Examples:
      | User  | Type | SubType                        | Stage | Tab                 | coding | validation result |
      | Delta | CR   | Factory Implementation Tracker | Draft | Affected Objects    | done   |                   |
      | Delta | CR   | Factory Implementation Tracker | Draft | Attachments         | done   |                   |
      | Delta | CR   | Factory Implementation Tracker | Draft | Relationships       | done   |                   |
#      | Delta | CR   | Factory Implementation Tracker | Draft | General Information | IP     |                   |
#      | Delta | CR   | Factory Implementation Tracker | Draft |                     |        |                   |
