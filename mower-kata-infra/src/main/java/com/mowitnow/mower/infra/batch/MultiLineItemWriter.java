package com.mowitnow.mower.infra.batch;

import com.mowitnow.mower.domain.DomainEntity;
import com.mowitnow.mower.domain.ParserException;
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


import java.io.*;

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
        try {
            firstLine = readFirstLine();
        } catch (IOException e) {
            log.error(" Parser exception cannot get mower coordinates ");
            throw new ParserException(e.getMessage());
        }
    }

    @Override
    public void write(Chunk<? extends DomainEntity> chunk) {

        if (!isEmpty(chunk.getItems())) {
            Command command = new Command(
                    from(chunk.getItems().get(0).toString()),
                    Coordinates.from(firstLine),
                    Movement.from(chunk.getItems().get(1).toString()));

            Position position = processCommand.processCommand (command);
            try {
                writeToOUtPutFile(Position.toLine(position));
            } catch (IOException e) {
                log.error(" cannot write a mower final position to output file ");
                throw new ParserException(e.getMessage());
            }
        }

    }

    public String readFirstLine() throws IOException {
        return new BufferedReader(new FileReader(inPut.getFile())).readLine();
    }

    public void writeToOUtPutFile(String toWrite) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outPut.getFilename()));
        writer.write(toWrite);
        writer.close();
    }


}