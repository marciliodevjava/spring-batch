package br.com.udemy.springbatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropsConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer(){
        PropertySourcesPlaceholderConfigurer config = new PropertySourcesPlaceholderConfigurer();
        config.setLocation(new ClassPathResource("config-properties.properties"));
        return config;
    }
}
