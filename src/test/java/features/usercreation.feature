Feature: Test to create user and assign read roles

#| firstName | lastName   | PID                   | email                             | Roles   |
#| Alpha     | Automation | SV_Alpha_Automation   | SV_Alpha_Automation@resideo.com   | Alpha   |
#| Beta      | Automation | SV_Beta_Automation    | SV_Beta_Automation@resideo.com    | Beta    |
#| Charlie   | Automation | SV_Charlie_Automation | SV_Charlie_Automation@resideo.com | Charlie |
#| Delta     | Automation | SV_Delta_Automation   | SV_Delta_Automation@resideo.com   | Delta   |
#| Gamma     | Automation | SV_Gamma_Automation   | SV_Gamma_Automation@resideo.com   | Gamma   |
#| Echo      | Automation | SV_Echo_Automation    | SV_Echo_Automation@resideo.com    | Echo    |

  @userCreation
  Scenario Outline: Create users and assign roles for <PID>
    Given the administrator user login is successful
    When edit a worker record with email <PID> to add department <Department> and location <Location> with code <LocationCode> with manager <Manager>
#    When create worker record for <email> with name <firstName> , <lastName> with PID <PID> in location <Location> with code <LocationCode> under department <Department> with manager <Manager>
#    And assign role <Roles> to worker record with PID <PID>
    And set password Welcome@23 for user <PID>
    Then remove all roles for <PID>
#    And inactivate user <PID>
#    Then re-assign role <Roles> to worker record with pid <PID>
#    And activate user <PID>
    Examples:
#      | firstName | lastName  | PID    | email                     | Roles                                         | Location | LocationCode |
#      | Veit        | Middelberg | 207492 | Veit.Middelberg@resideo.com | Rezi PLM View Only + Rezi PLM Approve Changes | Brno     | CZ52         |
      | PID    |
#      | 207093 |
#      | 304215 |
#      | 204604 |
#      | 267215 |
#      | 307619 |
#      | 203043 |
#      | 216256 |
#      | 278113 |
#      | 300873 |
#      | 208823 |
#      | 204284 |
#      | 239099 |
#      | 210793 |
#      | 211413 |
#      | 201879 |
#      | 235326 |
#      | 277372 |
#      | 278167 |
#      | 278352 |
#      | 278450 |
#      | 278953 |
#      | 279046 |
#      | 279176 |
#      | 279182 |
#      | 279363 |
#      | 287642 |


