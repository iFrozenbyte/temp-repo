package sorokin.dev.springcore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Scanner;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("sorokin.dev.springcore")
public class AppConfig {

    @Bean
    Scanner scanner() {
        return new Scanner(System.in);
    }
}