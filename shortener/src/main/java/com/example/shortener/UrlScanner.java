package com.example.shortener;

import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UrlScanner {
    private final KafkaTemplate<Url, List<String>> kafkaTemplate;
    private final Trie trie = Trie.builder().ignoreCase().addKeywords(
            Arrays.asList("politic", "porn", "illuminati", "section31", "obama", "psilocybin")
    ).build();

    public UrlScanner(KafkaTemplate<Url, List<String>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void scanUrl(Url url) {
        Collection<Emit> matches = trie.parseText(url.getUrl());

        if (matches.isEmpty()) {
            return;
        }

        List<String> words = matches.stream().map(Emit::getKeyword).toList();
        sendMessage(url, words);
    }

    private void sendMessage(Url url, List<String> words) {
        kafkaTemplate.send("url.censorship", url, words);
    }
}
