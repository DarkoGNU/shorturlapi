package com.example.shortener;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import org.sqids.Sqids;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/")
public class UrlController {
    private final UrlRepository urlRepository;
    private final UrlScanner urlScanner;
    private final Sqids sqids = Sqids.builder().build();
    private final Random random = new Random();

    private UrlController(UrlRepository urlRepository, UrlScanner urlScanner) {
        this.urlRepository = urlRepository;
        this.urlScanner = urlScanner;
    }

    @PostMapping
    private ResponseEntity<Void> createUrl(@Valid @RequestBody Url newUrl, UriComponentsBuilder ucb) {
        // Generate random ID between 0 and 3142742836020 using Sqids
        long longId;
        do {
            longId = random.nextLong(3142742836020L);
        } while (urlRepository.existsById(longId));

        newUrl.setId(longId);
        newUrl.setCreatedTime(LocalDateTime.now());
        urlRepository.save(newUrl);
        urlScanner.scanUrl(newUrl);
        String shortId = sqids.encode(List.of(longId));

        URI locationOfNewUrl = ucb
                .port(8082)
                .path("/{shortId}")
                .buildAndExpand(shortId)
                .toUri();

        return ResponseEntity.created(locationOfNewUrl).build();
    }
}
