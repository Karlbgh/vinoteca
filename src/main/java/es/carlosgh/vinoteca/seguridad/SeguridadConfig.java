package es.carlosgh.vinoteca.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SeguridadConfig {

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    List<UserDetails> users = new ArrayList<>();
    users.add(User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build()
    );
    users.add(User.builder()
            .username("user")
            .password(passwordEncoder().encode("user"))
            .roles("USER")
            .build()
    );

    return new InMemoryUserDetailsManager(users);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.authorizeHttpRequests()
              .antMatchers("/","/webjars/**", "/css/**", "/h2-console/**").permitAll()
              .anyRequest().authenticated()
              .and()
              .formLogin()
              .loginPage("/login")
              .permitAll()
              .and()
              .logout()
              .logoutSuccessUrl("/login?logout")
              .permitAll();

    return http.build();
  }

//  @Bean
//  public WebSecurityCustomizer webSecurityCustomizer() {
//    return (web) -> web.ignoring().antMatchers("/webjars/**", "/css/**", "/h2-console/**");
//  }
}
