package com.example.redirector;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;
import org.sqids.Sqids;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UrlController {
    private final UrlRepository urlRepository;
    private final Sqids sqids = Sqids.builder().build();

    private UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @GetMapping("/{requestedSqid}")
    private RedirectView redirectToUrl(@PathVariable String requestedSqid) {
        Long requestedId = sqids.decode(requestedSqid).get(0);
        Optional<Url> urlOptional = urlRepository.findById(requestedId);
        return new RedirectView(urlOptional.orElseThrow().url());
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
