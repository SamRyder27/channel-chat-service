package com.channel_chat_service.Config;

import com.channel_chat_service.JwtUtils.JwtAuthEntryPoint;
import com.channel_chat_service.JwtUtils.JwtAuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JwtAuthEntryPoint unauthorisedhandler;
    //@Autowired
    //private JwtAuthTokenFilter jwtAuthTokenFilter;

    public SecurityConfig(DataSource dataSource, JwtAuthEntryPoint unauthorisedhandler) {
        this.dataSource = dataSource;
        this.unauthorisedhandler = unauthorisedhandler;
       // this.jwtAuthTokenFilter = jwtAuthTokenFilter;
    }

    public  JwtAuthTokenFilter jwtAuthTokenFilter (){
        return  new JwtAuthTokenFilter();
    }


    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());



        http.authorizeHttpRequests(authRequest -> authRequest
                .requestMatchers("/console/signup/").permitAll()
                .requestMatchers("/api/signin/").permitAll()
                .anyRequest().authenticated()
        );
        http.sessionManagement(session -> session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS)
        );

        http.exceptionHandling((exception -> exception.authenticationEntryPoint(unauthorisedhandler)));
        http.headers( header -> header.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        http.addFilterBefore(jwtAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder){
        return builder.getAuthenticationManager();
    }

}
