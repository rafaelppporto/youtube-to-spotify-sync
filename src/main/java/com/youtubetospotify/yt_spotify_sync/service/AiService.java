package com.youtubetospotify.yt_spotify_sync.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiService {

    @Value("${gemini.api-key}")
    private String apiKey;

    @Value("${gemini.api-url}")
    private String baseUrl;

    @Value("${gemini.model}")
    private String model;

    public String processTracks(List<String> titles) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(30000);
        RestTemplate restTemplate = new RestTemplate(factory);

        String url = baseUrl + "/models/" + model + ":generateContent?key=" + apiKey;
        System.out.println("---------------------API URL:   " + url);

        /*
         * String url =
         * "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key="
         * + apiKey;
         */

        String prompt = buildPrompt(titles);

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of(
                                "role", "user",
                                "parts", List.of(
                                        Map.of("text", prompt)))));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        return extractText(response.getBody());
    }

    public String buildPrompt(List<String> titles) {

        StringBuilder sb = new StringBuilder();

        sb.append("Extract artist, track and featured artists from each line.\n");
        sb.append("Return ONLY JSON array in this format:\n");
        sb.append("[{ \"artist\": \"\", \"track\": \"\", \"feat\": \"\" }]\n\n");

        for (String title : titles) {
            sb.append(title).append("\n");
        }

        return sb.toString();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String extractText(Map<String, Object> response) {

        List candidates = (List) response.get("candidates");
        Map firstCandidate = (Map) candidates.get(0);

        Map content = (Map) firstCandidate.get("content");
        List parts = (List) content.get("parts");

        Map textPart = (Map) parts.get(0);

        return textPart.get("text").toString();
    }
}