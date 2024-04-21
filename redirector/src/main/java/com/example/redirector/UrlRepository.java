package com.example.redirector;

import org.springframework.data.cassandra.repository.CassandraRepository;

interface UrlRepository extends CassandraRepository<Url, Long> {
}
