package com.MapView.Gateway.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ProfillerController {

    @GetMapping("/userinfo")
    @CrossOrigin({"http://localhost:5173,http://localhost:4200"})

    public Map<String, Object> userInfo(@AuthenticationPrincipal OidcUser oidcUser, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient auth2AuthorizedClient){
        Map<String, Object> attributesMap = new HashMap<>(oidcUser.getAttributes());
        attributesMap.put("id_token", oidcUser.getIdToken().getTokenValue());
        attributesMap.put("access_token", auth2AuthorizedClient.getAccessToken().getTokenValue());
        attributesMap.put("client_name", auth2AuthorizedClient.getClientRegistration().getClientId());
        attributesMap.put("user_attributes", oidcUser.getAttributes());
        return attributesMap;
    }

}
