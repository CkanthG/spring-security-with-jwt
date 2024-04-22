package com.sree.jwt.api.config;

import com.sree.jwt.api.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class is used to configure security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomUserDetailsService service;

    @Autowired
    private AuthenticationConfiguration authConfiguration;

    /**
     * This method is to configure custom authentication details.
     * @param auth
     * @throws Exception
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service);
    }

    /**
     * This method gives password encoder
     * @return password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * This method gives an authentication manager bean object.
     * @return  authentication manager
     * @throws Exception
     */
    @Bean(name = "authenticationManager")
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    /**
     * This method is used for disable csrf and allow required endpoints.
     * @param http
     * @return security filter chain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(new Customizer<CsrfConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
                        httpSecurityCsrfConfigurer.disable();
                    }
                })
                .authorizeRequests().requestMatchers("/jwt/authenticate").permitAll()
                .requestMatchers("/jwt").authenticated();
        return http.build();
    }
}
