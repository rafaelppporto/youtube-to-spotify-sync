package com.youtubetospotify.yt_spotify_sync.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class SpotifyAuthService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.redirect.uri}")
    private String redirectUri;

    // STEP 1 - Generate login URL
    public String generateAuthUrl() {

        String scope = "playlist-modify-public playlist-modify-private";

        return "https://accounts.spotify.com/authorize"
                + "?client_id=" + clientId
                + "&response_type=code"
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                + "&scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8);
    }

    // STEP 2 - Exchange code for access token
    public String exchangeCodeForToken(String code) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://accounts.spotify.com/api/token";

        String body = "grant_type=authorization_code"
                + "&code=" + code
                + "&redirect_uri=" + redirectUri;

        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        if (response.getBody() == null) {
            return "Error: Empty response from Spotify";
        }

        return response.getBody().toString();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map<String, Object> getCurrentUser(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.spotify.com/v1/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        return response.getBody();
    }
}