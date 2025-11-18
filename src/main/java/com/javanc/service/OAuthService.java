package com.javanc.service;


import java.io.IOException;
import java.util.Map;

public interface OAuthService {
    String generateAuthorizationURL(String provider);
    Map<String, Object> fetchProfile (String code, String provider) throws IOException;
}
