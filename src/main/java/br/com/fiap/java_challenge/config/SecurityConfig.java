package br.com.fiap.java_challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/home", "/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/usuarios", "/usuarios/novo", "/usuarios/{id}/editar", "/usuarios/{id}/excluir",
                                "/usuarios/{id}/estabelecimentos", "/usuarios/{usuarioId}/estabelecimentos/{estabelecimentoId}/editar",
                                "/usuarios/{usuarioId}/estabelecimentos/{estabelecimentoId}/excluir", "/usuarios/{id}/itinerario",
                                "/avaliacoes", "/avaliacoes/novo", "/avaliacoes/{id}/editar", "/avaliacoes/{id}/excluir").permitAll()
                        .requestMatchers("/actuator/**").permitAll() // Permitir todos os endpoints do Actuator
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.disable()); // Desabilita o CSRF para simplificar (não recomendado para produção!)

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("admin")
                        .password(passwordEncoder().encode("admin"))
                        .roles("ADMIN")
                        .build();

        UserDetails user2 =
                User.withUsername("user")
                        .password(passwordEncoder().encode("user"))
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
