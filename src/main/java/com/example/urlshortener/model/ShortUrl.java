package com.example.urlshortener.model;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class ShortUrl {
    private String shortUrl;
    private String longUrl;
    private Long creationDate;

}
