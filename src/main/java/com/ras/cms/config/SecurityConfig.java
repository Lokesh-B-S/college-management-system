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
                .antMatchers("/", "/about","/actuator/**").permitAll()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**","/js/*.min*").permitAll()
                .antMatchers("/admin/**").hasAnyRole("PRINCIPAL")
                .antMatchers("/user/**").hasAnyRole("USER")
                .antMatchers("/hod/**").hasAnyRole("DEPT_HEAD")
                .antMatchers("/student/**").hasAnyRole("STUDENT")
                .antMatchers("/faculty/**").hasAnyRole("DEPT_LECTURER")
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
                .antMatchers("/CollegeEdit/**","/listCollege/**","/CollegeDelete/**").hasAnyRole("SITE_ADMIN")
                .antMatchers("/CollegeEdit/**","/listCourse/**","/courseEdit/**","/courseDelete/**").hasAnyRole("College_ADMIN")
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
                .withUser("u").password("{noop}p").roles("SITE_ADMIN")
                .and()
                .withUser("p").password("{noop}p").roles("PRINCIPAL")    //user //password //admin //password
                .and()
                .withUser("hodcse").password("{noop}p").roles("DEPT_HEAD")
                .and()
                .withUser("hodmech").password("{noop}p").roles("DEPT_HEAD")
                .and()
                .withUser("hodiem").password("{noop}p").roles("DEPT_HEAD")
                .and()
                .withUser("hodise").password("{noop}p").roles("DEPT_HEAD")
                .and()
                .withUser("s1").password("{noop}p").roles("STUDENT")
                .and()
                .withUser("s2").password("{noop}p").roles("STUDENT")
                .and()
                .withUser("s3").password("{noop}p").roles("STUDENT")
                .and()
                .withUser("s4").password("{noop}p").roles("STUDENT")
                .and()
                .withUser("s5").password("{noop}p").roles("STUDENT")
                .and()
                .withUser("f1").password("{noop}p").roles("DEPT_LECTURER")
                .and()
                .withUser("f2").password("{noop}p").roles("DEPT_LECTURER")
                .and()
                .withUser("f3").password("{noop}p").roles("DEPT_LECTURER");

    }
}