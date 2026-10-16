Feature: Test to check change functionalities

  # 8 hrs 20 mins

  @Gamma1 @All
  Scenario Outline: Check Gamma functionalities at <Stage> workflow stages for <Tab> Tab
    Given the <User> user login is successful
    And close open tabs if any
    And user creates Change of type <Type> and subtype <SubType> and reaches to <Stage> stage
    Then user is to be able to see and edit relevant objects as per <Stage> under tab <Tab>
    Examples:
      | User  | Type | SubType                 | Stage                 | Tab                 | 06 March | 08 March | 11 June | future |
#      | Gamma | CO   | Standard Change Order   | Audit                 | General Information | pass     | pass     | pass    | pass   |
#      | Gamma | CO   | Standard Change Order   | Audit                 | Affected Objects    | pass     | pass     | pass    | pass   |
#      | Gamma | CO   | Standard Change Order   | Audit                 | Tasks               | pass     | pass     | pass    | pass   |
#      | Gamma | CO   | Standard Change Order   | Audit                 | Attachments         | pass     | pass     | pass    | pass   |
#      | Gamma | CO   | Standard Change Order   | Audit                 | Workflow            | pass     | pass     | pass    | pass   |
#      | Gamma | CO   | Standard Change Order   | Audit                 | Relationships       | pass     | pass     | pass    | pass   |
#      | Gamma | CO   | Standard Change Order   | Audit                 | Actions             | pass     | pass     | pass    | pass   |
#      | Gamma | CO   | Standard Change Order   | Confirm               | Workflow            | pass     | pass     | pass    | pass   |
#      | Gamma | CO   | Standard Change Order   | Confirm               | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CO   | Standard Change Order   | Confirm               | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CO   | Standard Change Order   | Update Documentation  | Workflow            | pass     | pass     | pass    | pass   |
      | Gamma | CO   | Standard Change Order   | Update Documentation  | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CO   | Standard Change Order   | Update Documentation  | Actions             | fail     | pass     | pass    | pass   |
      | Gamma | CO   | Standard Change Order   | Approve Documentation | Workflow            | pass     | pass     | pass    | pass   |
      | Gamma | CO   | Standard Change Order   | Approve Documentation | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CO   | Standard Change Order   | Approve Documentation | Actions             | pass     | pass     | pass2   | pass   |
      | Gamma | CO   | Standard Change Order   | Ready To Release      | Workflow            | pass     | pass     | pass    | pass   |
      | Gamma | CO   | Standard Change Order   | Ready To Release      | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CO   | Standard Change Order   | Completed             | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CO   | Standard Change Order   | Scheduled             | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Audit                 | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Audit                 | Affected Objects    | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Audit                 | Tasks               | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Audit                 | Attachments         | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Audit                 | Workflow            | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Audit                 | Relationships       | pass     | pass     | pass2   | pass   |
      | Gamma | CO   | No Revision Update      | Audit                 | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Confirm               | Workflow            | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Confirm               | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Update Documentation  | Workflow            | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Update Documentation  | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Approve Documentation | Workflow            | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Approve Documentation | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Ready To Release      | Workflow            | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Ready To Release      | Actions             | pass     | pass     | pass2   | pass   |
      | Gamma | CO   | No Revision Update      | Completed             | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CO   | No Revision Update      | Scheduled             | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Audit                 | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Audit                 | Affected Objects    | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Audit                 | Tasks               | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Audit                 | Attachments         | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Audit                 | Workflow            | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Audit                 | Relationships       | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Audit                 | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Screen                | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Screen                | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Test                  | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Test                  | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Approve               | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Approve               | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Ready To Execute      | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Ready To Execute      | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Change Request | Completed             | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Deviation      | Audit                 | General Information | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Deviation      | Audit                 | Affected Objects    | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Deviation      | Audit                 | Tasks               | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Deviation      | Audit                 | Attachments         | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Deviation      | Audit                 | Workflow            | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Deviation      | Audit                 | Relationships       | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Deviation      | Audit                 | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Deviation      | Review                | Workflow            | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Deviation      | Review                | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Deviation      | Authorized            | Workflow            | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Deviation      | Authorized            | Actions             | pass     | pass     | pass    | pass   |
      | Gamma | CR   | Standard Deviation      | Completed             | General Information | pass     | pass     | pass    | pass   |


#      | Gamma | CO   | Admin Data Update       | Draft                 | General Information |          |          |         |        |