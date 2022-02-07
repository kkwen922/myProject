package my.project.config;


import my.project.common.config.BaseSwaggerConfig;
import my.project.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文檔相關配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("my.project.modules")
                .title("APT 系統文件")
                .description("pi-team@aptg.com.tw")
                .contactName("pi-team")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
