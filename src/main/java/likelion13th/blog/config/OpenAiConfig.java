package likelion13th.blog.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {

    private String openaikey = System.getenv("OPENAI_API_KEY");

    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(openaikey);
    }


}
