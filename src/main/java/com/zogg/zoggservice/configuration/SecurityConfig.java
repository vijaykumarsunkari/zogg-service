package com.zogg.zoggservice.configuration;

import com.zogg.zoggservice.security.JwtTokenFilter;
import com.zogg.zoggservice.utils.JwtUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired private JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers("/api/v1/auth/**")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        new JwtTokenFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                List.of(
                        "http://localhost:3000",
                        "https://a412-2401-4900-33b9-fb2e-95e0-5894-2ee6-e48e.ngrok-free.app",
                        "https://95b6-2401-4900-49a9-3dbe-415e-89c6-5893-2b58.ngrok-free.app")); // Specific frontend origin
        configuration.setAllowCredentials(
                true); // Allow credentials like cookies and authorization headers
        configuration.setAllowedMethods(
                List.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));
        //        configuration.setAllowedHeaders(List.of(
        //                "Authorization",
        //                "Content-Type",
        //                "Origin",           // Added to support CORS requests
        //                "Accept",          // Added to support CORS requests
        //                "X-Requested-With" // Added to support CORS requests
        //        ));
        //
        //        configuration.setExposedHeaders(Arrays.asList(
        //                "Authorization",
        //                "Access-Control-Allow-Origin",
        //                "Access-Control-Allow-Credentials"
        //        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
