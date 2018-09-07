package com.ras.cms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/about").permitAll()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**","/js/*.min*").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);

        http.headers().frameOptions().disable();
    }

    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/about","/error/**","/console/**").permitAll()
                .antMatchers("/error/403/**","/resources/**", "/static/**", "/css/**", "/js/**","/js/*.min*").permitAll()
                .antMatchers("/collageEdit/**","/listCollage/**","/collageDelete/**").hasAnyRole("SITE_ADMIN")
                .antMatchers("/collageEdit/**","/listCourse/**","/courseEdit/**","/courseDelete/**").hasAnyRole("COLLAGE_ADMIN")
                .antMatchers("/listCourse/**","/courseEdit/**","/listStudent/**","/studentEdit/**").hasAnyRole("COURSE_ADMIN")
//                .antMatchers("/user/**").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        http.headers().frameOptions().disable();
    }*/

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("SITE_ADMIN")
                .and()
                .withUser("admin").password("{noop}password").roles("COLLAGE_ADMIN");
    }
}