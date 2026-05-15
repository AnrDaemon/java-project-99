package hexlet.code.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import net.datafaker.Faker;

@TestConfiguration
public class TestDataConfig {

    @Bean
    public Faker faker() {
        return new Faker();
    }
}
