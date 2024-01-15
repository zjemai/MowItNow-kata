package com.mowitnow.mower.domain.area;

import com.mowitnow.mower.domain.DomainEntity;
import com.mowitnow.mower.domain.Entity;
import com.mowitnow.mower.domain.ParserException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;


@Data
@Entity
@Builder
@AllArgsConstructor
public class Coordinates implements DomainEntity {

    private static final String PATTERN_PELOUSE = "^\\d+ \\d+$";
    private static final String SEPARATOR = " ";


    private int x;
    private int y;

    public void addX() {
        this.x += 1;
    }

    public void minusX() {
        this.x -= 1;
    }

    public void addY() {
        this.y += 1;
    }

    public void minusY() {
        this.y -= 1;
    }

    public static Coordinates from(final String coordinates) {
        if (coordinates.matches(PATTERN_PELOUSE)) {
            List<String> pelouseSplit = asList(coordinates.split(SEPARATOR));
            return new Coordinates(Integer.parseInt(pelouseSplit.get(0)), Integer.parseInt(pelouseSplit.get(1)));
        } else {
            throw new ParserException(String.format("Error parse coordinates in this line [%s] it must be with this pattern  [two numbers between 0 and 9 separated by a space ]", coordinates));
        }
    }
}
