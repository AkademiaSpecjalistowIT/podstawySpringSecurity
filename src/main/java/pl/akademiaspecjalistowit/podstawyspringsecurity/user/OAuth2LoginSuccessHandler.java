package pl.akademiaspecjalistowit.podstawyspringsecurity.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JdbcUserDetailsManager userDetailsManager;

    public OAuth2LoginSuccessHandler(JdbcUserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Pobierz dane użytkownika z GitHub
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauthUser.getAttributes();
        String username = (String) attributes.get("login"); // Dla GitHub 'login' jest nazwą użytkownika
        String email = (String) attributes.get("email");

        if (!userDetailsManager.userExists(username)) {
            String password = "{noop}oauth2user";

            userDetailsManager.createUser(
                org.springframework.security.core.userdetails.User.withUsername(username)
                    .password(password)
                    .roles("USER")
                    .build()
            );
        }

        response.sendRedirect("/home");
    }
}
