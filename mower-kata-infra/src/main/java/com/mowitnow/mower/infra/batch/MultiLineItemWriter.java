package com.mowitnow.mower.infra.batch;

import com.mowitnow.mower.domain.DomainEntity;
import com.mowitnow.mower.domain.api.ProcessCommand;
import com.mowitnow.mower.domain.area.Coordinates;
import com.mowitnow.mower.domain.area.Position;
import com.mowitnow.mower.domain.command.Command;
import com.mowitnow.mower.domain.command.Movement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.Resource;


import static com.mowitnow.mower.domain.area.Position.from;
import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
public class MultiLineItemWriter implements ItemWriter<DomainEntity>, StepExecutionListener {


    private static String firstLine;
    private Resource inPut;
    private Resource outPut;
    private ProcessCommand processCommand;

    public MultiLineItemWriter(ProcessCommand processCommand, Resource inPut, Resource outPut) {
        this.inPut = inPut;
        this.outPut = outPut;
        this.processCommand = processCommand;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.debug("Read the  first line contains the coordinates of area ");
        // TODO to read From file
        firstLine = "5 5";
    }

    @Override
    public void write(Chunk<? extends DomainEntity> chunk) {

        if (!isEmpty(chunk.getItems())) {
            Command command = new Command(
                    from(chunk.getItems().get(0).toString()),
                    Coordinates.from(firstLine),
                    Movement.from(chunk.getItems().get(1).toString()));

            Position position = processCommand.processCommand (command);
            // TODO to be written in the out resource
            String line = Position.toLine(position);
        }

    }
}