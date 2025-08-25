package seonjiwon.ticketsync.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swagger() {
        Info info = new io.swagger.v3.oas.models.info.Info()
                .title("Ticker Sync")
                .description("티켓팅 서비스").version("0.0.1");

        return new OpenAPI()
                .info(info);
    }
}
