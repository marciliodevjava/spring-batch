package br.com.udemy.springbatch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
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
public class ProcesamentoTrintaDiasStep {
    @Bean
    public Step processamentoTrindaDias(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("leitura processamentoTrindaDias", jobRepository).<Integer, String>chunk(10, transactionManager).reader(contaAteDezReader()).processor(parOuImparProcessor()).writer(imprimeWriter()).build();
    }

    // Leitor
    public IteratorItemReader<Integer> contaAteDezReader() {
        List<Integer> numeroDeUmAteDez = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        return new IteratorItemReader<Integer>(numeroDeUmAteDez);
    }

    // Processador
    public FunctionItemProcessor<Integer, String> parOuImparProcessor() {
        return new FunctionItemProcessor<Integer, String>(item -> item % 2 == 0 ? String.format("Item %s é Par", item) : String.format("Item %s é impar", item));
    }

    // Escritor
    public ItemWriter<String> imprimeWriter() {
        return itens -> itens.forEach(System.out::println);
    }
}
