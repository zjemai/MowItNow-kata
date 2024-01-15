package com.mowitnow.mower.domain.command;

import com.mowitnow.mower.domain.DomainEntity;
import com.mowitnow.mower.domain.area.Coordinates;
import com.mowitnow.mower.domain.area.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class Command implements DomainEntity {

    private Position position;
    private Coordinates topRightCoordinates;
    private List<Movement> movements;

    public Position processCommandMovement() {
        movements.forEach(this::process);
        return position;
    }

    private void process(final Movement movement) {
        switch (movement) {
            case ADVANCE -> position.processAdvance(topRightCoordinates);
            case RIGHT -> position.turnToRightOrientation();
            case LEFT -> position.turnToLeftOrientation();
        }
    }
}
