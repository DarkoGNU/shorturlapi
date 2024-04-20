package com.example.shortener;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import org.sqids.Sqids;

import java.net.URI;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/")
public class UrlController {
    private final UrlRepository urlRepository;
    private final Sqids sqids = Sqids.builder().build();
    private final Random random = new Random();

    private UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @PostMapping
    private ResponseEntity<Void> createUrl(@Valid @RequestBody Url newUrl, UriComponentsBuilder ucb) {
        // Generate random ID between 0 and 3142742836020 using Sqids
        long longId;
        do {
            longId = random.nextLong(3142742836020L);
        } while (urlRepository.existsById(longId));

        newUrl.setId(longId);
        urlRepository.save(newUrl);
        String shortId = sqids.encode(List.of(longId));

        URI locationOfNewUrl = ucb
                .port(8082)
                .path("/{shortId}")
                .buildAndExpand(shortId)
                .toUri();

        return ResponseEntity.created(locationOfNewUrl).build();
    }
}
