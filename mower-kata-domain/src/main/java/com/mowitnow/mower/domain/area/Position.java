package com.mowitnow.mower.domain.area;

import com.mowitnow.mower.domain.DomainEntity;
import com.mowitnow.mower.domain.Entity;
import com.mowitnow.mower.domain.ParserException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode
public class Position implements DomainEntity {

    private static final String PATTERN_POSITION = "^\\d+ \\d+ [N|E|W|S]$";
    private static final String SEPARATOR = " ";


    private Coordinates coordinates;
    private Orientation orientation;


    public Position(Coordinates coordinates, Orientation orientation) {
        this.coordinates = coordinates;
        this.orientation = orientation;
    }

    public void turnToRightOrientation() {
        orientation = orientation.getRightOrientation();
    }

    public void turnToLeftOrientation() {
        orientation = orientation.getLeftOrientation();
    }

    public void processAdvance(Coordinates coordinates) {
        switch (orientation) {
            case NORTH:
                if (this.coordinates.getY() < coordinates.getY()) {
                    this.coordinates.addY();
                }
                break;
            case EAST:
                if (this.coordinates.getX() < coordinates.getX()) {
                    this.coordinates.addX();
                }
                break;
            case SOUTH:
                if (this.coordinates.getY() > 0) {
                    this.coordinates.minusY();
                }
                break;
            case WEST:
                if (this.coordinates.getX() > 0) {
                    this.coordinates.minusX();
                }
                break;
        }
    }

    public static Position from(final String position) {

        if (!position.matches(PATTERN_POSITION)) {
            throw new ParserException(String.format("Error parse position in this line [%s] it must be with this pattern [two numbers between 0 and 9 separated by a space , a space and letter in {N|E|W|S}]", position));
        }
        List<String> positionSplit = Arrays.asList(position.split(SEPARATOR));
        Coordinates p = new Coordinates(Integer.parseInt(positionSplit.get(0)), Integer.parseInt(positionSplit.get(1)));
        Orientation orientation = Orientation.findOrientationFromCode(positionSplit.get(2));
        return Position.builder()
                .orientation(orientation)
                .coordinates(p)
                .build();
    }

    public static String toLine(Position position) {
        return MessageFormat.format("{0} {1} {2}",
                position.getCoordinates().getX(),
                position.getCoordinates().getX(),
                position.getOrientation().getCode());
    }

    public String toString() {
        return this.coordinates.getX() + " " +
                this.coordinates.getY() + " " +
                orientation.getCode();
    }
}
