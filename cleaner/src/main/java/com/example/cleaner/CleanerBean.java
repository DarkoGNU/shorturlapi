package com.example.cleaner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CleanerBean {
    private final UrlRepository urlRepository;
    Logger logger = LoggerFactory.getLogger(CleanerBean.class);

    @Autowired
    public CleanerBean(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Scheduled(initialDelay = 15000, fixedDelay = 3600000)
    public void cleanUp() {
        LocalDateTime cutOffTime = LocalDateTime.now().minusDays(1);
        logger.info("Cleaning entries older than: {}", cutOffTime);
        List<Url> oldUrls = urlRepository.findByCreatedTimeLessThan(cutOffTime);
        logger.info("Entries to delete:\n{}", oldUrls);
        urlRepository.deleteAll(oldUrls);
    }
}
