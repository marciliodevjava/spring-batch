package br.com.udemy.springbatch.reader;

import br.com.udemy.springbatch.domain.Cliente;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;


@PropertySource("classpath:querry/cliente.properties")
public class JdbcCursosReaderConfig {

    @Value("${buscar.cliente}")
    private Cliente cliente;
    @Bean
    public JdbcCursorItemReader<Cliente> jdbcCursorReader(@Qualifier("appDataSource")DataSource dataSource){
        return new JdbcCursorItemReaderBuilder<Cliente>()
                .name("jdbcCursorReader")
                .dataSource(dataSource)
                .sql(String.valueOf(cliente))
                .rowMapper(new BeanPropertyRowMapper<Cliente>(Cliente.class))
                .build();
    }
}
