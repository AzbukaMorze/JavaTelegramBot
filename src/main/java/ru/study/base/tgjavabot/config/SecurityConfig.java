package ru.study.base.tgjavabot.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.study.base.tgjavabot.model.UserAuthority;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                .requestMatchers("/registration", "/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/jokes").hasAuthority(UserAuthority.MANAGE_JOKES.getAuthority())
                                .requestMatchers(HttpMethod.GET, "/jokes/**").hasAuthority(UserAuthority.PLACE_JOKES.getAuthority())
                                .requestMatchers(HttpMethod.PUT, "/jokes/").hasAuthority(UserAuthority.MANAGE_JOKES.getAuthority())
                                .requestMatchers(HttpMethod.DELETE, "/jokes/").hasAuthority(UserAuthority.MANAGE_JOKES.getAuthority())
                                .anyRequest().hasAuthority(UserAuthority.ALl.getAuthority()))
                                .formLogin(Customizer.withDefaults())
                                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
