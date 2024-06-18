package com.nikolay.dateApp.security;

import com.nikolay.dateApp.configuration.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.annotation.GetMapping;
import com.nikolay.dateApp.entities.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    private UserService userService;
    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
   public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

   @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)//отключение проверки межсайтовой подделки запросов
                    .authorizeHttpRequests(autorize-> autorize

                        .requestMatchers("/admin/**","/second/").hasAuthority("ADMIN")//.hasRole("ADMIN") -!!!! не работает в новой версии SPRING
                            .requestMatchers("*/regist/**").anonymous()
                            .requestMatchers("/auth/**",
                                    "/home", "/regist/**").permitAll()//, "/register","/auth/register" - вроде бы и без этого работает
                            .requestMatchers("/second/**").hasAuthority("USER")//.hasRole("USER")////проверяет строку, но подставлет преффикс "ROLE_"

                        .anyRequest()
                        .authenticated())
                        .httpBasic(Customizer.withDefaults());//добавил это и в Postman все заработало

                   http.
                        formLogin(login->login.loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                                //.loginProcessingUrl("/second")//вроде бы сразу после регистрации получет доступ без логина заново
                        .defaultSuccessUrl("/home", true)
                                //.failureUrl("/auth/register")//виснет
                                //.failureForwardUrl("/auth/register")
                                //.failureForwardUrl("/login?error=true")
                                .failureUrl("/login?error=true")//просто обновляется страница
                        .permitAll()
                        );
                   //не выходит из профиля
                /*               http .logout(logout->logout.logoutUrl("/logout")
                                       .logoutSuccessUrl("/homePage")
                                       .permitAll());*/
                http.
                        logout(form->form.invalidateHttpSession(true).
                                clearAuthentication(true).
                                logoutSuccessUrl("/homePage").
                                permitAll());

        return http.build();
    }

}
