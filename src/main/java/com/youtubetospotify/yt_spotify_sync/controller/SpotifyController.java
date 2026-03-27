package com.youtubetospotify.yt_spotify_sync.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youtubetospotify.yt_spotify_sync.service.SpotifyAuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class SpotifyController {

    private final SpotifyAuthService spotifyAuthService;

    public SpotifyController(SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthService = spotifyAuthService;
    }

    // STEP 1 - Login
    @GetMapping("/spotify/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect(spotifyAuthService.generateAuthUrl());
    }

    // STEP 2 - Callback
    @GetMapping("/spotify/callback")
    public Map<String, Object> callback(@RequestParam String code) {
        return spotifyAuthService.exchangeCodeForToken(code);
    }

    // STEP 3 - Get current user info via Authorization header
    @GetMapping("/spotify/me")
    public Map<String, Object> me(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return spotifyAuthService.getCurrentUser(token);
    }
}