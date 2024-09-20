package com.example.L10minordemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }/*

   // it is used to changed the password in encoding
    public static void main(String[] args)
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("2345"));
    }*/
   /*
    @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetails userDetails = User.builder()
                .username("rahul")
                .password("$2a$10$b9D6d2f  dWr6t3l45aFpuZ.KbV4NQOnDYUueDqJdStHU6dybXinwGi")
                .build();

        UserDetails userDetails1 = User.builder()
                .username("Manish")
                .password("$2a$10$d1eQC47uipNgDsMCSphn1.xFn1XUTiCMLywFFKXDTtcVmjU0wspDS")
                .build();

        return new InMemoryUserDetailsManager(userDetails1,userDetails);
    }
    */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .authorizeHttpRequests((auth)->{
            auth.requestMatchers("/Admin/**").hasAnyAuthority("ADMIN")
                    .requestMatchers("/Gate/**").hasAnyAuthority("GATEKEEPER")
                    .requestMatchers("/resident/**").hasAnyAuthority("RESIDENT")
                    .requestMatchers("/Public/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
        })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }


}
