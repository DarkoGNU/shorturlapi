package com.example.redirector;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

interface UrlRepository extends CassandraRepository<Url, UUID> {
}
