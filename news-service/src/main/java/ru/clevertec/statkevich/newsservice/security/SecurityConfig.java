package ru.clevertec.statkevich.newsservice.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.clevertec.statkevich.newsservice.security.jwt.JwtAuthEntryPoint;
import ru.clevertec.statkevich.newsservice.security.jwt.JwtDeniedHandler;
import ru.clevertec.statkevich.newsservice.security.jwt.JwtFilter;

/**
 * Configuration class for security filter chain. Customizing properties and request authorization.
 * Also requests authorization configured in controllers by method security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthEntryPoint authEntryPoint;

    private final JwtDeniedHandler jwtDeniedHandler;

    public SecurityConfig(JwtAuthEntryPoint authEntryPoint, JwtDeniedHandler jwtDeniedHandler) {

        this.authEntryPoint = authEntryPoint;
        this.jwtDeniedHandler = jwtDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter filter) throws Exception {

        return http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(Customizer.withDefaults())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling
                                .authenticationEntryPoint(authEntryPoint)
                                .accessDeniedHandler(jwtDeniedHandler)
                )
                .authorizeHttpRequests((req) -> req
                        .requestMatchers(HttpMethod.GET, "/api/v1/news").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/comments").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
