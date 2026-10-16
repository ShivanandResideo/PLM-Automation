Feature: Test to Create Items

#  runtime: 1 hour 40 mins

  @Delta @All
  Scenario Outline: Create <Item> and check attributes for <Item> with PGM <PartGenMtd>
    Given the <UserType> user login is successful
    When user tries to create <Item> item with PUOM as <PUOM> partGenMtd as <PartGenMtd> existing family as <EF> Option as <FO> version as <FV> Legacy <Legacy> LOB as <LOB> DA as <DA> CC as <CC> for <SpecialCase>
    Then edit item page is to be reached with all relevant attributes
    Examples:
      | UserType | Item                     | PUOM                   | PartGenMtd | EF | FO | FV | Legacy | LOB               | DA                      | CC                | SpecialCase | 06 March | 08 march |
      | delta    | Battery                  | EACH [EA]              | 0          | NA | NA | NA | NA     | Common/Shared     | CZ52 - Brno             | Custom            | NA          | pass     | pass     |
      | delta    | Capacitor                | GALLON [GAL]           | 0          | NA | NA | NA | NA     | Safety & Security | FR4A - Strasbourg       | COTS              | NA          | pass     | pass     |
      | delta    | Crystal/Oscillator       | GRAM [G]               | 0          | NA | NA | NA | NA     | Water             | GE23 - Mosbach          | COTS              | NA          | pass     | pass     |
      | delta    | Diode                    | KILOGRAM [KG]          | 0          | NA | NA | NA | NA     | Air               | GE4P - Lotte            | COTS              | NA          | pass     | pass     |
      | delta    | Fuse                     | MILLIMETRE [MM]        | 0          | NA | NA | NA | NA     | Air               | IT21 - Monza            | COTS              | NA          | pass     | pass     |
      | delta    | Hardware                 | OUNCE [OZ]             | 0          | NA | NA | NA | NA     | Air               | MN10 - Golden Valley    | COTS              | NA          | pass     | pass     |
      | delta    | Heat Sink                | POUND [LB]             | 0          | NA | NA | NA | NA     | Air               | MX0F - Chihuahua        | COTS              | NA          | pass     | pass     |
      | delta    | Inductor                 | ROLL [ROL]             | 0          | NA | NA | NA | NA     | Air               | NY80 - Melville         | COTS              | NA          | pass     | pass     |
      | delta    | Integrated Circuit       | TUBE [TU]              | 0          | NA | NA | NA | NA     | Air               | UK07 - Newhouse         | COTS              | NA          | pass     | pass     |
      | delta    | Magnetic Component       | REF                    | 0          | NA | NA | NA | NA     | Air               | XPDM Arena              | COTS              | NA          | pass     | pass     |
      | delta    | Material-Substance       | REF                    | 0          | NA | NA | NA | NA     | Air               | XPDM Enovia             | COTS              | NA          | pass     | pass     |
      | delta    | Media                    | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Mfg Supplies             | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Microphone               | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Module COTS              | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Optoelectronic           | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Polyswitch Resettable    | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Product                  | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | fail     | fail     |
  # -> product number field to enter manually is missing |
      | delta    | Relay-Contactor          | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Resistor                 | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Resonator                | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | RF Component             | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Sensor                   | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Solenoid                 | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Sounder                  | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Switch                   | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Thermistor               | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Thyristor                | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Transducer               | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Transistor               | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Varistor-MOV             | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Antenna                  | CUBIC CENTIMETER [CCM] | 1          | NA | NA | NA | NA     | Air               | CH9Y - Shanghai         | COTS              | NA          | pass     | pass     |
      | delta    | Cable Assembly           | FOOT [FT]              | 1          | NA | NA | NA | NA     | Energy            | FR1J - Sophia Antipolis | Source Controlled | NA          | pass     | pass     |
      | delta    | Fan                      | METRE [M]              | 1          | NA | NA | NA | NA     | Air               | HU03 - Nagykanizsa      | COTS              | NA          | pass     | pass     |
      | delta    | Final Assembly-Device    | MILLILITRE [ML]        | 1          | NA | NA | NA | NA     | Air               | ILA1 - Aurora           | COTS              | NA          | pass     | pass     |
      | delta    | IC Programmed            | REF                    | 1          | NA | NA | NA | NA     | Air               | NL05 - Emmen            | COTS              | NA          | pass     | pass     |
      | delta    | Label                    | REF                    | 1          | NA | NA | NA | NA     | Air               | XPDM Agile              | COTS              | NA          | pass     | pass     |
      | delta    | Mechanical Part          | REF                    | 1          | NA | NA | NA | NA     | Air               | XPDM SAP PDM            | COTS              | NA          | pass     | pass     |
      | delta    | Mfg Jig/Fixture/Tool     | REF                    | 1          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Motor                    | REF                    | 1          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Optical                  | REF                    | 1          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Packaging                | REF                    | 1          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | PCB (unpopulated)        | REF                    | 1          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Power Supply             | REF                    | 1          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Printed Circuit Assembly | REF                    | 1          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Printed Literature       | REF                    | 1          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Software File            | REF                    | 1          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Subassembly              | REF                    | 1          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Transformer-Winding      | REF                    | 1          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Wire/Conductor           | REF                    | 1          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Antenna                  | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Cable Assembly           | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Fan                      | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Final Assembly-Device    | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | IC Programmed            | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Label                    | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Mechanical Part          | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Mfg Jig/Fixture/Tool     | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Motor                    | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Optical                  | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Packaging                | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | PCB (unpopulated)        | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Power Supply             | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Printed Circuit Assembly | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Printed Literature       | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Software File            | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Subassembly              | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Transformer-Winding      | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Wire/Conductor           | REF                    | 2          | Y  | Y  | Y  | NA     | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Antenna                  | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Cable Assembly           | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Fan                      | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Final Assembly-Device    | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | IC Programmed            | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Label                    | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Mechanical Part          | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Mfg Jig/Fixture/Tool     | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Motor                    | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Optical                  | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Packaging                | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | PCB (unpopulated)        | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Power Supply             | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Printed Circuit Assembly | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Printed Literature       | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Software File            | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Subassembly              | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Transformer-Winding      | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Wire/Conductor           | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Transformer-Winding      | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Wire/Conductor           | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Transformer-Winding      | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Wire/Conductor           | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Transformer-Winding      | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Wire/Conductor           | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Transformer-Winding      | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |
      | delta    | Wire/Conductor           | REF                    | 3          | NA | NA | NA | Y      | Air               | CZ52 - Brno             | COTS              | NA          | pass     | pass     |

  @Delta @All @Attachments @Item_Attachments
  Scenario Outline: Create items and check attachments tab for <SpecialCase>
    Given the <User> user login is successful
    When user tries to create <Item> item with PUOM as <PUOM> partGenMtd as <PartGenMtd> existing family as <EF> Option as <FO> version as <FV> Legacy <Legacy> LOB as <LOB> DA as <DA> CC as <CC> for <SpecialCase>
    And makes necessary changes as per the case <SpecialCase> for Item
    Then attachment page is to be reached with all relevant functionalities
    Examples:
      | User  | Item    | PUOM | PartGenMtd | EF | FO | FV | Legacy | LOB | DA          | CC   | SpecialCase          | 06 march | 08 March |
      | delta | Antenna | REF  | 1          | NA | NA | NA | NA     | Air | CZ52 - Brno | COTS | Attach Zip           | pass     | pass     |
      | delta | Battery | REF  | 0          | NA | NA | NA | NA     | Air | CZ52 - Brno | COTS | Attach File Document | pass     | pass     |
