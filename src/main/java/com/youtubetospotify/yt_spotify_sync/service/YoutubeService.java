package com.youtubetospotify.yt_spotify_sync.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.youtubetospotify.yt_spotify_sync.client.YoutubeClient;
import com.youtubetospotify.yt_spotify_sync.model.TrackInfo;

@Service
public class YoutubeService {

    private final YoutubeClient youtubeClient;

    public YoutubeService(YoutubeClient youtubeClient) {
        this.youtubeClient = youtubeClient;
    }

    @SuppressWarnings("unchecked")
    public List<String> getRawTitles(String playlistId) {

        return youtubeClient.getPlaylistItems(playlistId).stream()
                .map(item -> {
                    Map<String, Object> snippet = (Map<String, Object>) item.get("snippet");
                    return snippet != null
                            ? snippet.get("title").toString()
                            : "";
                })
                .toList();
    }

    @SuppressWarnings("unchecked")
    public List<TrackInfo> getTracks(String playlistId) {

        return youtubeClient.getPlaylistItems(playlistId).stream()
                .map(item -> {

                    Map<String, Object> snippet = (Map<String, Object>) item.get("snippet");
                    String originalTitle = snippet != null
                            ? snippet.get("title").toString()
                            : "";

                    // 1. extract feat FROM ORIGINAL
                    String feat = extractFeat(originalTitle);

                    // 2. remove feat FROM ORIGINAL
                    String noFeatTitle = removeFeat(originalTitle);

                    // 3. clean AFTER removing feat
                    String cleaned = cleanTitle(noFeatTitle);

                    // 4. split artist / track
                    String[] split = splitArtistAndTrack(cleaned);

                    return new TrackInfo(
                            split[0],
                            split[1],
                            feat);
                })
                .toList();
    }

    private String cleanTitle(String title) {
        if (title == null)
            return "";

        String cleaned = title;

        // remove content inside () and []
        cleaned = cleaned.replaceAll("\\(.*?\\)", "");
        cleaned = cleaned.replaceAll("\\[.*?\\]", "");

        // remove quotes
        cleaned = cleaned.replace("\"", "");
        cleaned = cleaned.replace("'", "");

        // remove common unnecessary terms
        cleaned = cleaned.replaceAll("(?i)official music video", "");
        cleaned = cleaned.replaceAll("(?i)official video", "");
        cleaned = cleaned.replaceAll("(?i)lyrics", "");
        cleaned = cleaned.replaceAll("(?i)lyric video", "");
        cleaned = cleaned.replaceAll("(?i)audio", "");
        cleaned = cleaned.replaceAll("(?i)visualizer", "");
        cleaned = cleaned.replaceAll("(?i)visualiser", "");
        cleaned = cleaned.replaceAll("(?i)live", "");
        cleaned = cleaned.replaceAll("(?i)hd", "");
        cleaned = cleaned.replaceAll("(?i)4k", "");

        // remove everything after "|"
        cleaned = cleaned.replaceAll("\\|.*", "");

        // remove hashtags
        cleaned = cleaned.replaceAll("#\\w+", "");

        // remove emojis and non-ASCII characters
        cleaned = cleaned.replaceAll("[^\\x00-\\x7F]", "");

        // remove trailing dash (e.g., "Artist - Song -")
        cleaned = cleaned.replaceAll("-\\s*$", "");

        // normalize multiple spaces
        cleaned = cleaned.replaceAll("\\s+", " ");

        return cleaned.trim();
    }

    private String extractFeat(String title) {
        if (title == null)
            return "";

        var matcher = java.util.regex.Pattern
                .compile("(?i)\\b(?:ft\\.?|feat\\.?)\\s+(.+)")
                .matcher(title);

        if (matcher.find()) {
            String feat = matcher.group(1);

            // stop at common separators
            feat = feat.split(" - |\\(|\\[")[0];

            // remove trailing symbols
            feat = feat.replaceAll("[\\)\\]]", "");

            return feat.trim();
        }

        return "";
    }

    private String removeFeat(String title) {
        if (title == null)
            return "";

        return title.replaceAll("(?i)\\b(ft\\.?|feat\\.?)\\s+.+", "").trim();
    }

    private String[] splitArtistAndTrack(String title) {

        if (title == null || title.isBlank()) {
            return new String[] { "", "" };
        }

        // padrão mais comum: "Artista - Música"
        if (title.contains(" - ")) {
            return title.split(" - ", 2);
        }

        // fallback
        return new String[] { "", title };
    }

    public List<String> getPlaylistItemsUsingAI(String playlistId) {
        return null;
    }
}