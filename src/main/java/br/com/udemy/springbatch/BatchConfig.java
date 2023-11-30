package br.com.udemy.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

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

    @Bean
    public Step processamentoTrindaDias(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("processamentoTrindaDias", jobRepository)
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    return getprocessamentoTrindaDias();
                }, transactionManager)
                .build();
    }

    @Bean
    public Step processamentoQuinzeDias(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("processamentoQuinzeDias", jobRepository)
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    return getprocessamentoQuinzeDias();
                }, transactionManager)
                .build();
    }

    @Bean
    public Step processamentoUmDias(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("processamentoUmDias", jobRepository)
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    return getprocessamentoUmDias();
                }, transactionManager)
                .build();
    }

    private static RepeatStatus getprocessamentoTrindaDias() {
        System.out.println("Inicio processamento Trinda Dias!");
        System.out.println("Fim processamento Trinda Dias");
        return RepeatStatus.FINISHED;
    }

    private static RepeatStatus getprocessamentoQuinzeDias() {
        System.out.println("Inicio processamento Quinze Dias!");
        System.out.println("Fim processamento Quinze Dias");
        return RepeatStatus.FINISHED;
    }

    private static RepeatStatus getprocessamentoUmDias() {
        System.out.println("Inicio processamento Um Dia!");
        System.out.println("Fim processamento Um Dia.");
        return RepeatStatus.FINISHED;
    }
}
