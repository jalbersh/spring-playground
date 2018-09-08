package com.galvanize.jalbersh.springplayground.config;

import com.galvanize.jalbersh.springplayground.service.EmployeeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    EmployeeDetailsService employeeDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().mvcMatchers("/flights/**", "/math/**", "/movies/**", "/favorites/**", "/me").permitAll();
        http.authorizeRequests().mvcMatchers("/admin/employees").hasRole("ADMIN");
        http.authorizeRequests().mvcMatchers("/employees").access("hasAnyRole('EMPLOYEE','ADMIN')");
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(employeeDetailsService)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance());
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.httpBasic();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests().mvcMatchers("/flights/**", "/math/**").permitAll();
//        http.authorizeRequests().mvcMatchers("/admin/employees").access("hasRole('ADMIN')");
//        http.authorizeRequests().mvcMatchers("/employees","/me").access("hasAnyRole('EMPLOYEE','ADMIN')");
//        http.authorizeRequests().anyRequest().authenticated();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .withUser("employee").password("my-employee-password").roles("EMPLOYEE")
//                .and()
//                .withUser("boss").password("my-boss-password").roles("ADMIN");
//    }
}
