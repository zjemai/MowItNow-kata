package com.mowitnow.mower.domain.area;

import com.mowitnow.mower.domain.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class CoordinatesTest {

    private static Stream<Arguments> coordinates() {
        return Stream.of(
                arguments("5 5", 5, 5),
                arguments("6 6", 6, 6),
                arguments("7 6", 7, 6)
        );
    }

    @ParameterizedTest
    @MethodSource("coordinates")
    public void should_parse_a_valid_coordinates(final String line, final int x, final int y) {
        Coordinates coordinates = Coordinates.from(line);
        Coordinates expected = new Coordinates(x, y);
        Assertions.assertEquals(coordinates, (expected));
    }

    @Test()
    public void should_throws_ParserException_then_invalid_coordinates() {
        Assertions.assertThrows(ParserException.class,() -> Coordinates.from("invalid") ) ;
    }

}