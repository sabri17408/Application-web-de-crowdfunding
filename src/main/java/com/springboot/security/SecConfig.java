package com.springboot.security;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.springboot.service.CustomOAuth2UserService;


@Configuration
public class SecConfig extends WebSecurityConfigurerAdapter {


	
	
	
	  @Autowired 
	  private DataSource dataSource;
	  
		
		@Autowired private UserDetailsService userDetailsService;
	    @Autowired private CustomOAuth2UserService oAuth2UserService;
		 
	 
	  
	  //pour spécifier à spring security comment chercher les utilisateurs
	  
	  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
		  PasswordEncoder passwordEncoder = passwordEncoder();
	  
			/*
			 * // System.out.println(passwordEncoder.encode("123")); //
			 * auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.
			 * encode("123")).roles("USER");
			 */
	  
	  
			/*
			 * auth.jdbcAuthentication().dataSource(dataSource)
			 * .usersByUsernameQuery("select username as principal, password as credentials, enabled from user where username=?"
			 * )
			 * 
			 * .passwordEncoder(passwordEncoder);
			 */
		  auth.userDetailsService(userDetailsService)
		  .passwordEncoder(passwordEncoder());
		 }
	 
	 
//pour autoriser ou interdire l'accès à une ressource
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       //http.csrf().disable();//déconseillé
       http.formLogin().loginPage("/login"); //pour personnnalier la page d'authentification
    	//http.formLogin();
       http.authorizeRequests().antMatchers("/login/**","/index/**",
    		   "/formproject/**",
               "/accountVerification/**",
    		   "/discoverprojects/**",
    		   "/viewproject/**",
               "/projet/**/",
               "/register/**",
               "/oauth/**",
               "/css**/**",
               "/images**/**",
               "/img**/**",
               "/img/js/**",
               "/mail/**",

               "/lib/**",

               "/resources**/**").
	       permitAll()
	       .and()
	   .logout()
	       .permitAll();
        http.authorizeRequests().anyRequest().authenticated();

		
		 http.oauth2Login() .loginPage("/login") .userInfoEndpoint()
		  .userService(oAuth2UserService);
		 

        //http.exceptionHandling().accessDeniedPage("/403");

        //http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"));

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
