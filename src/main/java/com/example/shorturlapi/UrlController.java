package com.example.shorturlapi;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;

import org.sqids.Sqids;

@RestController
@RequestMapping("/url")
public class UrlController {
    private final UrlRepository urlRepository;
    private final Sqids sqids = Sqids.builder().build();

    private UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @GetMapping("/{requestedSqid}")
    private ResponseEntity<Object> redirectToUrl(@PathVariable String requestedSqid) {
        Long requestedId = sqids.decode(requestedSqid).get(0);
        Optional<Url> urlOptional = urlRepository.findById(requestedId);

        HttpHeaders httpHeaders = new HttpHeaders();
        if (urlOptional.isPresent()) {
            try {
                URI url = new URI(urlOptional.get().url());
                httpHeaders.setLocation(url);
                return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
            } catch (URISyntaxException e) {
                // HttpStatus.NOT_FOUND
            }
        }

        return new ResponseEntity<>(httpHeaders, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    private ResponseEntity<Void> createUrl(@RequestBody String newUrl, UriComponentsBuilder ucb) {
        Url savedUrl = urlRepository.save(new Url(null, newUrl));
        String sqid = sqids.encode(Collections.singletonList(savedUrl.id()));

        URI locationOfNewUrl = ucb
                .path("url/{sqid}")
                .buildAndExpand(sqid)
                .toUri();

        return ResponseEntity.created(locationOfNewUrl).build();
    }
}
