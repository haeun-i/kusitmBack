package com.kusitms.kusitms5.config;


import com.kusitms.kusitms5.jwt.JwtAccessDeniedHandler;
import com.kusitms.kusitms5.jwt.JwtAuthenticationEntryPoint;
import com.kusitms.kusitms5.jwt.JwtSecurityConfig;
import com.kusitms.kusitms5.jwt.TokenProvider;
import com.kusitms.kusitms5.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final OAuthService oAuthService;


    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler,
            OAuthService oAuthService
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.oAuthService = oAuthService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // web
                /*.ignoring()
                .antMatchers(
                        "/h2-console/**"
                        ,"/favicon.ico"
                );*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // token??? ???????????? ???????????? ????????? csrf??? disable?????????.
                .csrf().disable()
                // ????????? Exception??? ????????? ??? ??? ????????? ?????? ????????? ??????
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin() // oauth -> disable()

                // ????????? ???????????? ?????? ????????? STATELESS??? ??????
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // ????????? api??? ???????????? api??? ????????? ?????? ???????????? ????????? ???????????? ?????????
                // ?????? permitAll ????????? ?????????
                .and()
                .authorizeRequests()
                .antMatchers("/api/hello").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/signup").permitAll()
                .anyRequest().permitAll()

                // JwtFilter??? addFilterBefore??? ???????????? JwtSecurityConfig???????????? ??????
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))

                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                    .userInfoEndpoint()
                    .userService(oAuthService);
    }


}
