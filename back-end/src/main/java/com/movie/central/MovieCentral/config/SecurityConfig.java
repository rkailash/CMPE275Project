package com.movie.central.MovieCentral.config;

import com.movie.central.MovieCentral.repository.CustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@EnableWebSecurity
@Configuration
@EnableJpaRepositories(basePackageClasses = CustomerRepository.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
            .and()
            .cors()
            .and()
            .sessionManagement()
            .and()
            .authorizeRequests()
            .antMatchers("/api/*").permitAll()//change
            .and()
            .formLogin().loginPage("http://localhost:3000/login")
                .and().addFilter(getBasicAuthenticationFilter(authenticationManagerBean()))
                .addFilter(getSecurityContextPersistenceFilter());
    }

    @Bean
    public SecurityContextPersistenceFilter getSecurityContextPersistenceFilter(){
        return new SecurityContextPersistenceFilter();
    }

    @Bean
    public BasicAuthenticationFilter getBasicAuthenticationFilter(AuthenticationManager authenticationManager){
        return new BasicAuthenticationFilter(authenticationManager);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
