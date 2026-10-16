Feature: Test to verify the notification feature for Beta users

  @Beta @All
  Scenario Outline: <User> logs in and checks for notifications
    Given the <SecondaryUser> user login is successful
    When <SecondaryUser> creates <Type> for case <SpecialCase> #pending to go to the required workflow state also
#     the specific object number is defined in the above step definition
    And makes edits on <Tab> tab in <Type> with respect to <User> for <SpecialCase>
    # And logout covered in next step
    Then the <User> user login is successful
    And check for notifications and other features on <Type> for user <User> for case <SpecialCase>
    Examples:
      | User | SecondaryUser | Type | Tab      | SpecialCase           | Jennifer test ID                                                                                                        |
      | beta | gamma         | SCO  | Tasks    | Draft                 | B23 done pass                                                                                                           |
      | beta | gamma         | SCO  | Tasks    | Current Blank         | B24     done pass bug                                                                                                   |
      | beta | gamma         | SCO  | Tasks    | Current               | B24         done pass bug                                                                                               |
      | beta | gamma         | SCO  | Tasks    | Future                | B25 done pass                                                                                                           |
      | beta | gamma         | SCO  | Tasks    | Future Promoted       | B25 done pass bug                                                                                                       |
      | beta | gamma         | SCO  | Workflow | Audit                 | B26, B27 done connected                                                                                                 |
      | beta | gamma         | SCO  | Workflow | Audit Reject          | B28, B29 done continued                                                                                                 |
      | beta | gamma         | SCO  | Tasks    | Audit Promoted        | B30, B31 done (we may have to have different tests to verify approval notifications and tasks notifications)            |
      | beta | gamma         | SCO  | Tasks    | Confirm               | B32, B33 done (same as above. This is where we could use the tab column and see which type of notification is expected) |
      | beta | gamma         | SCO  | Tasks    | Update Documentation  | B34, B35 done                                                                                                           |
      | beta | gamma         | SCO  | Tasks    | Approve Documentation | B36, B37 done                                                                                                           |
      | beta | gamma         | SCO  | Tasks    | Ready to Release      | B38, B39                                                                                                                |
      | beta | gamma         | NRU  | Tasks    | Audit Promoted        | B40, B41                                                                                                                |
      | beta | gamma         | NRU  | Tasks    | Confirm               | B42, B43                                                                                                                |
      | beta | gamma         | NRU  | Tasks    | Update Documentation  | B44, B45                                                                                                                |
      | beta | gamma         | NRU  | Tasks    | Approve Documentation | B46, B47                                                                                                                |
      | beta | gamma         | NRU  | Tasks    | Ready to Release      | B48, B49                                                                                                                |
      | beta | gamma         | SCR  | Tasks    | Audit Promoted        | B50, B51                                                                                                                |
      | beta | gamma         | SCR  | Tasks    | Screen                | B52, B53                                                                                                                |
      | beta | gamma         | SCR  | Tasks    | Test                  | B54, B55                                                                                                                |
      | beta | gamma         | SCR  | Tasks    | Approve               | B56, B57                                                                                                                |
      | beta | gamma         | SCR  | Tasks    | Ready to Execute      | B58, B59                                                                                                                |
      | beta | gamma         | SDV  | Tasks    | Audit Promoted        | B60, B61                                                                                                                |
      | beta | gamma         | SDV  | Tasks    | Review                | B62, B63                                                                                                                |
      | beta | gamma         | SDV  | Tasks    | Authorized            | B64, B65                                                                                                                |

