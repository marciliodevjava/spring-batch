package br.com.udemy.springbatch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Configuration
public class BatchConfig {

    @Bean
    public Job processamentoFimVirgencia(JobRepository jobRepository,
                                         Step processamentoTrindaDias,
                                         Step processamentoQuinzeDias,
                                         Step processamentoUmDias) {
        return new JobBuilder("processamentoFimVirgencia", jobRepository)
                .start(processamentoTrindaDias)
                .next(processamentoQuinzeDias)
                .next(processamentoUmDias)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
