Feature: Test to Create Change for delta

  @Delta @ChangeOrder @All
  Scenario Outline: Create Change of type <Sub Type> and check attributes for tab <Special Case>
    Given the <User> user login is successful
#    When user tries to create 3 items and 3 documents for <Special Case>
    And user tries to create 1 Changes of type <Type> and sub type <Sub Type> priority as <Priority>, severity as <Severity>, category code as <Category Code>, justification code as <Justification Code> for <Special Case>
    Then Then all relevant <Type> functionalities are working as expected for <Special Case>
    Examples:
      | User  | Type | Sub Type              | Priority     | Severity                                           | Category Code                             | Justification Code                      | Special Case      | 06 March | 08 March | 11 June |
      | Delta | CO   | Standard Change Order | NA           | 1 - Customer issue - requires immediate resolution | Bucket Project / Sustaining / Maintenance | Corrective Action (fix something)       | GeneralCase       | pass     | pass     | \       |
      | Delta | CO   | Standard Change Order | 1 - Critical | 1 - Unable to manufacture - line down              | Discontinuation                           | Improvement (feature / function / cost) | COAffectedObjects | fail     | fail     | fail    |
  # the above test failed because of element click intercepted (CreateCOSteps.java:347) -> (CreateCOSteps.java:2362) -> (StructuresTab.java:352) |
      | Delta | CO   | Standard Change Order | 2 - High     | 1 - Unable to sell or ship product                 | NPI Project                               | Maintenance                             | Tasks             | pass     | pass     |         |
      | Delta | CO   | Standard Change Order | 3 - Standard | 2 - Schedule slip Manufacturing                    | VE / CE Project                           | Maintenance                             | Attachments       | pass     | pass     |         |
      | Delta | CO   | Standard Change Order | NA           | 2 - Schedule slip Sales or Shipment to Customer    | VE / CE Project                           | Maintenance                             | Relationships     | pass     | pass     |         |
      | Delta | CO   | Standard Change Order | NA           | 3 - Standard                                       | VE / CE Project                           | Maintenance                             | SaveAs            | pass     | pass     |         |
      | Delta | CO   | No Revision Update    | NA           | 1 - Customer issue - requires immediate resolution | Bucket Project / Sustaining / Maintenance | Corrective Action (fix something)       | GeneralCase       | pass     | pass     |         |
      | Delta | CO   | No Revision Update    | 1 - Critical | 1 - Unable to manufacture - line down              | Discontinuation                           | Improvement (feature / function / cost) | AffectedObjects   | pass     | pass     |         |
      | Delta | CO   | No Revision Update    | 2 - High     | 1 - Unable to sell or ship product                 | NPI Project                               | Maintenance                             | Tasks             | pass     | pass     |         |
      | Delta | CO   | No Revision Update    | 3 - Standard | 2 - Schedule slip Manufacturing                    | VE / CE Project                           | Maintenance                             | Attachments       | pass     | pass     |         |
      | Delta | CO   | No Revision Update    | NA           | 2 - Schedule slip Sales or Shipment to Customer    | VE / CE Project                           | Maintenance                             | Relationships     | pass     | pass     | pass2    |
      | Delta | CO   | No Revision Update    | NA           | 3 - Standard                                       | VE / CE Project                           | Maintenance                             | SaveAs            | pass     | pass     |         |


# not required
  Scenario Outline: Create Change of type <Sub Type> and check attributes for tab Workflow
    Given the <User> user login is successful
#    When user tries to create 3 items and 3 documents for <Special Case>
    And user tries to create 1 Changes of type <Type> and sub type <Sub Type> priority as <Priority>, severity as <Severity>, category code as <Category Code>, justification code as <Justification Code> for <Special Case>
    Then Then all relevant <Type> functionalities are working as expected for <Special Case>
    # Signout from delta and signIn fromGamma to approve the change
#    Signin from delta and verify the functionalities
    Examples:
      | Delta | CO | No Revision Update    | NA | 2 - Schedule slip NPI project | VE / CE Project | Maintenance | Workflow | stuck in loop -> WorkflowTab36 |
      | Delta | CO | Standard Change Order | NA | 2 - Schedule slip NPI project | VE / CE Project | Maintenance | Workflow | stuck in loop -> WorkflowTab36 |




#     General case has the attribute verification and
#    -------      Then verify the attributes in general information and SCO change attributes and Design team and Impacted manufacturing and Change implementation details
#    -----------  When Items and Docs are created and added to affected objects of the SCO using select and Add
#    Then verify the modifiable options in the newly added items and docs
#    And affected objects when clicked goes to edit page (is editable) and the edit when saved appears in red
#    And structure tab of affected object is verified for D127, D128
#    And Attachment tab of affected objects is verified
#    And After saving the above 3 changes are shown in the Summary of affected tab of SCO
#    And the items are removable using remove icon
#    When Tasks are added to the tasks tab
#    Then verify the tasks tab D132, D133,
#    And Task can be removed
#    When Attachments are added
#    Then Verify the attachments
#    And attachments are removable
#    When participants are added in workflow for all steps except draft
#    Then verify D137
#    When Relationships are added
#    Then D138, D139, D140
#  Then Create another SCO using Actions-Save as -> D141
#    Then change status has just prepare and cancel
#    Then change status to prepare (dialog box)
#    Then change status to audit
#  And verify the modifiable and non modifiable attributes
#    Then Change status to confirm
#  And verify the modifiable and non modifiable attributes
#    Then Change status to update documentation
#  And verify the modifiable and non modifiable attributes
#    Then Change status to approve documentation
#  And verify the modifiable and non modifiable attributes
#    Then Change status to ready to release
#  And verify the modifiable and non modifiable attributes
#    Then Change status to schedule
#  And verify the modifiable and non modifiable attributes
#    Then Change status to completed
#  And verify the modifiable and non modifiable attributes
#    Then change Order is cancelled


#    Examples
#    SCO,
#
#
#    verfify:
#    D118, D119, D120, D121,