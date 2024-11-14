package caodong0225.login.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger Configuration for Singularity Application
 * Provides API documentation for public and private endpoints.
 *
 * @author jyzxc
 * @since 2024-07-20
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder().group("用户模块").pathsToMatch("/user/**").build();
    }

    @Bean
    public OpenAPI customSwagger() {
        return new OpenAPI()
                .info(new Info()
                        .title("Login API")
                        .version("1.0.0")
                        .description("登陆验证的接口文档."));

    }
}
