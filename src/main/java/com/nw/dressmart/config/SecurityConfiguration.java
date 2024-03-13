package com.nw.dressmart.config;

import com.nw.dressmart.entity.Role;
import com.nw.dressmart.service.OAuth2UserService;
import com.nw.dressmart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserService userService;

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/api/v1/auth/**","/user-details")
                                .permitAll()
                                .requestMatchers("/v3/api-docs/**","/swagger-ui/**").permitAll()
                                .requestMatchers("/api/v1/user/").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST,"/api/v1/category/").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT,"/api/v1/category/**").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/category/**").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST,"/api/v1/product/").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT,"/api/v1/product/**").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/product/**").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST,"/api/v1/inventory/stock").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT,"/api/v1/inventory/**").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/inventory/**").hasAuthority(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.GET,"/api/v1/order/").hasAuthority(Role.ADMIN.name())
                                .anyRequest()
                                .authenticated())

                .oauth2Login(oauth2->
                                oauth2.defaultSuccessUrl("/user-details").userInfoEndpoint(userInfo->
                                        userInfo.oidcUserService(oAuth2UserService))
                                )
                .sessionManagement(manager->
                        manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

//    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
//        final OidcUserService delegate = new OidcUserService();
//
//        return (userRequest) -> {
//            // Delegate to the default implementation for loading a user
//            OidcUser oidcUser = delegate.loadUser(userRequest);
//
//            OAuth2AccessToken accessToken = userRequest.getAccessToken();
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//
//            // TODO
//            // 1) Fetch the authority information from the protected resource using accessToken
//            // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities
//
//            // 3) Create a copy of oidcUser but use the mappedAuthorities instead
//            oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
//
//            return oidcUser;
//        };
//    }

//    private Map<String,Object>extractAttributes(OidcUserRequest oidcUserRequest){
//        Map<String,Object> attributes=new HashMap<>();
//        attributes.put("name",oidcUserRequest.getIdToken().getClaim("name"));
//        attributes.put("email",oidcUserRequest.getIdToken().getClaim("email"));
//        return attributes;
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
