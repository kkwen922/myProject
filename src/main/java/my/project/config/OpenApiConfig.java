package my.project.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kevinchang
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("SpringDoc API Test")
                        .description("SpringDoc Simple Application Test")
                        .version("0.0.1"));
    }
}
