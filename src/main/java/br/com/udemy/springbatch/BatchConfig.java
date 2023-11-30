package br.com.udemy.springbatch;

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

    @Bean
    public Step processamentoTrindaDias(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("leitura processamentoTrindaDias", jobRepository)
                .<Integer, String>chunk(10, transactionManager)
                .reader(contaAteDezReader())
                .processor(parOuImparProcessor())
                .writer(imprimeWriter())
                .build();
    }

    // Leitor
    public IteratorItemReader<Integer> contaAteDezReader() {
        List<Integer> numeroDeUmAteDez = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new IteratorItemReader<Integer>(numeroDeUmAteDez);
    }

    // Processador
    public FunctionItemProcessor<Integer, String> parOuImparProcessor() {
        return new FunctionItemProcessor<Integer, String>(item -> item % 2 == 0 ? String.format("Item %s é Par", item) :
                String.format("Item %s é impar", item));
    }

    // Escritor
    public ItemWriter<String> imprimeWriter(){
        return itens -> itens.forEach(System.out::println);
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
