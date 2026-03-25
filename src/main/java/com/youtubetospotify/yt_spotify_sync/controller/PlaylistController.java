package com.youtubetospotify.yt_spotify_sync.controller;

import com.youtubetospotify.yt_spotify_sync.service.YoutubeService;
import com.youtubetospotify.yt_spotify_sync.utils.YoutubeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private final YoutubeService service = new YoutubeService();

    @GetMapping("/extract")
    public List<String> extract(@RequestParam String url) {

        String playlistId = YoutubeUtils.extractPlaylistId(url);

        if (playlistId == null) {
            return List.of("URL inválida");
        }

        return service.getTitles(playlistId);
    }
}