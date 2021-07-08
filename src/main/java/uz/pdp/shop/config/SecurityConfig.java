package uz.pdp.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.shop.entity.role.UserPermission;
import uz.pdp.shop.security.ApplicationJwtTokenFilter;
import uz.pdp.shop.security.ApplicationUsernamePasswordAuthenticationFilter;
import uz.pdp.shop.service.auth.AuthService;
import uz.pdp.shop.entity.role.UserPermission;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final AuthService authService;
    private final ApplicationJwtTokenFilter applicationJwtTokenFilter;


    @Autowired
    public SecurityConfig(AuthService authService, ApplicationJwtTokenFilter applicationJwtTokenFilter) {
        this.authService = authService;
        this.applicationJwtTokenFilter = applicationJwtTokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilter(new ApplicationUsernamePasswordAuthenticationFilter(authenticationManager()))
                .addFilterBefore(applicationJwtTokenFilter, ApplicationUsernamePasswordAuthenticationFilter.class)
                .anonymous()
                .and();
        http.authorizeRequests()
                .antMatchers("/api/admin/**").permitAll()
                .antMatchers("/api/shop/user/add").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
          /* web
                    .ignoring()
                    .antMatchers("/plugins/**");*/
    }


}
