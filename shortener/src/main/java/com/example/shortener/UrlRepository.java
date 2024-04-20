package com.example.shortener;

import org.springframework.data.cassandra.repository.CassandraRepository;

interface UrlRepository extends CassandraRepository<Url, Long> {
}
