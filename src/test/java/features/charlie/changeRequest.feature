Feature: Test to Create Change

  @Charlie1 @ChangeRequest
  Scenario Outline: Create Change and check attributes
    Given the <User> user login is successful
#    When user tries to create 3 items and 3 documents for <Special Case>
    And user tries to create 1 Changes of type <Type> and sub type <Sub Type> priority as <Priority>, severity as <Severity>, category code as <Category Code>, justification code as <Justification Code> for <Special Case>
    Then Then all relevant <Type> functionalities are working as expected for <Special Case>
    Examples:
      |Pass | User   | Type | Sub Type              | Priority          | Severity                                           | Category Code                             | Justification Code                      | Special Case     |
      |Pass| charlie | CR   | Standard Change Request | NA                | 1 - Customer issue - requires immediate resolution | Bucket Project / Sustaining / Maintenance | Corrective Action (fix something)       | GeneralCase     |
      |Pass| charlie | CR   | Standard Change Request | NA                | 1 - Customer issue - requires immediate resolution | Bucket Project / Sustaining / Maintenance | Corrective Action (fix something)       | GeneralCase     |
      |pass| charlie | CR   | Standard Change Request | Refer to Severity | 1 - Unable to manufacture - line down              | Discontinuation                           | Improvement (feature / function / cost) | AffectedObjects |
      |pass| charlie | CR   | Standard Change Request | Refer to Severity | 1 - Unable to sell or ship product                 | NPI Project                               | Maintenance                             | Tasks           |
      |pass| charlie | CR   | Standard Change Request | Refer to Severity | 2 - Schedule slip Manufacturing                    | VE / CE Project                           | Maintenance                             | Attachments     |
      |pass| charlie | CR   | Standard Change Request | Refer to Severity | 2 - Schedule slip NPI project                      | VE / CE Project                           | Maintenance                             | Workflow        |
      |pass| charlie | CR   | Standard Change Request | Refer to Severity | 2 - Schedule slip Sales or Shipment to Customer    | VE / CE Project                           | Maintenance                             | Relationships   |
      |pass| charlie | CR   | Standard Change Request | Refer to Severity | 3 - Standard                                       | VE / CE Project                           | Maintenance                             | Actions          |
      |test| charlie | CR   | Standard Change Request | Refer to Severity | 3 - Standard                                       | VE / CE Project                           | Maintenance                             | Status           |
      |test| charlie | CR   | Standard Deviation      | NA                | 1 - Customer issue - requires immediate resolution | Bucket Project / Sustaining / Maintenance | Corrective Action (fix something)       | GeneralCase     |
      |test| charlie | CR   | Standard Deviation      | Refer to Severity | 1 - Unable to manufacture - line down              | Discontinuation                           | Improvement (feature / function / cost) | AffectedObjects |
      |test| charlie | CR   | Standard Deviation      | Refer to Severity | 1 - Unable to sell or ship product                 | NPI Project                               | Maintenance                             | Tasks           |
      |test| charlie | CR   | Standard Deviation      | Refer to Severity | 2 - Schedule slip Manufacturing                    | VE / CE Project                           | Maintenance                             | Attachments     |
      |test| charlie | CR   | Standard Deviation      | Refer to Severity | 2 - Schedule slip NPI project                      | VE / CE Project                           | Maintenance                             | Workflow        |
      |test| 301917 | CR   | Standard Deviation      | Refer to Severity | 2 - Schedule slip Sales or Shipment to Customer    | VE / CE Project                           | Maintenance                             | Relationships   |
      |test| 301917 | CR   | Standard Deviation      | Refer to Severity | 3 - Standard                                       | VE / CE Project                           | Maintenance                             | Actions          |
