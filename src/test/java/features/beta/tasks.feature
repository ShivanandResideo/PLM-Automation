Feature: Test to verify access under tasks in overview page

  @Beta @All
  Scenario Outline: <User> reaches overview page and checks access
    Given the <User> user login is successful
    When user reaches overview page
    Then user checks availability of task features
    Examples:
      | User  |
      | beta  |


  @Beta @All
  Scenario Outline: <User> reaches overview page and checks access
    Given the <User> user login is successful
    When user reaches old edit page for <type>
    Then user checks minor information of <type> in <tabs> for <specialCase>
    Examples:
      | User | type               | tabs                                                            | specialCase |
      | beta | Items              | General Information,Attachments,Structure,AML                   |             |
      | beta | Documents          | General Information,Attachments,Structure                       |             |
      | beta | SCO                | General Information,Attachments,Affected Objects,Tasks,Workflow |             |
      | beta | NRU                | General Information,Attachments,Affected Objects,Tasks,Workflow |             |
      | beta | SCR                | General Information,Attachments,Affected Objects,Tasks,Workflow |             |
      | beta | SDV                | General Information,Attachments,Affected Objects,Tasks,Workflow |             |
      | beta | Manufacturer Parts | General Information,Attachments                                 |             |
      | beta | Manufacturers      | General Information,Attachments                                 |             |
    # tabs column value is comma separated
