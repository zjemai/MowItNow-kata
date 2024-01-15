package com.mowitnow.mower.domain.command;

import com.mowitnow.mower.domain.area.Coordinates;
import com.mowitnow.mower.domain.area.Orientation;
import com.mowitnow.mower.domain.area.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class CommandTest {


    private static Stream<Arguments> command() {
        return Stream.of(
                //should Rotate To EAST When Tondeuse In NORTH And Instruction Is Right
                arguments(2, 2, 2, 2, Collections.singletonList(Movement.RIGHT), Orientation.NORTH, Orientation.EAST, new Coordinates(5, 5)),
                //should Rotate To WEST When Tondeuse In SOUTH And Movement Is Right
                arguments(2, 2, 2, 2, Collections.singletonList(Movement.RIGHT), Orientation.SOUTH, Orientation.WEST, new Coordinates(5, 5)),
                //should Rotate To NORTH When Tondeuse In WEST And Movement Is Right
                arguments(2, 2, 2, 2, Collections.singletonList(Movement.RIGHT), Orientation.WEST, Orientation.NORTH, new Coordinates(5, 5)),
                // should Rotate To WEST When Tondeuse In NORTH And Movement Is Left
                arguments(2, 2, 2, 2, Collections.singletonList(Movement.LEFT), Orientation.NORTH, Orientation.WEST, new Coordinates(5, 5)),
                // should Rotate To NORTH When Tondeuse In EAST And Movement Is Left
                arguments(2, 2, 2, 2, Collections.singletonList(Movement.LEFT), Orientation.EAST, Orientation.NORTH, new Coordinates(5, 5)),
                //should Rotate To EAST When Tondeuse In SOUTH And Movement Is Left
                arguments(2, 2, 2, 2, Collections.singletonList(Movement.LEFT), Orientation.SOUTH, Orientation.EAST, new Coordinates(5, 5)),
                //should Rotate To SOUTH When Tondeuse In WEST And Movement Is Left
                arguments(2, 2, 2, 2, Collections.singletonList(Movement.LEFT), Orientation.WEST, Orientation.SOUTH, new Coordinates(5, 5)),
                //should Move To Next Y Position When Movement Is Advance And Orientation Is NORTH
                arguments(2, 2, 2, 3, Collections.singletonList(Movement.ADVANCE), Orientation.NORTH, Orientation.NORTH, new Coordinates(5, 5)),
                //should Move To Next X Position When Movement Is Advance And Orientation Is EAST
                arguments(2, 2, 3, 2, Collections.singletonList(Movement.ADVANCE), Orientation.EAST, Orientation.EAST, new Coordinates(5, 5)),
                //should Move To Back Y Position When Movement Is Advance And Orientation Is SOUTH
                arguments(2, 2, 2, 1, Collections.singletonList(Movement.ADVANCE), Orientation.SOUTH, Orientation.SOUTH, new Coordinates(5, 5)),
                //should Move To Back X Position When Movement Is Advance And Orientation Is WEST
                arguments(2, 2, 1, 2, Collections.singletonList(Movement.ADVANCE), Orientation.WEST, Orientation.WEST, new Coordinates(5, 5)),
                //should Stay At Same Position When X is Zero And Movement Is Advance And Orientation WEST
                arguments(0, 2, 0, 2, Collections.singletonList(Movement.ADVANCE), Orientation.WEST, Orientation.WEST, new Coordinates(5, 5)),
                //should Stay At Same Position When Y is Zero And Movement Is Advance And Orientation SOUTH
                arguments(2, 0, 2, 0, Collections.singletonList(Movement.ADVANCE), Orientation.SOUTH, Orientation.SOUTH, new Coordinates(5, 5)),
                //should Stay At Same Place When Tondeuse In Garden LimitX
                arguments(5, 5, 5, 5, Collections.singletonList(Movement.ADVANCE), Orientation.EAST, Orientation.EAST, new Coordinates(5, 5)),
                //should Stay At Same Place When Tondeuse In Garden LimitY
                arguments(5, 5, 5, 5, Collections.singletonList(Movement.ADVANCE), Orientation.NORTH, Orientation.NORTH, new Coordinates(5, 5)),
                //should Move To 1 3 N When Tondeuse Position Is 1 2 N And Movements GAGAGAGAA
                arguments(1, 2, 1, 3, List.of(Movement.LEFT, Movement.ADVANCE,
                        Movement.LEFT, Movement.ADVANCE, Movement.LEFT, Movement.ADVANCE,
                        Movement.LEFT, Movement.ADVANCE, Movement.ADVANCE), Orientation.NORTH, Orientation.NORTH, new Coordinates(5, 5)),
                //should Move To 5 1 E When Tondeuse Position Is 3 3 E And Movements AADAADADDA
                arguments(3, 3, 5, 1, List.of(Movement.ADVANCE, Movement.ADVANCE,
                        Movement.RIGHT, Movement.ADVANCE, Movement.ADVANCE, Movement.RIGHT,
                        Movement.ADVANCE, Movement.RIGHT, Movement.RIGHT, Movement.ADVANCE), Orientation.EAST, Orientation.EAST, new Coordinates(5, 5))


        );
    }


    @ParameterizedTest
    @MethodSource("command")
    void processTondeuseInstructionsTest(final int x, final int y,
                                         final int expectedX, final int expectedY,
                                         List<Movement> instructions, Orientation orientation,
                                         Orientation expectedOrientation, Coordinates coordinates) {
        Command tondeuse = Command.builder()
                .position(new Position(new Coordinates(x, y), orientation))
                .movements(instructions)
                .topRightCoordinates(coordinates).build();
        Position position = tondeuse.processCommandMovement();
        Position expectedPosition = new Position(new Coordinates(expectedX, expectedY), expectedOrientation);
        Assertions.assertEquals(position, expectedPosition);
    }

}