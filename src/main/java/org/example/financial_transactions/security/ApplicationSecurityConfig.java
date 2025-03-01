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
        http
                .csrf(AbstractHttpConfigurer::disable)   //CSRF protection is often disabled in stateless APIs or applications that rely on other mechanisms (like JWT tokens) for security.
                .authorizeHttpRequests(authorize -> authorize
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
