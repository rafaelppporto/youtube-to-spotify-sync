package com.youtubetospotify.yt_spotify_sync.client;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SpotifyClient {

    // TODO: inject RestTemplate if needed
    // private final RestTemplate restTemplate;

    /**
     * Search a track on Spotify by artist and track name
     * 
     * @return track URI
     */
    public String searchTrack(String artistName, String trackName) {
        return null; // TODO: implement Spotify search API call
    }

    /**
     * Create a new playlist for the user
     * 
     * @return playlist ID or URL
     */
    public String createPlaylist(String userId, String playlistName) {
        return null; // TODO: implement create playlist API call
    }

    /**
     * Add a list of tracks to a playlist
     */
    public void addTracksToPlaylist(String playlistId, List<String> trackUris) {
        // TODO: implement add tracks API call
    }
}