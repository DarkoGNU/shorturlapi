package com.example.shortener;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class UrlController {
    private final UrlRepository urlRepository;
    // private final Sqids sqids = Sqids.builder().build();

    private UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @PostMapping
    private ResponseEntity<Void> createUrl(@Valid @RequestBody Url newUrl, UriComponentsBuilder ucb) {
        Url savedUrl = urlRepository.save(new Url(UUID.randomUUID(), newUrl.getUrl()));
        // String sqid = sqids.encode(Collections.singletonList(savedUrl.id()));

        URI locationOfNewUrl = ucb
                .path("/{sqid}")
                .buildAndExpand(savedUrl.getId())
                .toUri();

        return ResponseEntity.created(locationOfNewUrl).build();
    }
}
