package com.example.urlshortener.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.model.ShortUrlDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> getMethodName(@PathVariable String shortUrl) {
        if (urlMap.containsKey(shortUrl)) {
            String longUrl = urlMap.get(shortUrl).getLongUrl();
            return ResponseEntity.status(302).header("Location", longUrl).build();
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
