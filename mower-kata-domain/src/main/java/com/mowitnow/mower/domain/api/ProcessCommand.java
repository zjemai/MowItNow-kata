package com.mowitnow.mower.domain.api;

import com.mowitnow.mower.domain.area.Position;
import com.mowitnow.mower.domain.command.Command;

@FunctionalInterface
public interface ProcessCommand {

    /**
     * Process a command by mower
     *
     * @param command
     * @return the final position of each mower
     */
    Position processCommand(Command command);
}
