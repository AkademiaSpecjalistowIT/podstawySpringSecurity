package pl.akademiaspecjalistowit.podstawyspringsecurity.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.SupplierClientRegistrationRepository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import pl.akademiaspecjalistowit.podstawyspringsecurity.user.OAuth2LoginSuccessHandler;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final OAuth2LoginSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/books").permitAll()
                .requestMatchers("/students/addButton").hasRole("ADMIN")
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login()
            .successHandler(successHandler);

        http.formLogin();
        http.csrf().disable();
        http.headers().frameOptions().disable();

        return http.build();
    }
}
