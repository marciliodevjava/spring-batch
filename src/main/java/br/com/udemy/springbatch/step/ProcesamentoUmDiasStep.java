package br.com.udemy.springbatch.step;

import br.com.udemy.springbatch.tasklet.ProcessamentoUmDiaTasklet;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ProcesamentoUmDiasStep {

    @Autowired
    private ProcessamentoUmDiaTasklet tasklet;

    @Bean
    public Step processamentoUmDias(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("processamentoUmDias", jobRepository)
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    return tasklet.execute(null, null);
                }, transactionManager)
                .build();
    }
}
