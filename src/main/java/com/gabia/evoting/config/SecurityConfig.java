package com.gabia.evoting.config;

import com.gabia.evoting.auth.jwt.AuthenticationTokenProvider;
import com.gabia.evoting.auth.jwt.JwtTokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    private static final String[] PUBLIC_URI = {
            // -- swagger ui
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/file/**",
            "/image/**",
            "/swagger/**",
            "/swagger-ui/**",
            // other public endpoints of your API may be appended to this array
            "/h2/**"
    };

    private final AuthenticationTokenProvider jwtAuthenticationTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
//                .antMatchers("/api/agenda").hasRole("ADMIN")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
//                .exceptionHandling()
//                        .authenticationEntryPoint(jwtAuthenticationEntriPoint)
//                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenAuthenticationFilter(jwtAuthenticationTokenProvider), UsernamePasswordAuthenticationFilter.class);


//                .authenticationEntryPoint(new RestAuthenticationEntryPoint());

        // Add the customer filter. Cannot use the filter bean. See the detail in
        // https://stackoverflow.com/questions/39314176/filter-invoke-twice-when-register-as-spring-bean
//        http.addFilterBefore(new JwtTokenAuthenticationFilter(jwtAuthenticationTokenProvider), AnonymousAuthenticationFilter.class);
//        http.addFilterBefore(new JwtTokenAuthenticationFilter(jwtAuthenticationTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        // 정적인 파일 요청에 대해 무시
        web.ignoring().antMatchers(PUBLIC_URI);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
