package com.example.shortener;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

interface UrlRepository extends CassandraRepository<Url, UrlKey> {
    Optional<Url> findByKeyId(Long longId);

    boolean existsByKeyId(Long longId);
}
