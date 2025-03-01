package org.example.financial_transactions.security;

import lombok.RequiredArgsConstructor;
import org.example.financial_transactions.dao.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        http
//                .csrf(AbstractHttpConfigurer::disable)   //CSRF protection is often disabled in stateless APIs or applications that rely on other mechanisms (like JWT tokens) for security.
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/**").hasRole("EMPLOYEE") // This rule states that any request to any path (/** matches all URLs) must have the role EMPLOYEE.
//                        .anyRequest().authenticated()) // All other requests (not covered by /**) must be authenticated, meaning any user who is logged in can access them (but they must have valid credentials).
//                .httpBasic(withDefaults()); // This enables HTTP Basic Authentication. Basic authentication sends the username and password with each request as part of the Authorization header.
//        return http.build();  //This builds the final security configuration and returns a SecurityFilterChain object, which Spring Security uses to apply the defined security settings.

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/**").hasRole("EMPLOYEE")
                        .anyRequest().authenticated())
                .httpBasic(withDefaults());
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                userDetailsService(username -> adminRepository.findByNationalCode(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String
                                .format("this %s not found", username))))
                .passwordEncoder(passwordEncoder);
    }
}
