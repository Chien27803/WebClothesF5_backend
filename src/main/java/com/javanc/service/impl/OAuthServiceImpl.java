package com.javanc.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.javanc.service.OAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@PropertySource("classpath:application.yml")
public class OAuthServiceImpl implements OAuthService {

    // google
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    String googleClientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    String googleRedirectUri;
    @Value("${spring.security.oauth2.client.provider.google.authorization-uri}")
    String googleAuthorizationUri;
    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    String googleTokenUri;
    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    String googleUserInfoUri;

    // facebook
    @Value("${spring.security.oauth2.client.registration.facebook.client-id}")
    String facebookClientId;
    @Value("${spring.security.oauth2.client.registration.facebook.client-secret}")
    String facebookClientSecret;
    @Value("${spring.security.oauth2.client.registration.facebook.redirect-uri}")
    String facebookRedirectUri;
    @Value("${spring.security.oauth2.client.provider.facebook.authorization-uri}")
    String facebookAuthorizationUri;
    @Value("${spring.security.oauth2.client.provider.facebook.token-uri}")
    String facebookTokenUri;
    @Value("${spring.security.oauth2.client.provider.facebook.user-info-uri}")
    String facebookUserInfoUri;

    final String responseType = "code";
    final String scopeGoogle = "openid profile email";
    final String scopeFacebook = "email public_profile";

    public Map<String, Object> fetchProfileWithGoogle(String code) throws IOException {

        GenericUrl tokenUrl = new GenericUrl(googleTokenUri);

        // Tạo AuthorizationCodeTokenRequest
        AuthorizationCodeTokenRequest tokenRequest = new AuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                new JacksonFactory(),
                tokenUrl,
                code
        );

        // Thiết lập client authentication và redirect URI
        tokenRequest.setClientAuthentication(
                new com.google.api.client.auth.oauth2.ClientParametersAuthentication(googleClientId, googleClientSecret)
        );
        tokenRequest.setRedirectUri(googleRedirectUri);

        // Thực hiện yêu cầu và lấy access token
        TokenResponse tokenResponse = tokenRequest.execute();
        String accessToken = tokenResponse.getAccessToken();

        // Sử dụng access token để lấy thông tin người dùng
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> userInfoRequest = new HttpEntity<>(headers);
        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(
                googleUserInfoUri,
                HttpMethod.GET,
                userInfoRequest,
                Map.class
        );
        return Map.of("email", userInfoResponse.getBody().get("email"),
                "name", userInfoResponse.getBody().get("name"),
                "avatar", userInfoResponse.getBody().get("picture"));
    }

    public Map<String, Object> fetchProfileWithFaceBook(String code) throws JsonProcessingException {

        // Facebook: dùng RestTemplate để lấy token
        String accessTokenUrl = UriComponentsBuilder.fromHttpUrl(facebookTokenUri)
                .queryParam("client_id", facebookClientId)
                .queryParam("redirect_uri", facebookRedirectUri)
                .queryParam("client_secret", facebookClientSecret)
                .queryParam("code", code)
                .toUriString();

        ResponseEntity<String> fbTokenResponse = new RestTemplate().getForEntity(accessTokenUrl, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(fbTokenResponse.getBody());
        String fbAccessToken = jsonNode.get("access_token").asText();

        // Lấy thông tin user từ Facebook
        String userInfoUrl = UriComponentsBuilder.fromHttpUrl(facebookUserInfoUri)
                .queryParam("fields", "id,name,email,picture.type(large)")
                .queryParam("access_token", fbAccessToken)
                .toUriString();

        ResponseEntity<String> fbUserInfoResponse = new RestTemplate().getForEntity(userInfoUrl, String.class);
        JsonNode userInfo = objectMapper.readTree(fbUserInfoResponse.getBody());

        return Map.of("email", userInfo.has("email") ? userInfo.get("email").asText() : null,
                "name", userInfo.get("name").asText(),
                "avatar", userInfo.get("picture").get("data").get("url").asText());
    }

    @Override
    public String generateAuthorizationURL(String provider) {
        String clientId = "";
        String redirectUri = "";
        String authorizationUri = "";
        String scopes = "";

        switch (provider.trim().toLowerCase()) {
            case "google":
                clientId = googleClientId;
                redirectUri = googleRedirectUri;
                authorizationUri = googleAuthorizationUri;
                scopes = scopeGoogle;
                break;
            case "facebook":
                clientId = facebookClientId;
                redirectUri = facebookRedirectUri;
                authorizationUri = facebookAuthorizationUri;
                scopes = scopeFacebook;
                break;
            default:
                throw new IllegalArgumentException("Unsupported OAuth2 provider: " + provider);
        }
        String state = UUID.randomUUID().toString();
        String url = UriComponentsBuilder.fromUriString(authorizationUri)
                .queryParam("response_type", responseType)
                .queryParam("client_id", clientId)
                .queryParam("scope", scopes)
                .queryParam("state", state)
                .queryParam("redirect_uri", redirectUri)
                .build()
                .toUriString();
        return url;
    }

    @Override
    public Map<String, Object> fetchProfile(String code, String provider) throws IOException {
        switch (provider.trim().toLowerCase()) {
            case "google":
                return fetchProfileWithGoogle(code);
            case "facebook":
                return fetchProfileWithFaceBook(code);
            default:
                throw new IllegalArgumentException("Unsupported OAuth type: " + provider);
        }
    }
}
