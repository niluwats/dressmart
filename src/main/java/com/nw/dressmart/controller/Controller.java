package com.nw.dressmart.controller;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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
    public String home(){
        return "welcome";
    }

    @PostMapping("/login/oauth2/code/google")
    public  String oauth2Google(Model model, OAuth2AuthenticationToken auth2AuthenticationToken){
        System.out.println("++++++++++++++++++++++++++++++++++++++");
            OAuth2AuthorizedClient client=oAuth2AuthorizedClientService.loadAuthorizedClient(
                    auth2AuthenticationToken.getAuthorizedClientRegistrationId(),
                    auth2AuthenticationToken.getName()
            );
            String userInfoEndpointUri= client.getClientRegistration()
                    .getProviderDetails().getUserInfoEndpoint().getUri();
            if(!StringUtils.isEmpty(userInfoEndpointUri)){
                RestTemplate restTemplate=new RestTemplate();
                HttpHeaders headers=new HttpHeaders();
                headers.add(HttpHeaders.AUTHORIZATION,"Bearer "+client.getAccessToken()
                        .getTokenValue());
                HttpEntity  entity=new HttpEntity("",headers);
                ResponseEntity response=restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET,entity, Map.class);
                Map userAttributes= (Map) response.getBody();
                model.addAttribute("name",userAttributes.get("name"));
                System.out.println("000000000"+userAttributes.get("name").toString());
            }

            return "authenticated using google account";
    }

    @GetMapping("/success")
    public String success(){

        return "success";
    }
}
