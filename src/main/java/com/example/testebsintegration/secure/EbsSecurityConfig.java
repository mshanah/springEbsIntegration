package com.example.testebsintegration.secure;

import com.example.testebsintegration.secure.filter.EbsAuthenticationFilter;
import com.example.testebsintegration.secure.filter.EbsSecFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class EbsSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    EbsAuthenticationProvider authProvider;

    @Bean
    Filter authenticationFilter() throws Exception {
        final EbsAuthenticationFilter filter = new EbsAuthenticationFilter(authenticationManager());
        //filter.setAuthenticationManager(authenticationManager());
        //filter.setAuthenticationSuccessHandler(successHandler());
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authProvider)
                .authorizeRequests()
                .antMatchers("/api/**")
                .authenticated()
                .and().httpBasic().disable()
                .formLogin().disable()

                .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)

        ; // Use Basic authentication
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        // ignoring security for /error
        web.ignoring().antMatchers("/error");
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider)
        ;
    }
}
