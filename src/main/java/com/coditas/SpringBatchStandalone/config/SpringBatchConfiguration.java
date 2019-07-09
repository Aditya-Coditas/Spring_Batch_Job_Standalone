package com.coditas.SpringBatchStandalone.config;

import com.coditas.SpringBatchStandalone.model.Information;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration
{

    @Bean
    public Job job(JobBuilderFactory jobFactory, StepBuilderFactory stepFactory, ItemReader<Information> itemReader,ItemProcessor<Information,Information> itemProcessor,ItemWriter<Information> itemWriter
            ){
        Step step = stepFactory.get("File-Load").<Information,Information>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
        Job job=jobFactory.get("Load-User").incrementer(new RunIdIncrementer()).start(step).build();
        return job;
    }

    @Bean
    public FlatFileItemReader<Information> fileItemReader(@Value("${input}") Resource resource)
    {
        FlatFileItemReader<Information> flatFileItemReader=new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("User Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    public LineMapper<Information> lineMapper() {
        DefaultLineMapper<Information> defaultLineMapper=new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer(); //seprate tokens on delimeter
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"First Name","Last Name"});
        defaultLineMapper.setLineTokenizer(lineTokenizer);

        BeanWrapperFieldSetMapper<Information> fieldSetMapper=new BeanWrapperFieldSetMapper<>(); //set token to the User
        fieldSetMapper.setTargetType(Information.class);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
