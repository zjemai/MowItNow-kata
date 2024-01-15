package com.mowitnow.mower.domain.command;

import com.mowitnow.mower.domain.DomainEntity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

public enum Movement implements DomainEntity {

    ADVANCE('A'),
    RIGHT('D'),
    LEFT('G');

    private char code;

    Movement(char code) {
        this.code = code;
    }

    public static Movement findMovementFromCode(char code) {
        return stream(Movement.values())
                .filter(c -> c.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No movements found for " + code));
    }

    public static List<Movement> from(final String movements) {
        List<Movement> instructionsSplit = new ArrayList<>();
        for (char movement : movements.toCharArray()) {
            instructionsSplit.add(Movement.findMovementFromCode(movement));
        }
        return instructionsSplit;
    }
}
