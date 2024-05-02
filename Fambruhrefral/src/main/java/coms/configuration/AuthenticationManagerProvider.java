package coms.configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManagerProvider {
	private final AuthenticationManager authenticationManager;

    public AuthenticationManagerProvider(AuthenticationConfiguration authenticationConfiguration) {
        try {
            this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException("Failed to obtain AuthenticationManager", e);
        }
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }
}
