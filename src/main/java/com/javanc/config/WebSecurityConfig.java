package com.javanc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@PropertySource("classpath:application.yml")
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration.class
})
public class WebSecurityConfig {

    @Value("${jwt.sign_key}")
    private String SIGN_KEY;

    private final String[] PUBLIC_ENPOINTS = {
            "/users",
            "/auth/login",
            "/auth/register",
            "/auth/social-login",
            "/auth/refreshToken",
            "/auth/logout",
            "/api/upload",
            "/api/chatbot/ask",
            "/auth/sendEmail",
            "/api/otp/send",
            "/api/otp/verify",
            "/auth/forgot/OTPRequest",
            "/auth/forgot/checkOTP"
    };

    @Autowired
    private CustomJwtDecoder customerJwtDecoder;


    private final String apiPrefix = "/api/admin";
    private final String userApiPrefix = "/api";

    // ===================== CORS =====================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/ws/**");
    }



    // ===================== SECURITY =====================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .authorizeHttpRequests(request -> request


                        // ======== CORS Preflight ========
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ======== Chatbot cần token ========


                        // ======== Public POST ========
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENPOINTS).permitAll()

                        // ======== Public Auth ========
                        .requestMatchers("/auth/**").permitAll()

                        // ======== Product ========
                        .requestMatchers(HttpMethod.GET, String.format("%s/products/**", userApiPrefix)).permitAll()

                        // ======== Shopping cart ========
                        .requestMatchers(HttpMethod.POST, String.format("%s/shopping-cart/**", userApiPrefix)).permitAll()

                        // ======== Common ========
                        .requestMatchers(HttpMethod.GET, String.format("%s/common/**", userApiPrefix)).permitAll()

                        // ======== Forgot password ========
                        .requestMatchers(HttpMethod.PATCH, "/auth/forgot/reset").permitAll()

                        // ======== Admin GET public ========
                        .requestMatchers(HttpMethod.GET, "/api/admin/user").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/orders").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/product").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/recent").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/total_month").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/topOrder").permitAll()
                        .requestMatchers("/api/chat-rooms").permitAll()
                        .requestMatchers("/ws/**").permitAll()


                        // ======== Các API còn lại cần JWT ========
                        .anyRequest().authenticated()
                );

        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customerJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(new AuthenticationEntryPointConfig())
        );


        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }


    // ================= PASSWORD ENCODER =================
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    // ================= JWT ROLE CONVERTER =================
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter granted = new JwtGrantedAuthoritiesConverter();
        granted.setAuthorityPrefix("");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(granted);
        return converter;
    }
}
