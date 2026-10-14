package com.angyal.app.Config;

import com.angyal.app.Security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String API_PREFIX = "/api";

    // Povolené originy pre CORS - načítané z application.properties (env premenná CORS_ALLOWED_ORIGINS)
    @Value("${app.cors.allowed-origins}")
    private List<String> allowedOrigins;

    /**
     * Bean pre BCryptPasswordEncoder
     * Používa sa na šifrovanie a porovnávanie hesiel
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Vypnutie CSRF (Cross-Site Request Forgery) ochrany
                // Nie je potrebné pre REST API ktoré používajú JWT token
                .csrf(csrf -> csrf.disable())

                // Autorizačné pravidlá
                .authorizeHttpRequests(auth -> auth
                        // Verejné endpointy - nevyžadujú token
                        .requestMatchers(HttpMethod.OPTIONS, API_PREFIX + "/**").permitAll()  // Povoľ všetky CORS preflight requesty
                        .requestMatchers(
                                API_PREFIX + "/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/swagger-resources").permitAll()

                        // Všetky ostatné endpointy vyžadujú autentifikáciu (JWT token)
                        .anyRequest().authenticated()
                )

                // Nastavenie session policy na STATELESS
                // Nepoužívame server-side sessions, len JWT tokeny
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Pridanie JWT filtra pred štandardný Spring Security filter
                // Tento filter extrahuje a validuje JWT token z každého requestu
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", c -> c.isAnnotationPresent(RestController.class));
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(API_PREFIX + "/**", config);
        return source;
    }



    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http.cors(cors -> cors.configurationSource(request -> {
    //         CorsConfiguration config = new CorsConfiguration();
    //         config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    //         config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    //         config.setAllowedHeaders(Arrays.asList("*"));
    //         config.setAllowCredentials(true);
    //         return config;
    //     }));
    //     return http.build();
    // }
}
