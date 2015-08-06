package ua.kiev.test.testwebmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import ua.kiev.test.testwebmvc.config.security.SecurityConfig;

/**
 * Created by lutay.d on 03.08.2015.
 */
@Configuration
@ComponentScan({"ua.kiev.test.testwebmvc.service"})
@Import({JpaConfig.class, SecurityConfig.class})
public class AppConfig {

    @Bean
    MappingJackson2HttpMessageConverter jacksonConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
}
