package com.mowitnow.mower.infra.config;

import com.mowitnow.mower.domain.CommandProcessing;
import com.mowitnow.mower.domain.api.ProcessCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

    @Bean
    public ProcessCommand processCommand() {
        return new CommandProcessing();
    }

}
