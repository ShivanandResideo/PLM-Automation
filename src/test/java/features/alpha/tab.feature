Feature: Test to verify information in each tab

  @Alpha @All
  Scenario Outline: <User> reaches <type> edit page and check information
    Given the <User> user login is successful
    When <User> reaches old edit page for <type>
    # the specific part and doc number is defined in the above step definition
    Then user checks information of <type> in <tab> for <specialCase>
    Examples:
      | User  | type      | tab                 | specialCase                      |
      | alpha | Items     | General Information |                                  |
      | alpha | Items     | Attachments         |                                  |
      | alpha | Items     | Structure           | 100-00008-001-00                 |
      | alpha | Items     | AML                 |                                  |
      | alpha | Items     | Changes             | Item Changes                     |
      | alpha | Items     | Changes             | Structure Changes 100-00008-001-00 |
      | alpha | Items     | Relationships       |                                  |
      | alpha | Items     | Team                |                                  |
      | alpha | Items     | Item Grade          |                                  |
      | alpha | Items     | Where Used          |                                  |
      | alpha | Items     | History             |                                  |
      | alpha | Documents | General Information |                                  |
      | alpha | Documents | Attachments         |                                  |
      | alpha | Documents | Structure           | 100-00008-001-00                 |
      | alpha | Documents | Changes             | Item Changes                     |
      | alpha | Documents | Changes             | Structure Changes 100-00008-001-00 |
      | alpha | Documents | Relationships       |                                  |
      | alpha | Documents | Team                |                                  |
      | alpha | Documents | Where Used          |                                  |
      | alpha | Documents | History             |                                  |
      | alpha | Parts     | General Information |                                  |
      | alpha | Parts     | Attachments         |                                  |
      | alpha | Parts     | Where Used          |                                  |
      | alpha | SCR       | General Information |                                  |
      | alpha | SCR       | Affected Objects    |                                  |
      | alpha | SCR       | Tasks               |                                  |
      | alpha | SCR       | Impact Analysis     |                                  |
      | alpha | SCR       | Attachments         |                                  |
      | alpha | SCR       | Workflow            |                                  |
      | alpha | SCR       | Relationships       |                                  |
      | alpha | SCR       | Team/Security       | Need to check with Jennifer/Amit |
      | alpha | SCR       | History             |                                  |
      | alpha | SCR       | Others              |                                  |
      | alpha | SDV       | General Information |                                  |
      | alpha | SDV       | Affected Objects    |                                  |
      | alpha | SDV       | Tasks               |                                  |
      | alpha | SDV       | Impact Analysis     |                                  |
      | alpha | SDV       | Attachments         |                                  |
      | alpha | SDV       | Workflow            |                                  |
      | alpha | SDV       | Relationships       |                                  |
      | alpha | SDV       | Team/Security       | Need to check with Jennifer/Amit |
      | alpha | SDV       | History             |                                  |
      | alpha | SDV       | Others              |                                  |
      | alpha | SCO       | General Information |                                  |
      | alpha | SCO       | Affected Objects    |                                  |
      | alpha | SCO       | Tasks               |                                  |
      | alpha | SCO       | Impact Analysis     |                                  |
      | alpha | SCO       | Attachments         |                                  |
      | alpha | SCO       | Workflow            |                                  |
      | alpha | SCO       | Relationships       |                                  |
      | alpha | SCO       | Team/Security       | Need to check with Jennifer/Amit |
      | alpha | SCO       | History             |                                  |
      | alpha | SCO       | Others              |                                  |
      | alpha | NRU       | General Information |                                  |
      | alpha | NRU       | Affected Objects    |                                  |
      | alpha | NRU       | Tasks               |                                  |
      | alpha | NRU       | Impact Analysis     |                                  |
      | alpha | NRU       | Attachments         |                                  |
      | alpha | NRU       | Workflow            |                                  |
      | alpha | NRU       | Relationships       |                                  |
      | alpha | NRU       | Team/Security       | Need to check with Jennifer/Amit |
      | alpha | NRU       | History             |                                  |
      | alpha | NRU       | Others              |                                  |
