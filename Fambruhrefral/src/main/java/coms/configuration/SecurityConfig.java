package coms.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import coms.service.UserDetailService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailService).passwordEncoder(passwordEncoder());
    }
    
    @Bean
	protected SecurityFilterChain configureAuthorization (HttpSecurity http) throws Exception{
    	return 
    	  http
	      .csrf().disable()
	      .cors().disable()
	      .authorizeHttpRequests((auth) -> {
	      	try {
	      	    auth
	      		.antMatchers("/generate-token", "/current-user").permitAll()
	            .antMatchers("/user/**").permitAll()
	            .antMatchers("/product/getById/**", "/product/get/all", "/product/getByName/**", 
	              		     "/product/get/comboproduct/**", "/product/get/all-comboproducts").permitAll()
	            .antMatchers("/home/**").permitAll()
	            .antMatchers("/blog/get/all-blogs", "/blog/get/**", "/blog/get/blogByTitle/**", "/newsletter/**").permitAll()
	            .antMatchers(HttpMethod.OPTIONS).permitAll()
	            .anyRequest().authenticated();
	      	} catch(Exception e) {
	      		throw new RuntimeException(e);
	      	}
	      })
	      .exceptionHandling().authenticationEntryPoint(authEntryPoint)
	      .and()
	      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	      .and()
	      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
	      .build(); 
    }

}
