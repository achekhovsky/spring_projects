package com.custom.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web
.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web
.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.custom.spring.mvc.security.handlers.CustomSimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                    .disable()
                .authorizeRequests()
                    //Доступ только для не зарегистрированных пользователей
                    .antMatchers("/", "/home", "/login").not().fullyAuthenticated()
                    //Доступ только для пользователей с ролью Администратор
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/orders").hasAnyRole("ADMIN", "USER")
                    //Доступ разрешен всем пользователей
                    .antMatchers("/", "/home").permitAll()
                //Все страницы кроме "/" требуют аутентификации
                //.regexMatchers("!.").authenticated()
                .and()
                    //Настройка для входа в систему
                    .formLogin()
                    .loginPage("/login")
                    .successHandler(customAuthenticationSuccessHandler())
                    //Перенарпавление на главную страницу после успешного входа
                    //.defaultSuccessUrl("/orders")
                    .permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/home");
    }
    
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
          .withUser("user").password(bCryptPasswordEncoder().encode("user")).roles("USER")
          .and()
          .withUser("admin").password(bCryptPasswordEncoder().encode("admin")).roles("ADMIN", "USER");
    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
	public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
	    return new CustomSimpleUrlAuthenticationSuccessHandler();
	}
}
