
Feature:  Movements

 Scenario Outline: Should move mower to final position when execute movement list
    Given the initial position <InitialPosition> and <Coordinate>
    And with the following <Movements>
    When I move the mower with the following movements
    Then the final position is <FinalPosition>
    Examples:
      | InitialPosition | Coordinate | Movements     | FinalPosition |
      | 1 2 "N"         | 5 5         | "GAGAGAGAA"  | 1 3 "N"       |
      | 3 3 "E"         | 5 5         | "AADAADADDA" | 5 1 "E"       |