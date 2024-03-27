package com.example.redirector;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.sqids.Sqids;

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
        Long requestedId = sqids.decode(requestedSqid).getFirst();
        Optional<Url> urlOptional = urlRepository.findById(requestedId);
        return new RedirectView(urlOptional.orElseThrow().url());
    }
}
