Feature: Test to Create Documents

  #06 March: 21 mins

  @Delta @All
  Scenario Outline: Create <Document> and check attributes for type <Document> with PGM <DocGenMtd>
    Given the <User> user login is successful
    When user tries to create <Document> document with docGenMtd as <DocGenMtd> Prefix as <PRE> Root Part-Family as <RPF> Version as <VER> Legacy as <Legacy> for <SpecialCase>
    Then edit document page is to be reached with all relevant attributes
    Examples:
      | User  | Document              | DocGenMtd | PRE | RPF | VER | Legacy | SpecialCase | 17 Sept | 08 March |
      | delta | ERCV Document         | 0         | NA  | NA  | NA  | NA     | NA          | pass     | pass     |
      | delta | NPI Document          | 0         | NA  | NA  | NA  | NA     | NA          | pass     | pass     |
      | delta | Procedure             | 0         | NA  | NA  | NA  | NA     | NA          | pass     | pass     |
      | delta | Specification         | 1         | NA  | NA  | NA  | NA     | NA          | fail     | fail     |
      | delta | Specification         | 2         | NA  | NA  | NA  | NA     | NA          | pass     | pass     |
      | delta | Specification         | 3         | NA  | NA  | NA  | NA     | NA          | pass     | pass     |
      | delta | Operational Documents | 0         | NA  | NA  | NA  | NA     | NA          | pass     | pass     |
      | delta | Quality Records       | 0         | NA  | NA  | NA  | NA     | NA          | pass     | pass     |
      | delta | System Documents      | 0         | NA  | NA  | NA  | NA     | NA          | pass     | pass     |

  @Delta @All
  Scenario Outline: Create docs and check attachments tab for <SpecialCase>
    Given the <User> user login is successful
    When user tries to create <Document> document with docGenMtd as <DocGenMtd> Prefix as <PRE> Root Part-Family as <RPF> Version as <VER> Legacy as <Legacy> for <SpecialCase>
    And makes necessary changes as per the case <SpecialCase> for Document
    Then attachment page is to be reached with all relevant functionalities
    Examples:
      | User  | Document      | DocGenMtd | PRE | RPF | VER | Legacy | SpecialCase               | 06 March |
      | delta | ERCV Document | 0         | NA  | NA  | NA  | NA     | Attach Zip                | pass     |
      | delta | NPI Document  | 0         | NA  | NA  | NA  | NA     | Attach File Document      | pass     |
#      | delta  | Procedure     | 0         | NA  | NA  | NA  | NA     | Attach Repository Manufacturing | disabled after request from Vijay
      | delta | ERCV Document | 0         | NA  | NA  | NA  | NA     | Attach Text Miscellaneous | pass     |
      | delta | ERCV Document | 0         | NA  | NA  | NA  | NA     | Attach URL Source         | pass     |

  @Delta @All
  Scenario Outline: Create 3 objects and check structure tab
    Given the <User> user login is successful
    When user tries to create <INumber> items and <DNumber> documents for AffectedObjects
    Then all relevant <Functionality> functionalities are working as expected for Documents
#    And The changes made in the structure of child objects are reflected in the edit pages of the child items
    Examples:
      | User  | INumber | DNumber | Functionality | 06 march | 08 March | June 2026 |
      | delta | 2       | 1       | Structure Tab | pass     | pass     | the control is going to items once the tab is closed but needs to be stay in documents. StructureSteps -172 |
  # the above case failed with element click intercepted at (StructureSteps.java:138)
#      | delta  | 2       | 1       | drag and drop |

  # implementation ???
 # Add atleast one to favorites for each user type of each object type
