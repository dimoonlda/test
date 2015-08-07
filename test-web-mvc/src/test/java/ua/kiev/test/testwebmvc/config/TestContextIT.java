package ua.kiev.test.testwebmvc.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import ua.kiev.test.testwebmvc.config.security.CustomUserDetailsService;
import ua.kiev.test.testwebmvc.service.UserService;
import ua.kiev.test.testwebmvc.utils.TokenUtils;
import ua.kiev.test.testwebmvc.utils.TokenUtilsImpl;

/**
 * Created by lutay.d on 07.08.2015.
 */
@Configuration
@ComponentScan({"ua.kiev.test.testwebmvc.service"})
@Import({TestJpaConfig.class})
public class TestContextIT {

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(new CustomUserDetailsService());
        return authenticationProvider;
    }

    @Bean
    public TokenUtils tokenUtils(){
        return Mockito.mock(TokenUtilsImpl.class);
    }
}
