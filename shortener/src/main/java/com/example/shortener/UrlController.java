package com.example.shortener;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.sqids.Sqids;

import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/")
public class UrlController {
    private final UrlRepository urlRepository;
    private final Sqids sqids = Sqids.builder().build();

    private UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @PostMapping
    private ResponseEntity<Void> createUrl(@Valid @RequestBody Url newUrl, UriComponentsBuilder ucb) {
        Url savedUrl = urlRepository.save(new Url(null, newUrl.url()));
        String sqid = sqids.encode(Collections.singletonList(savedUrl.id()));

        URI locationOfNewUrl = ucb
                .path("/{sqid}")
                .buildAndExpand(sqid)
                .toUri();

        return ResponseEntity.created(locationOfNewUrl).build();
    }
}
