package com.example.redirector;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class UrlController {
    private final UrlRepository urlRepository;

    private UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @GetMapping("/{shortId}")
    private RedirectView redirectToUrl(@PathVariable String shortId) {
        Optional<Url> urlOptional = urlRepository.findById(UUID.fromString(shortId));
        return new RedirectView(urlOptional.orElseThrow().getUrl());
    }
}
