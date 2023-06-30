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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.clevertec.statkevich.newsservice.security.jwt.JwtAuthEntryPoint;
import ru.clevertec.statkevich.newsservice.security.jwt.JwtDeniedHandler;
import ru.clevertec.statkevich.newsservice.security.jwt.JwtFilter;

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

        DefaultSecurityFilterChain defaultSecurityFilterChain = http.cors(Customizer.withDefaults())
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
                        .requestMatchers(HttpMethod.GET, "/api/v1/comments", "/api/v1/news").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();

        return defaultSecurityFilterChain;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
