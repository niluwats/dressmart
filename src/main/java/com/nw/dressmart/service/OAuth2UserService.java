package com.nw.dressmart.service;

import com.nw.dressmart.dto.Oauth2UserDto;
import com.nw.dressmart.entity.Role;
import com.nw.dressmart.entity.User;
import com.nw.dressmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class OAuth2UserService extends OidcUserService {
    @Autowired
    private UserRepository userRepository;

    public OidcUser loadUser(OidcUserRequest userRequest)throws OAuth2AuthenticationException {
        OidcUser oidcUser=super.loadUser(userRequest);
        Map<String, Object> attributes=oidcUser.getAttributes();

        Oauth2UserDto oauth2UserDto=new Oauth2UserDto(
                attributes.get("name").toString().split(" ")[0],
                attributes.get("name").toString().split(" ")[1],
                attributes.get("email").toString()

        );

        updateUser(oauth2UserDto);

        return oidcUser;
    }

    private void updateUser(Oauth2UserDto oauth2UserDto){
       Optional<User> optionalUser=userRepository.findByEmail(oauth2UserDto.getEmail());
       User user=new User();

        if (optionalUser.isPresent()){
            user=optionalUser.get();
        }

        user.setEmail(oauth2UserDto.getEmail());
        user.setFirstName(oauth2UserDto.getFirstName());
        user.setLastName(oauth2UserDto.getLastName());
        user.setRole(Role.CUSTOMER);
        user.setLocked(false);
        user.setEnabled(true);

        userRepository.save(user);
    }
}
