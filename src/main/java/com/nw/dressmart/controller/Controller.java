package com.nw.dressmart.controller;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@RestController
public class Controller {
    private OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

    @GetMapping()
    public String home() {
        return "welcome";
    }

    @GetMapping("/login/oauth2/code/google")
    public String oauth2Google() {
        return "authenticated using google account";
    }

//    @GetMapping("/user-details")
//    public String success(@AuthenticationPrincipal OAuth2User principal){
//        System.out.println("//////////////////////////////");
//        System.out.println(principal.getAttribute("name").toString());
//        return "OK";
//    }

    @GetMapping("/user-details")
    public Object success() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  authentication.getPrincipal();
    }
//
//    @GetMapping("/user-details")
//    public String success(Model model, OAuth2AuthenticationToken authentication){
//        OAuth2AuthorizedClient client = oAuth2AuthorizedClientService
//                .loadAuthorizedClient(
//                        authentication.getAuthorizedClientRegistrationId(),
//                        authentication.getName());
//
//        String userInfoEndpointUri = client.getClientRegistration()
//                .getProviderDetails().getUserInfoEndpoint().getUri();
//
//        if (!StringUtils.isEmpty(userInfoEndpointUri)) {
//            RestTemplate restTemplate = new RestTemplate();
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
//                    .getTokenValue());
//            HttpEntity entity = new HttpEntity("", headers);
//            ResponseEntity<Map> response = restTemplate
//                    .exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
//            Map userAttributes = response.getBody();
//            model.addAttribute("name", userAttributes.get("name"));
//        }
//        return "OK";
//    }

}