#      | delta | Optoelectronic | REF  | 0          | NA | NA | NA | NA     | Air | CZ52 - Brno | COTS | Attach Repository Manufacturing | # disabled after request from Vijay
      | delta | Resistor    | REF  | 0          | NA | NA | NA | NA     | Air | CZ52 - Brno | COTS | Attach Text Miscellaneous | pass     | pass     |
      | delta | Subassembly | REF  | 1          | NA | NA | NA | NA     | Air | CZ52 - Brno | COTS | Attach URL Source         | pass     | pass |

  @Delta @All
  Scenario Outline: Create 3 objects and check structure tab
    Given the <User> user login is successful
    When user tries to create <INumber> items and <DNumber> documents for AffectedObjects
    Then all relevant <Functionality> functionalities are working as expected for Items
#    And The changes made in the structure of child objects are reflected in the edit pages of the child items
#    Need to check if the above step is required -> not required
    Examples:
      | User  | INumber | DNumber | Functionality | 06 March | 08 March | 17 June |
      | delta | 2       | 1       | Structure Tab | pass     | pass     | pass    |
#      | delta | 2       | 1       | drag and drop | # test manually
#       second case not working need to test manually at the moment

  @Delta @All
  Scenario Outline: Create an item and check Relationship and Team tab
    Given the <User> user login is successful
    When user tries to create <Item> item with PUOM as <PUOM> partGenMtd as <PartGenMtd> existing family as <EF> Option as <FO> version as <FV> Legacy <Legacy> LOB as <LOB> DA as <DA> CC as <CC> for <SpecialCase>
    And navigate to <Tab> tab
    Then <Tab> tab is to be reached with all relevant attributes
    Examples:
      | User  | Item       | PUOM | PartGenMtd | EF | FO | FV | Legacy | LOB | DA          | CC   | SpecialCase | Tab          | 06 March | 08 march |
      | delta | Transducer | REF  | 0          | NA | NA | NA | NA     | Air | CZ52 - Brno | COTS | NA          | Relationship | pass     | pass     |
#      | delta | Transducer               | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          | Team          |

#   already covered above
#  @Delta @Now
#  Scenario Outline: Create an item and check Team tab
#    Given the <User> user login is successful
#    When user tries to create <Item> item with PUOM as <PUOM> partGenMtd as <PartGenMtd> existing family as <EF> Option as <FO> version as <FV> Legacy <Legacy> LOB as <LOB> DA as <DA> CC as <CC> for <SpecialCase>
#    And navigate to Team tab
#    Then Team tab is to be reached with all relevant attributes
#    Examples:
#      | User   | Item                     | PUOM                   | PartGenMtd | EF | FO | FV | Legacy | LOB               | DA                      | CC                | SpecialCase |
#      | 301917 | Transducer               | REF                    | 0          | NA | NA | NA | NA     | Air               | CZ52 - Brno             | COTS              | NA          |

#     Ctrl / will comment and uncomment
#    | alpha | Antenna |
#    | beta  | Battery |
#    # Add all 55 types of items

# ctrl Alt I and Ctrl Atl L for formatting
