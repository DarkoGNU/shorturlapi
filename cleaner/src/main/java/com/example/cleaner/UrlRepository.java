package com.example.cleaner;

import org.springframework.data.cassandra.repository.CassandraRepository;

interface UrlRepository extends CassandraRepository<Url, Long> {
}
