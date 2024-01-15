package com.mowitnow.mower.domain;

import com.mowitnow.mower.domain.api.ProcessCommand;
import com.mowitnow.mower.domain.area.Position;
import com.mowitnow.mower.domain.command.Command;

public class CommandProcessing implements ProcessCommand {


    @Override
    public Position processCommand(Command command) {
        return command.processCommandMovement();
    }

}
