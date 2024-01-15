package com.mowitnow.mower.infra.config;

import com.mowitnow.mower.domain.DomainEntity;
import com.mowitnow.mower.domain.api.ProcessCommand;
import com.mowitnow.mower.infra.batch.MultiLineItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration {


    @Bean(name = "instructionProcessingJob")
    public Job job(JobRepository jobRepository, @Qualifier("instructionProcessingStep") Step instructionProcessingStep) {
        return new JobBuilder("instructionProcessingJob", jobRepository).preventRestart().start(instructionProcessingStep).build();
    }

    @Bean
    protected Step instructionProcessingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                             ItemReader<String> reader, ItemWriter<DomainEntity> writer) {
        return new StepBuilder("instructionProcessingStep", jobRepository).<String, DomainEntity> chunk(2, transactionManager)
                .reader(reader).writer(writer).build();
    }

    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager getTransactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean(name = "jobRepository")
    public JobRepository getJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource());
        factory.setTransactionManager(getTransactionManager());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean(name = "jobLauncher")
    public JobLauncher getJobLauncher() throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }


    @Bean
    public FlatFileItemReader<String> itemReader(@Value("${input}") Resource resource) {

        FlatFileItemReader<String> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("Mower-Reader");
        flatFileItemReader.setLineMapper(new DefaultLineMapper<>());
        flatFileItemReader.setLinesToSkip(1);
        return flatFileItemReader;
    }

    @Bean
    public ItemWriter<DomainEntity> itemWriter(@Value("${output}") Resource inPut, @Value("${output}") Resource output ,
                                               ProcessCommand processCommand) {
        return new MultiLineItemWriter(processCommand, inPut, output);
    }


}
