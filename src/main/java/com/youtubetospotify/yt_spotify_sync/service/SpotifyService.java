package com.youtubetospotify.yt_spotify_sync.service;

import com.youtubetospotify.yt_spotify_sync.client.SpotifyClient;
import com.youtubetospotify.yt_spotify_sync.model.TrackInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpotifyService {

    private final SpotifyAuthService authService;
    private final SpotifyClient spotifyClient;

    public SpotifyService(SpotifyAuthService authService, SpotifyClient spotifyClient) {
        this.authService = authService;
        this.spotifyClient = spotifyClient;
    }

    /**
     * Create a playlist for the user with selected tracks
     *
     * @param userId       Spotify user ID
     * @param playlistName Name of the playlist to create
     * @param tracks       List of TrackInfo selected by the user
     * @return URL of the created playlist
     */
    public String createPlaylistForUser(String userId, String playlistName, List<TrackInfo> tracks) {

        // Search each track on Spotify and get the corresponding URI
        List<String> trackUris = tracks.stream()
                .map((TrackInfo track) -> spotifyClient.searchTrack(track.getArtist(), track.getTrack()))
                .collect(Collectors.toList());

        // Create playlist and add tracks
        String playlistId = spotifyClient.createPlaylist(userId, playlistName);
        spotifyClient.addTracksToPlaylist(playlistId, trackUris);

        // Return Spotify playlist URL
        return "https://open.spotify.com/playlist/" + playlistId;
    }
}