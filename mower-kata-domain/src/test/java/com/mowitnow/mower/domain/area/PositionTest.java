package com.mowitnow.mower.domain.area;

import com.mowitnow.mower.domain.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PositionTest {


    private static Stream<Arguments> position() {
        return Stream.of(
                arguments("5 5 N", 5, 5, "N"),
                arguments("6 6 E", 6, 6, "E"),
                arguments("7 6 S", 7, 6, "S")
        );
    }


    @ParameterizedTest
    @MethodSource("position")
    public void should_parse_a_valid_position(final String line, final int x, final int y,
                               String orientation) {
        Position position = Position.from(line);
        Position expectedPosition = Position.builder()
                .orientation(Orientation.findOrientationFromCode(orientation))
                .coordinates(Coordinates.builder().x(x).y(y).build())
                .build();
        assertEquals(position, expectedPosition);
    }

    @Test()
    public void should_throws_ParserException_then_invalid_position() {
        Assertions.assertThrows(ParserException.class,() -> Position.from("invalid") ) ;
    }

}