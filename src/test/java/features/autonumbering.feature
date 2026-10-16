Feature: To enable or disable auto-numbering

  @autoNumbering
  Scenario Outline: Configure AutoNumbering for <Part>
    Given the administrator user login is successful
    When reach the Manage Item Classes page
    Then make necessary changes to <Part> to Item Number Generation Method to Rule Generated
    #INGM is to either 'Rule Generated' (after migration) or 'User Defined' (before migration)
    Examples:
      | Part                     |
      | ERCV Document            |
      | ERCV Document            |
      | NPI Document             |
      | Procedure                |
      | Specification            |
      | Antenna                  |
      | Battery                  |
      | Cable Assembly           |
      | Capacitor                |
      | Connector                |
      | Crystal/Oscillator       |
      | Diode                    |
      | Fan                      |
      | Final Assembly-Device    |
      | Fuse                     |
      | Hardware                 |
      | Heat Sink                |
      | IC Programmed            |
      | Inductor                 |
      | Integrated Circuit       |
      | Label                    |
      | Magnetic Component       |
      | Material-Substance       |
      | Mechanical Part          |
      | Media                    |
      | Mfg Jig/Fixture/Tool     |
      | Mfg Supplies             |
      | Microphone               |
      | Module COTS              |
      | Motor                    |
      | Optical                  |
      | Optoelectronic           |
      | PCB (unpopulated)        |
      | Packaging                |
      | Polyswitch Resettable    |
      | Power Supply             |
      | Printed Circuit Assembly |
      | Printed Literature       |
      | Product                  |
      | RF Component             |
      | Relay-Contactor          |
      | Resistor                 |
      | Resonator                |
      | Sensor                   |
      | Software File            |
      | Solenoid                 |
      | Sounder                  |
      | Subassembly              |
      | Switch                   |
      | Thermistor               |
      | Thyristor                |
      | Transducer               |
      | Transformer-Winding      |
      | Transistor               |
      | Unclassified             |
      | Varistor-MOV             |
      | Wire/Conductor           |
      | Operational Documents    |
      | System Documents         |
      | Quality Records          |