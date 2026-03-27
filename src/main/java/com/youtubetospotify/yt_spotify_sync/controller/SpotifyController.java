package com.youtubetospotify.yt_spotify_sync.controller;

import com.youtubetospotify.yt_spotify_sync.model.TrackInfo;
import com.youtubetospotify.yt_spotify_sync.service.SpotifyAuthService;
import com.youtubetospotify.yt_spotify_sync.service.SpotifyService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/spotify")
public class SpotifyController {

    private final SpotifyAuthService authService;
    private final SpotifyService spotifyService;

    public SpotifyController(SpotifyAuthService authService, SpotifyService spotifyService) {
        this.authService = authService;
        this.spotifyService = spotifyService;
    }

    /**
     * Endpoint to start Spotify OAuth login
     */
    @GetMapping("/login")
    public String login() {
        return authService.getLoginUrl();
    }

    /**
     * Callback endpoint to receive Spotify authorization code
     */
    @GetMapping("/callback")
    public String callback(@RequestParam String code) {
        String accessToken = authService.getAccessToken(code);
        return "Access Token: " + accessToken;
    }

    /**
     * Create a playlist on Spotify with user-selected tracks
     */
    @PostMapping("/create-playlist")
    public String createPlaylist(
            @RequestParam String userId,
            @RequestParam String playlistName,
            @RequestBody List<TrackInfo> tracks) {

        return spotifyService.createPlaylistForUser(userId, playlistName, tracks);
    }
}