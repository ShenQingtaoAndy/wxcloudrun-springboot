package com.tencent.wxcloudrun.config;

import com.tencent.wxcloudrun.cons.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    private final CustomAuthenticationProvider customAuthenticationProvider;

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider,
                          CustomUserDetailsService customUserDetailsService) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/login.html").permitAll()
                        .antMatchers("/login").permitAll()
                        .antMatchers("/loginByWechat").permitAll()
                        .antMatchers("/enter.html").permitAll()
                        .antMatchers("/api/**").permitAll()
                        .antMatchers("/mobile/mainPage.xhtml").permitAll()
                        .antMatchers("/mobile/**").hasAnyRole(UserRole.Sales.name(),UserRole.Purchaser.name(),UserRole.Admin.name())
                        .antMatchers("/pages/**").hasRole(UserRole.Admin.name())
                        .antMatchers("/admin/**").hasRole(UserRole.Admin.name())
                        .anyRequest().authenticated()
                )
                .userDetailsService(customUserDetailsService)
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/pages/test.xhtml")
                        .failureForwardUrl("/login.html")
                        .permitAll()
                )
                .authenticationProvider(customAuthenticationProvider)
                .logout(logout -> logout.permitAll());
        return http.build();
    }

}