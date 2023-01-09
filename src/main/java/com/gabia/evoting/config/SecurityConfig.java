package com.gabia.evoting.config;

import com.gabia.evoting.auth.jwt.AuthenticationTokenProvider;
import com.gabia.evoting.auth.jwt.JwtTokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    private static final String[] PUBLIC_URI = {
            "/user/me", "/h2-console/**",  "/h2-console", "*.html", "*.js", "/**"
    };

    private final AuthenticationTokenProvider jwtAuthenticationTokenProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http
                        .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC_URI).permitAll()
//                .antMatchers("/**").permitAll()
                .antMatchers("/api/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling();
//
//                .authenticationEntryPoint(new RestAuthenticationEntryPoint());

        // Add the customer filter. Cannot use the filter bean. See the detail in
        // https://stackoverflow.com/questions/39314176/filter-invoke-twice-when-register-as-spring-bean
        http.addFilterBefore(new JwtTokenAuthenticationFilter(jwtAuthenticationTokenProvider), AnonymousAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(PUBLIC_URI);
    }
}
