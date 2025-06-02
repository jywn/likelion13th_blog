package likelion13th.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class OpenAIConfigTests {

    @Test
    void envVar() {
        System.out.println("KEY: " + System.getenv("OPENAI_API_KEY"));
    }
}
