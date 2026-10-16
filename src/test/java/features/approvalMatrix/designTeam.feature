Feature: Tests to verify Design Team

  # below test is obsolete as the new DT is set by the combination of other parameters
  @DesignTeamUAT
  Scenario Outline: Create <Item> and check Design Team dropdown options
    Given the <UserType> user login is successful
    When user tries to create <Item> item with PUOM as <PUOM> partGenMtd as <PartGenMtd> existing family as <EF> Option as <FO> version as <FV> Legacy <Legacy> LOB as <LOB> DA as <DA> CC as <CC> for <SpecialCase>
    Then edit item page is to be reached to verify Design Team dropdown options
    Examples:
      | UserType | Item    | PUOM      | PartGenMtd | EF | FO | FV | Legacy | LOB           | DA          | CC     | SpecialCase |
      | echo     | Battery | EACH [EA] | 0          | NA | NA | NA | NA     | Common/Shared | CZ52 - Brno | Custom | NA          |

  @DesignTeam
  Scenario Outline: Create <Object> and check Design Team dropdown options
    Given the <UserType> user login is successful
    When user tries to create object of type <Object>
    Then edit item page is to be reached to verify Design Team combination
    Examples:
      | UserType | Object    |
      | echo     | Items     |
      | echo     | Documents |
