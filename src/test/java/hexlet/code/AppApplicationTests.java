package hexlet.code;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import hexlet.code.config.TestDataConfig;

@SpringBootTest
@Import(TestDataConfig.class)
class AppApplicationTests {
    @Test
    void contextLoads() {
    }
}
