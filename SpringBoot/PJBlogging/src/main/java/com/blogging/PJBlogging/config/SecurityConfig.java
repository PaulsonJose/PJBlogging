package com.blogging.PJBlogging.config;

import com.blogging.PJBlogging.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

@EnableWebSecurity()
@AllArgsConstructor
public class SecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource datasource;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

//Authorisation
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource()).and().csrf().disable().authorizeRequests().
                antMatchers("/api/auth/**").permitAll().
                antMatchers("/v2/api-docs**","/configuration/ui",
                        "/swagger-resources/**","/configuration/security",
                        "/swagger-ui.html","/webjars/**").permitAll().
                antMatchers(HttpMethod.GET, "/api/posts/**").permitAll().
                antMatchers(HttpMethod.GET, "/api/posts/").permitAll().
                antMatchers(HttpMethod.GET,"/api/pjsubblog").permitAll().
                //antMatchers("/**").hasAnyRole("USER","ADMIN")
                anyRequest().authenticated()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
                /*.and()
                .formLogin().loginPage("/api/auth/login").permitAll()
                .and()
                .logout().permitAll();*/
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
//Authentication
    @Override
    public void configure(AuthenticationManagerBuilder authMgr) throws Exception {
        //Set configuration on the authentication object. Once the userDetailsService returns user information
        // from any source this will be authenticated by Spring Security.
        authMgr.userDetailsService(userDetailsService);
        //In case of JDBC Authentication
        /*authMgr.jdbcAuthentication()
        .dataSource(datasource)
        .usersByUsernameQuery("select user_name username, password, enabled from blog_user where user_name = ?")
        .authoritiesByUsernameQuery("select user_name username, user_role authority from blog_user where user_name = ?");
        */
    }

    //Spring Security only supports encoded passwords for the login
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        boolean cenab = jwtAuthenticationFilter.isCorsEnabled();
        if(jwtAuthenticationFilter.isCorsEnabled()) {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8081"));
            configuration.setAllowedMethods(Arrays.asList(RequestMethod.GET.name(),
                    RequestMethod.POST.name(),
                    RequestMethod.OPTIONS.name(),
                    RequestMethod.DELETE.name(),
                    RequestMethod.PUT.name()));
            configuration.setExposedHeaders(Arrays.asList("Authorization", "x-auth-token", "x-requested-with", "x-xsrf-token"));
            configuration.setAllowedHeaders(Arrays.asList("*"));
            source.registerCorsConfiguration("/**", configuration);
        }
        return source;
    }
}
