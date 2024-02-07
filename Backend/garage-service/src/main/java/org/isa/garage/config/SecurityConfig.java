package org.isa.garage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionPolicy-> sessionPolicy.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/signup").permitAll()
                                .anyRequest().authenticated()

                );
        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager(){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource){
            @Override
            protected List<UserDetails> loadUsersByUsername(String username) {
                return jdbcTemplate.query(
                        "select id, email, password, isActive from user where email=?",
                        (rs, rowNum) -> {
                            Long id = rs.getLong(1);
                            String email = rs.getString(2);
                            String password = rs.getString(3);
                            boolean enabled = rs.getBoolean(4);
                            return new GarageUserDetails(id, email, password, Collections.emptyList(), enabled);
                        },
                        username
                );
            }
        };


        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select email, 'ROLE_USER' from user where email=?");
        return jdbcUserDetailsManager;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }





}
