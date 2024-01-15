package com.mowitnow.mower.domain.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MovementTest {


    private static Stream<Arguments> instructions() {
        return Stream.of(
                arguments("ADDG", List.of(Movement.ADVANCE, Movement.RIGHT, Movement.RIGHT, Movement.LEFT)),
                arguments("ADDGAAG", List.of(Movement.ADVANCE, Movement.RIGHT, Movement.RIGHT, Movement.LEFT, Movement.ADVANCE, Movement.ADVANCE, Movement.LEFT)),
                arguments("ADDGAAGAA", List.of(Movement.ADVANCE, Movement.RIGHT, Movement.RIGHT, Movement.LEFT, Movement.ADVANCE, Movement.ADVANCE, Movement.LEFT, Movement.ADVANCE, Movement.ADVANCE))
        );
    }

    @ParameterizedTest
    @MethodSource("instructions")
    public void instructionsParser(final String line, List<Movement> expectedInstructions) {
        List<Movement> instructions = Movement.from(line);
        assertEquals(instructions, expectedInstructions);
    }

    @Test()
    public void should_throws_IllegalArgumentException_then_invalid_movement() {
        Assertions.assertThrows(IllegalArgumentException.class,() -> Movement.findMovementFromCode('S') ) ;
    }
}