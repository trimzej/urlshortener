package com.example.urlshortener.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.model.ShortUrlDTO;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class UrlController {

    HashMap<String, ShortUrl> urlMap = new HashMap<>();

    @PostMapping("/shorten")
    // Recieve long URL from request body (ShortUrlDTO)
    public ResponseEntity<String> shortenUrl(@RequestBody ShortUrlDTO shortUrlDTO) {
        String longUrl = shortUrlDTO.getLongUrl();
        String shortUrl = UUID.randomUUID().toString().substring(0, 8);
        if (longUrl.startsWith("http://")) {
            shortUrl = longUrl.substring(longUrl.lastIndexOf("/") + 1);
            urlMap.put(shortUrl, new ShortUrl(shortUrl, shortUrlDTO.getLongUrl(), System.currentTimeMillis()));
            return ResponseEntity.ok(shortUrl);
        } else {
            return ResponseEntity.badRequest().body("Invalid URL");
        }
    }

    @GetMapping("/urls")
    public Map<String, ShortUrl> getUrls() {
        return urlMap;
    }

    /*
     * @GetMapping("/{shortUrl}")
     * public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
     * String longUrl = urlService.getLongUrl(shortUrl);
     * return ResponseEntity.status(HttpStatus.FOUND)
     * .location(URI.create(longUrl))
     * .build();
     * }
     */
}
