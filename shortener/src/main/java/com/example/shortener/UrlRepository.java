package com.example.shortener;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.time.LocalDateTime;
import java.util.List;

interface UrlRepository extends CassandraRepository<Url, Long> {
    List<Url> findByCreatedTimeLessThan(LocalDateTime createdTime);
}
